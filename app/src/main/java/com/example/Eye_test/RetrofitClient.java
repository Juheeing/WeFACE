package com.example.Eye_test;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static RetrofitClient instance = null;
    private static LoginRequest loginRequest;

    //서버주소
    private  static String baseUrl = "http://52.79.174.94:8000/";

    private RetrofitClient() {
        //로그 보기 위해 interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //Retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) //로그기능 추가
                .build();

        loginRequest = retrofit.create(LoginRequest.class);

    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static LoginRequest getRetrofitInterface() {

        return loginRequest;
    }

}

