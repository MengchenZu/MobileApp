package com.example.mobileapp.models;

import com.example.mobileapp.R;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
    public String loginId;
    public String name;
    public int avatar;
    public String info;

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

    public String getLoginId() {
        return this.loginId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return this.avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static User fromJsonObj(JSONObject obj) throws JSONException {
        int avatar = obj.getInt("avatar");
        switch (avatar) {
            case 1:
                avatar = R.drawable.one;
                break;
            case 2:
                avatar = R.drawable.two;
                break;
            case 3:
                avatar = R.drawable.three;
                break;
            case 4:
                avatar = R.drawable.four;
                break;
            case 5:
                avatar = R.drawable.five;
                break;
            case 6:
                avatar = R.drawable.six;
                break;
            case 7:
                avatar = R.drawable.seven;
                break;
            default:
                avatar = R.drawable.profile;
                break;
        }
        return new User(obj.getString("loginId"), obj.getString("name"), avatar, obj.getString("info"));
    }

    public boolean sameIdWith(User user2){
        return this.loginId.equals(user2.loginId);
    }

    public boolean sameIdWith(UserState state){
        return this.loginId.equals(state.loginId);
    }

    public boolean equals(User user2){
        if(user2 == this){
            return true;
        }
        return sameIdWith(user2) &&
                this.name == user2.name &&
                this.avatar == user2.avatar &&
                this.info == user2.info;
    }

}
