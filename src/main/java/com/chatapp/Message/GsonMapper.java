package com.chatapp.Message;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.javalin.json.JsonMapper;

public class GsonMapper implements JsonMapper{
    private Gson gson;

    public GsonMapper(){
        gson = new GsonBuilder().create();
    }
    @Override
    public <T> T fromJsonString(String json, Type targetType) {
        // TODO Auto-generated method stub
        return gson.fromJson(json, targetType);
    }

    @Override
    public String toJsonString(Object obj, Type type) {
        // TODO Auto-generated method stub
        return gson.toJson(obj, type);
    }
    
}
