package com.example.mobileapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendRequest {
    private User user;
    private String message;

    public FriendRequest(User user, String message){
        this.user = user;
        this.message = message;
    }

    public static FriendRequest fromJsonObj(JSONObject obj) throws JSONException {
        User user = User.fromJsonObj(obj);
        return new FriendRequest(user, obj.getString("message"));
    }

    public User getUser() {
        return this.user;
    }

    public String getMessage() {
        return this.message;
    }
}
