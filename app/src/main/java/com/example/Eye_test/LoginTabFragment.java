package com.example.Eye_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment{

    EditText et_id, et_pw;
    Button btn_login;
    String username, password, login_token;
    RetrofitClient retrofitClient;
    LoginRequest loginRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = (View) inflater.inflate(R.layout.login_tab_fragment, container, false);

        et_id = view.findViewById(R.id.et_id);
        et_pw = view.findViewById(R.id.et_pw);
        btn_login = view.findViewById(R.id.btn_login);

        Button btn_login = view.findViewById(R.id.btn_login);

        //로그인 버튼 클릭시
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        return view;
    }

    public void Login() {

        username = et_id.getText().toString();
        password = et_pw.getText().toString();
        user.getInstance().setUsername(username);

        retrofitClient = RetrofitClient.getInstance();
        loginRequest = RetrofitClient.getRetrofitInterface();

        loginRequest.getLoginResponse(username, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Retrofit", "Data fetch SUCCESS");
                System.out.println(response);    //로그인 토큰 프린트

                if (response.isSuccessful() && response.code() == 200) {

                    login_token = response.body().getAccess();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), user.getInstance().getUsername() + "님 환영합니다.", Toast.LENGTH_LONG).show();
                    //Fragment에선 Activity.this가 아닌 getActivity사용

                } else {
                    Toast.makeText(getActivity(), "아이디/비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("로그인 실패", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                Log.e("연결실패", "오류");
            }
        });
    }
}