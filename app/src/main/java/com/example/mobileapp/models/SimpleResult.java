package com.example.mobileapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class SimpleResult {
    public boolean success;
    public String message;

    public SimpleResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public static SimpleResult fromJsonObj(JSONObject obj) throws JSONException {
        return new SimpleResult(obj.getBoolean("success"), obj.getString("message"));
    }
}
