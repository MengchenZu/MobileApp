package com.example.mobileapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserState implements Serializable {
    public String loginId;
    public double lat;
    public double lng;
    public int state;
    public boolean online;

    public UserState(String loginId, double lat, double lng, int state, boolean online){
        this.loginId = loginId;
        this.lat =lat;
        this.lng =lng;
        this.state = state;
        this.online = online;
    }

    public static UserState fromJsonObj(JSONObject obj) throws JSONException {
        return new UserState(obj.getString("loginId"),obj.getDouble("lat"),obj.getDouble("lng"),obj.getInt("state"), obj.getBoolean("online"));
    }

    public boolean sameIdWith(UserState state2){
        return this.loginId.equals(state2.loginId);
    }

    public boolean equals(UserState state2){
        if(state2 == this){
            return true;
        }
        return sameIdWith(state2) &&
                this.lat == state2.lat &&
                this.lng == state2.lng &&
                this.state == state2.state;
    }
}
