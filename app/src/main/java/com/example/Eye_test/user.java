package com.example.Eye_test;

public class user {

    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private static user instance = null;

    public static synchronized user getInstance(){
        if(null == instance){
            instance = new user();
        }
        return instance;
    }
}
