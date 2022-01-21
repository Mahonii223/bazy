package com.company.handlers;


import com.google.gson.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import javax.persistence.criteria.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class CrudHandler implements iHandler {
    private final Gson gson = new Gson();
    private static SessionFactory sessionFactory = null;

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

//
//    private Predicate getPredicateWhere(CriteriaBuilder cb, Root root, JsonElement element, String name) {
//
//
//        if (element.isJsonObject()) {
//            JsonObject obj = element.getAsJsonObject();
//            if (name == "and") {
//
//            } else if (name == "or") {
//
//            } else {
//                for(String key : obj.keySet()) {
//
//                }
//            }
//
//        }
//
//
//    }

    private Predicate getPredicateFieldObject(CriteriaBuilder cb, Root<Object> root, JsonObject object, String fieldName) {
        List<Predicate> predicates = new ArrayList<>();
        for (String key: object.keySet()) {
            if (key == "and") {

            } else if (key == "or") {

            } else {
                Predicate p = getPredicateFieldPrimitive(cb, root, object.getAsJsonPrimitive(key), fieldName, key);
                predicates.add(p);
            }
        }



        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getPredicateFieldPrimitive(CriteriaBuilder cb, Root<Object> root, JsonPrimitive primitive, String fieldName, String op) {
        if (op == null) op = "equal";

        try {
            Method method = cb.getClass().getMethod(op, Expression.class, Object.class);

            if (primitive.isBoolean()) {
                return (Predicate) method.invoke(cb, root.get(fieldName), primitive.getAsBoolean());
            }
            if (primitive.isNumber()) {
                return (Predicate) method.invoke(cb, root.get(fieldName), primitive.getAsNumber());
            }
            if (primitive.isString()) {
                return (Predicate) method.invoke(cb, root.get(fieldName), primitive.getAsString());
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private CriteriaQuery<Object> buildCriteria(Class<?> entity, String query) {
        JsonObject top = JsonParser.parseString(query).getAsJsonObject();
        if (!top.has("where")) {
            System.out.println("JSON parsing: No key named 'where' at the top level.");
        }

        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Object> cr = cb.createQuery();
        Root<Object> root = cr.from(Object.class);

        JsonObject where = top.get("where").getAsJsonObject();
        List<Predicate> predicates = new ArrayList<>();
        for (String key: where.keySet()) {
            if (key == "and") {

            } else if (key == "or") {

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

        cr.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
        return cr;
    }

    private void create(CrudArgs args) {

    }

    private void read(CrudArgs args) {
        CriteriaQuery<Object> cr = buildCriteria(args.entity, args.query);
        System.out.println(cr);
    }

    private void update(CrudArgs args) {

    }

    private void delete(CrudArgs args) {

    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory =
                    configuration.configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
