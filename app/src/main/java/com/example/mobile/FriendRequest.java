package com.example.mobile;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendRequest {
    User user;
    String message;

    public FriendRequest(User user, String message){
        this.user = user;
        this.message = message;
    }

    public FriendRequest fromJsonObj(JSONObject obj) throws JSONException {
        User user = User.fromJsonObj(obj);
        return new FriendRequest(user, obj.getString("message"));
    }
}
