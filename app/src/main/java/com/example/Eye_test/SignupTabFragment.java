package com.example.Eye_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    EditText et_id, et_pw, et_pwcheck, et_email;
    Button btn_signup;
    String username, password1, password2, email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = (View) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        et_id = view.findViewById(R.id.et_id);
        et_pw = view.findViewById(R.id.et_pw);
        et_pwcheck = view.findViewById(R.id.et_pwcheck);
        et_email = view.findViewById(R.id.et_email);
        btn_signup = view.findViewById(R.id.btn_signup);

        Button btn_signup = view.findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et_id.getText().toString();
                password1 = et_pw.getText().toString();
                password2 = et_pwcheck.getText().toString();
                email = et_email.getText().toString();

                if(!password1.equals(password2)){    //비밀번호와 비밀번호확인이 일치하지 않을때
                    Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    //Fragment에선 Activity.this가 아닌 getActivity사용
                }
                else {
                    UploadUtils.send_info(username, email, password1, password2);
                    Toast.makeText(getActivity(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}

