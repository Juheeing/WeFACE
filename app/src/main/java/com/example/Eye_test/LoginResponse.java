package com.example.Eye_test;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("access")
    private  String access;

    public String getAccess() {

        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
