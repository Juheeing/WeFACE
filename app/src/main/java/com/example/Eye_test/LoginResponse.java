package com.example.Eye_test;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {    //서버로부터 받을 데이터

    @SerializedName("access")    //로그인 토큰
    private  String access;

    public String getAccess() {

        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
