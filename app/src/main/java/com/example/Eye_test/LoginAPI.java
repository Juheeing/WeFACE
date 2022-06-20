package com.example.Eye_test;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginAPI {
    @FormUrlEncoded
    @POST("accounts/login/")
    Call<LoginResponse> getLoginResponse(@Field("username") String username,
                                         @Field("password") String password);
}
