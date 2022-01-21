package com.company.handlers;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;


public class CrudHandler implements iHandler {
    private final Gson gson = new Gson();

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

    private void create(CrudArgs args) {

    }

    private void read(CrudArgs args) {
        try {
//            args.entity.cast()
            System.out.println(args.query);
            Object test = gson.fromJson(args.query, Object.class);
            System.out.println(test);

        } catch (JsonSyntaxException exception) {
            System.out.println(exception.getMessage());
            System.out.println(Arrays.toString(exception.getStackTrace()));
        }
    }

    private void update(CrudArgs args) {

    }

    private void delete(CrudArgs args) {

    }


}
