package com.kkb.utils;

import com.google.gson.Gson;

public class JSONUtil {
    private static Gson g = new Gson();
    public static String toJSON(Object obj){
        return g.toJson(obj);
    }
}
