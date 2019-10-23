package com.example.mobileapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserState implements Serializable {
    public String loginId;
    public double lat;
    public double lng;
    public int state;

    public UserState(String loginId, double lat, double lng, int state){
        this.loginId = loginId;
        this.lat =lat;
        this.lng =lng;
        this.state = state;
    }

    public UserState fromJsonObj(JSONObject obj) throws JSONException {
        return new UserState(obj.getString("loginId"),obj.getDouble("lat"),obj.getDouble("lng"),obj.getInt("state"));
    }
}
