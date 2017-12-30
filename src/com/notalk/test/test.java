package com.notalk.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class test {
    public static void main(String[] args) {
//        Gson gson = new GsonBuilder().create();
//        gson.toJson("Hello", System.out);
//        gson.toJson(123, System.out);
        Gson gson = new Gson();
        int i = gson.fromJson("100",int.class);
        String jsonNumber = gson.toJson(100);       // 100
        String jsonBoolean = gson.toJson(false);    // false
        String jsonString = gson.toJson("String"); //"String"
        System.out.println(jsonBoolean+jsonNumber+jsonString);
    }
}