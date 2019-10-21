package com.example.mobileapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class User {
    public String loginId;
    public String name;
    public int avatar;
    public String info;

    public User(String loginId, String name, int avatar, String info){
        this.loginId = loginId;
        this.name = name;
        this.avatar = avatar;
        this.info = info;
    }

    //{...}
    public static User fromJsonObj(JSONObject obj) throws JSONException {
        return new User(obj.getString("loginId"), obj.getString("name"), obj.getInt("avatar"),obj.getString("info"));
    }
}
