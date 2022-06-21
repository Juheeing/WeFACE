package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BeforeEyeActivity extends AppCompatActivity {

    ImageView iv_before;
    Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_eye);

        iv_before = findViewById(R.id.iv_before);
        btn_close = findViewById(R.id.btn_close);

        iv_before.setImageBitmap(((EyeCameraActivity1) EyeCameraActivity1.context_camera1).rotatedBitmap1);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}