package com.example.mobileapp.models;

import android.content.Context;
import android.util.JsonReader;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.example.mobileapp.MapsActivity;
import com.example.mobileapp.models.*;
import com.example.mobileapp.utilities.ApiHelper;
import com.google.android.gms.common.util.Predicate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserData {

    // login info
    private String loginId;
    private String password;
    private String sessionKey;
    private int timeOut;

    // data
    private User currentUser;
    private UserState currentUserState;
    private HashMap<String, User> friends = new HashMap<>();
    private HashMap<String, UserState> friendStates = new HashMap<>();
    private List<FriendRequest> requests= new ArrayList<>();
    private HashMap<String,String> messages = new HashMap<>();

    // instance
    private static UserData userData = new UserData();

    // init
    public static boolean initRegister(Context context, String loginId, String rowPassword, String name, int avatar, String info){
        userData.loginId = loginId;
        userData.password = getMD5(rowPassword);
        if(userData.password == null){
            showInfo(context,"Password conversion failed");
            return false;
        }
        String regResult =  ApiHelper.register(userData.loginId, userData.password, name,avatar,info);
        return processResult(context, regResult, (JSONObject objResult) -> {
            try{
                userData.sessionKey = objResult.getString("sessionkey");
                userData.timeOut = objResult.getInt("timeout");
                return true;
            }catch (Exception e){
                showInfo(context,"Unable to read the result returned by the server");
                return false;
            }
        });
    }

    public static boolean initLogin(Context context, String loginId, String rowPassword){
        userData.loginId = loginId;
        userData.password = getMD5(rowPassword);
        if(userData.password == null){
            showInfo(context,"Password conversion failed");
            return false;
        }
        String regResult =  ApiHelper.login(userData.loginId, userData.password);
        return processResult(context, regResult, (JSONObject objResult) -> {
            try{
                userData.sessionKey = objResult.getString("sessionkey");
                userData.timeOut = objResult.getInt("timeout");
                return true;
            }catch (Exception e){
                showInfo(context,"Unable to read the result returned by the server");
                return false;
            }
        });
    }

    public static boolean initSessionKey(Context context, String sessionKey){
        if(sessionKey == null){
            return false;
        }
        userData.sessionKey = sessionKey;

        userData.currentUser = null;
        userData.currentUserState = null;
        userData.friends.clear();
        userData.friendStates.clear();
        userData.requests.clear();
        userData.messages.clear();
        return true;
    }
    // instance
    public static UserData getInstance(){
        return userData.sessionKey == null ? null : userData;
    }

    // update

    public boolean updateSelfProfile(Context context){
        String strResult =  ApiHelper.self(sessionKey);
        return processResult(context, strResult, (JSONObject objResult) -> {
            try {
                JSONObject selfObj = objResult.getJSONObject("self");
                userData.currentUser =  User.fromJsonObj(selfObj);
                return true;
            }catch (Exception e){
                showInfo(context,"Unable to read the result returned by the server");
                return false;
            }
        });
    }

    public static boolean updateFriends(Context context){
        String strResult =  ApiHelper.friends(userData.sessionKey);
        return processResult(context, strResult, (JSONObject objResult)->{
            try {
                JSONArray friendsArr = objResult.getJSONArray("friends");
                for (int i = 0; i < friendsArr.length(); i++) {
                    User friend = User.fromJsonObj(friendsArr.getJSONObject(i));
                    userData.friends.put(friend.loginId, friend);
                }
                return true;
            }catch (Exception e){
                showInfo(context,"Unable to read the result returned by the server");
                return false;
            }
        });
    }

    // updateFriendStates
    public static boolean updateFriendStates(Context context){
        String strResult =  ApiHelper.friendStates(userData.sessionKey);
        return  processResult(context, strResult, (JSONObject objResult)->{
            try {
                JSONArray statesArr = objResult.getJSONArray("states");
                for (int i = 0; i < statesArr.length(); i++) {
                    UserState state = UserState.fromJsonObj(statesArr.getJSONObject(i));
                    userData.friendStates.put(state.loginId, state);
                }
                return true;
            }catch (Exception e){
                showInfo(context,"Unable to read the result returned by the server");
                return false;
            }
        });
    }
    // updateRequests
    public static boolean updateRequest(Context context){
        String strResult =  ApiHelper.getRequests(userData.sessionKey);
        return processResult(context, strResult, (JSONObject objResult)->{
            try {
                JSONArray requestArr = objResult.getJSONArray("requests");
                userData.requests.clear();
                for (int i = 0; i < requestArr.length(); i++) {
                    FriendRequest req = FriendRequest.fromJsonObj(requestArr.getJSONObject(i));
                    userData.requests.add(req);
                }
                return true;
            }catch (Exception e){
                showInfo(context,"Unable to read the result returned by the server");
                return false;
            }
        });
    }

    // updateMessages
    public static boolean updateMessages(Context context){
        String strResult =  ApiHelper.getMessages(userData.sessionKey);
        return  processResult(context, strResult, (JSONObject objResult)->{
            try {
                JSONArray messagesArr = objResult.getJSONArray("messages");
                for (int i = 0; i < messagesArr.length(); i++) {
                    FriendMessage message = FriendMessage.fromJsonObj(messagesArr.getJSONObject(i));

                    userData.messages.put(message.loginId, message.message);
                }
                return true;
            }catch (Exception e){
                showInfo(context,"Unable to read the result returned by the server");
                return false;
            }
        });
    }

    // selfState
    public static boolean updateSelfState(Context context, UserState userState){
        if (userState!=null){
            userData.currentUserState = userState;
            String strResult = ApiHelper.updateState(userData.sessionKey,userState.lat,userState.lng,userState.state);
            return processResult(context,strResult,(JSONObject objResult)-> true);
        }else{
            return false;
        }
    }


    // getter

    public User getCurrentUser(){
        return userData.currentUser;
    }

    public UserState getCurrentState(){
        return userData.currentUserState;
    }

    public User getFriend(String loginId){
        return userData.friends.get(loginId);
    }

    public Collection<User> getFriends(){
        return userData.friends.values();
    }

    public UserState getState(String loginId){
        return userData.friendStates.get(loginId);
    }

    public Collection<UserState> getStates(){
        return userData.friendStates.values();
    }

    public List<FriendRequest> getRequests(){
        return userData.requests;
    }

    public String getMessage(String loginId){
        return userData.messages.get(loginId);
    }

    public Collection<String> getMessages(){
        return userData.messages.values();
    }

    public void clear(){
        userData = new UserData();
    }

    public String getSessionKey(){
        return userData.sessionKey;
    }

    // setter
    public boolean setPassword(String rowPassword){
        String pw = getMD5(rowPassword);
        if(pw == null){
            return false;
        }else {
            userData.password = pw;
            return true;
        }
    }

    public void setCurrentUserState(UserState state){
        userData.currentUserState = state;
    }

    // static func
    private static void showInfo(Context context, String text){
        if(context!=null){
            Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
        }
    }

    private static String getMD5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean processResult(Context context, String strResult, Predicate<JSONObject> predicate){
        if(strResult == null){
            showInfo(context,"Unable to connect to the server, please check the network and try again");
            return false;
        }else{
            try {
                JSONObject objResult = new JSONObject(strResult);
                SimpleResult result = SimpleResult.fromJsonObj(objResult);
                if(result.success){
                    return predicate.apply(objResult);
                }
                else {
                    showInfo(context,result.message);
                    return false;
                }

            }
            catch (JSONException je){
                showInfo(context,"Unable to read the result returned by the server");
                return false;
            }
        }
    }


}
