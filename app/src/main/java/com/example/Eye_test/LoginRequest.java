package com.example.Eye_test;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginRequest {    //서버에 보낼 데이터
    @FormUrlEncoded    //키벨류 형식, @Field와 같이 쓰임.
    @POST("accounts/login/")    //@통신 방식("통신 API명")
    Call<LoginResponse> getLoginResponse(@Field("username") String username,
                                         @Field("password") String password);
}
