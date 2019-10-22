package com.example.mobile;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
    private String loginId;
    private String name;
    private int avatar;
    private String info;

    public User(String name, int avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public User(String loginId, String name, int avatar, String info) {
        this.loginId = loginId;
        this.name = name;
        this.avatar = avatar;
        this.info = info;
    }

    public String getName() {
        return this.name;
    }

    public int getAvatar() {
        return this.avatar;
    }

    public static User fromJsonObj(JSONObject obj) throws JSONException {
        return new User(obj.getString("loginId"), obj.getString("name"), obj.getInt("avatar"),obj.getString("info"));
    }
}
