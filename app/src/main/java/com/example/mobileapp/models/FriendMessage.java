package com.example.mobileapp.models;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendMessage {
    public String loginId;
    public String message;

    public FriendMessage(String loginId, String message){
        this.loginId = loginId;
        this.message = message;
    }

    public static FriendMessage fromJsonObj(JSONObject obj) throws JSONException {
        return  new FriendMessage(obj.getString("loginId"),obj.getString("message"));
    }
}
