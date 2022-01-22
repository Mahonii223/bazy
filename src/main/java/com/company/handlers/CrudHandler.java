package com.company.handlers;


import com.company.entities.OrderDetails;
import com.company.entities.Shippers;
import com.google.gson.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;


public class CrudHandler implements iHandler {
    Gson gson = new Gson();
    private static SessionFactory sessionFactory = null;

    private final Map<String, Method> methodMap = new HashMap<>();

    public static class CrudArgs {
        public Class<?> entity;
        public String command;
        public String query;
        public String update = null;
    }

    @Override
    public void handle(String[] args) {
        CrudArgs parsed = parseArgs(args);
        if (parsed == null) return;
        initMethodMap();

        switch (parsed.command) {
            case "create" -> create(parsed);
            case "read" -> read(parsed);
            case "update" -> update(parsed);
            case "delete" -> delete(parsed);
            default -> System.out.printf("Incorrect crud command '%s'. Available: create, read, update, delete%n", parsed.command);
        }
    }

    public CrudArgs parseArgs(String[] args) {
        if (args.length < 2) {

            System.out.println("No database entity specified.");
            return null;
        }
        if (args.length < 3) {
            System.out.println("No crud command specified. Available: create, read, update, delete");
            return null;
        }
        if (args.length < 4) {
            System.out.println("No query file specified.");
            return null;
        }

        CrudArgs result = new CrudArgs();
        result.command = args[2];
        if (Objects.equals(result.command, "update") && args.length < 5) {
            System.out.println("Update command requires additional 'updatedRecord.json' file with instructions how to modify query results.");
            return null;
        }

        String entity = args[1];
        try {
            result.entity = Class.forName("com.company.entities." + entity);
        } catch (ClassNotFoundException exception) {
            System.out.printf("Entity class '%s' not found.%n", entity);
            return null;
        }

        String queryFile = args[3];
        try {
            result.query = Files.readString(Path.of(queryFile));
        } catch (IOException exception) {
            System.out.printf("Query file '%s' not found.%n", queryFile);
        }

        if (Objects.equals(result.command, "update")) {
            String updateFile = args[4];
            try {
                result.update = Files.readString(Path.of(updateFile));
            } catch (IOException exception) {
                System.out.printf("Query file '%s' not found.%n", updateFile);
            }
            System.out.println("Update command requires additional 'updatedRecord.json' file with instructions how to modify query results.");
            return null;
        }

        return result;
    }

    private Predicate getPredicateFieldObject(CriteriaBuilder cb, Root<?> root, JsonObject object, String fieldName) {
        List<Predicate> predicates = new ArrayList<>();
        for (String key: object.keySet()) {
            if (Objects.equals(key, "and")) {
                // todo
            } else if (Objects.equals(key, "or")) {
                // todo
            } else {
                Predicate p = getPredicateFieldPrimitive(cb, root, object.getAsJsonPrimitive(key), fieldName, key);
                predicates.add(p);
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getPredicateFieldPrimitive(CriteriaBuilder cb, Root<?> root, JsonPrimitive primitive, String fieldName, String op) {
        if (op == null) op = "equal";

        try {
            Method method = methodMap.get(op);
            if (method == null) return null;
            if (primitive.isBoolean()) {
                return (Predicate) method.invoke(cb, root.get(fieldName), primitive.getAsBoolean());
            }
            if (primitive.isNumber()) {
//                if (op.equals("lessThan") || op.equals("lessThanOrEqualTo") || op.equals("greaterThan") || op.equals("greaterThanOrEqualTo")) {
//                    return (Predicate) method.invoke(cb, root.get(fieldName), primitive.getAsNumber());
//                }
                return (Predicate) method.invoke(cb, root.get(fieldName), primitive.getAsNumber().doubleValue());
            }
            if (primitive.isString()) {
                return (Predicate) method.invoke(cb, root.get(fieldName), primitive.getAsString());
            }
            if (primitive.isJsonNull()) {
                return (Predicate) method.invoke(cb, root.get(fieldName));
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Predicate buildWhere(Session session, Root root, Class<?> entity, String query) {
        JsonObject top = JsonParser.parseString(query).getAsJsonObject();
        if (!top.has("where")) {
            System.out.println("JSON parsing: No key named 'where' at the top level.");
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();

        JsonObject where = top.get("where").getAsJsonObject();
        List<Predicate> predicates = new ArrayList<>();
        for (String key: where.keySet()) {
            if (Objects.equals(key, "and")) {
                // todo
            } else if (Objects.equals(key, "or")) {
                // todo
            } else {
                JsonElement el = where.get(key);
                if (el.isJsonObject()) {
                    Predicate p = getPredicateFieldObject(cb, root, el.getAsJsonObject(), key);
                    predicates.add(p);
                } else if (el.isJsonPrimitive()) {
                    Predicate p = getPredicateFieldPrimitive(cb, root, el.getAsJsonPrimitive(), key, null);
                    predicates.add(p);
                }
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private CriteriaQuery<Object> buildCriteriaQuery(Session session, Class<?> entity, String query) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object> cr = cb.createQuery();
        Root<?> root = cr.from(entity);
        Predicate where = buildWhere(session, root, entity, query);
        cr.select(root).where(where);
        return cr;
    }

    private CriteriaDelete<?> buildCriteriaDelete(Session session, Class<?> entity, String query) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Object> criteriaDelete = cb.createCriteriaDelete((Class<Object>) entity);

        Root<?> root = criteriaDelete.from((Class<Object>) entity);
        Predicate where = buildWhere(session, root, entity, query);
        criteriaDelete.where(where);
        return criteriaDelete;
    }

    private Object createEntity(Class<?> entity, String query) {
        return gson.fromJson(query, entity);
//        try {
//            Constructor<?> cons = entity.getConstructor();
//            Object instance = cons.newInstance();
//            Object parsed = gson.fromJson(query, entity);
//            for (Field field : parsed.getClass().getDeclaredFields()) {
//                Field target = instance.getClass().getDeclaredField(field.getName());
//                field.setAccessible(true);
//                target.setAccessible(true);
//                target.set(instance, field.get(parsed));
//            }
//            return instance;
//        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    private void create(CrudArgs args) {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Object entity = createEntity(args.entity, args.query);
        session.save(entity);
        tx.commit();
        session.close();
    }

    private void read(CrudArgs args) {
        Session session = getSessionFactory().openSession();
        CriteriaQuery<Object> cr = buildCriteriaQuery(session, args.entity, args.query);

        Query<Object> q = session.createQuery(cr);
        System.out.println("SQL: " + q.getQueryString());
        List<Object> results = q.getResultList();
        printResults(args.entity, results);
    }

    private void update(CrudArgs args) {

    }

    private void delete(CrudArgs args) {
        Session session = getSessionFactory().openSession();
        CriteriaDelete<?> cr = buildCriteriaDelete(session, args.entity, args.query);

        Query<?> q = session.createQuery(cr);
        System.out.println("SQL: " + q.getQueryString());

        Transaction t = session.beginTransaction();
        q.executeUpdate();
        t.commit();
    }

    private void initMethodMap() {
        try {
            methodMap.put("abs", CriteriaBuilder.class.getMethod("abs", Expression.class));
            methodMap.put("coalesce", CriteriaBuilder.class.getMethod("coalesce", Expression.class, Object.class));
            methodMap.put("concat", CriteriaBuilder.class.getMethod("concat", Expression.class, String.class));
            methodMap.put("diff", CriteriaBuilder.class.getMethod("diff", Expression.class, Number.class));
            methodMap.put("equal", CriteriaBuilder.class.getMethod("equal", Expression.class, Object.class));
            methodMap.put("greaterThan", CriteriaBuilder.class.getMethod("greaterThan", Expression.class, Comparable.class));
            methodMap.put("greaterThanOrEqualTo", CriteriaBuilder.class.getMethod("greaterThanOrEqualTo", Expression.class, Comparable.class));
            methodMap.put("ge", CriteriaBuilder.class.getMethod("ge", Expression.class, Number.class));
            methodMap.put("gt", CriteriaBuilder.class.getMethod("gt", Expression.class, Number.class));
            methodMap.put("isEmpty", CriteriaBuilder.class.getMethod("isEmpty", Expression.class));
            methodMap.put("isFalse", CriteriaBuilder.class.getMethod("isFalse", Expression.class));
            methodMap.put("isNotEmpty", CriteriaBuilder.class.getMethod("isNotEmpty", Expression.class));
            methodMap.put("isNotNull", CriteriaBuilder.class.getMethod("isNotNull", Expression.class));
            methodMap.put("isNull", CriteriaBuilder.class.getMethod("isNull", Expression.class));
            methodMap.put("isTrue", CriteriaBuilder.class.getMethod("isTrue", Expression.class));
            methodMap.put("lessThan", CriteriaBuilder.class.getMethod("lessThan", Expression.class, Comparable.class));
            methodMap.put("lessThanOrEqualTo", CriteriaBuilder.class.getMethod("lessThanOrEqualTo", Expression.class, Comparable.class));
            methodMap.put("le", CriteriaBuilder.class.getMethod("le", Expression.class, Number.class));
            methodMap.put("like", CriteriaBuilder.class.getMethod("like", Expression.class, String.class));
            methodMap.put("lt", CriteriaBuilder.class.getMethod("lt", Expression.class, Number.class));
            methodMap.put("mod", CriteriaBuilder.class.getMethod("mod", Expression.class, Integer.class));
            methodMap.put("notEqual", CriteriaBuilder.class.getMethod("notEqual", Expression.class, Object.class));
            methodMap.put("notLike", CriteriaBuilder.class.getMethod("notLike", Expression.class, String.class));
            methodMap.put("prod", CriteriaBuilder.class.getMethod("prod", Expression.class, Number.class));
            methodMap.put("quot", CriteriaBuilder.class.getMethod("quot", Expression.class, Number.class));
            methodMap.put("sqrt", CriteriaBuilder.class.getMethod("sqrt", Expression.class));
            methodMap.put("substring", CriteriaBuilder.class.getMethod("substring", Expression.class, int.class));
            methodMap.put("sum", CriteriaBuilder.class.getMethod("sum", Expression.class, Number.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory =
                    configuration.configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    private void printResults(Class<?> entity, List<?> results) {
        List<String> headers = getHeaderList(entity);
        List<Integer> widths = new ArrayList<>(headers.stream().map(String::length).toList());
        List<List<String>> rows = new ArrayList<>();
        for (Object result: results) {
            List<String> row = getValueList(entity, result);
            rows.add(row);

            IntStream.range(0, row.size()).forEach(value -> widths.set(value, Math.max(row.get(value).length(), widths.get(value))));
        }
        List<String> paddedHeaders = IntStream.range(0, widths.size()).mapToObj(i -> padRight(headers.get(i), widths.get(i))).toList();
        System.out.println(String.join(" | ", paddedHeaders));
        System.out.println(String.join(" | ", paddedHeaders.stream().map(s -> "-".repeat(s.length())).toList()));

        for (List<String> row : rows) {
            List<String> paddedRow = IntStream.range(0, widths.size()).mapToObj(i -> padRight(row.get(i), widths.get(i))).toList();
            System.out.println(String.join(" | ", paddedRow));
        }
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private List<String> getHeaderList(Class<?> entity) {
        List<String> values = new ArrayList<>();

        for (Field field : entity.getDeclaredFields()) {
            values.add(field.getName());
        }
        return values;
    }

    private List<String> getValueList(Class<?> entity, Object obj) {
        List<String> values = new ArrayList<>();
        for (Field field : entity.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                values.add(field.get(obj).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

}
