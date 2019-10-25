package com.example.mobileapp.models;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendMessage {
    /**
     *  A model class for store friend message
     *  (a user id and a message) and parse from an json object
     */
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
