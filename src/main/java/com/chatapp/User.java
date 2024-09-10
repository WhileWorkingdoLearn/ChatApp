package com.chatapp;
import java.util.*;

public class User {
    private final String userName;
    private final String id;
    private final String sessionID;
    private final String groupID;
    private final String role;
    private boolean isActive;
    
    public User(String userName,String id, String groupID,String role, boolean isActive) {
        this.userName = userName == null ? "" : userName;
        this.id = id == null ? "" : id;
        this.sessionID = randomId();
        this.groupID = groupID;
        this.role = role;
        this.isActive = isActive;
    }

    private static String randomId() {
        return UUID.randomUUID().toString();
    }

    public String getUserName(){
        return userName;
    }

    public String getId() {
        return id;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getRole(){
        return role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
