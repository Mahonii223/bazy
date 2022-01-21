package com.company.handlers;


import com.google.gson.Gson;

public class CrudHandler implements iHandler {

    @Override
    public void handle(String[] args) {
        Gson gson = new Gson();
//        gson.toJson()
        System.out.println(gson.toJson(args));
    }
}
