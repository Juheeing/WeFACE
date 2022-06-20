package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class MouthImageActivity1 extends AppCompatActivity {

    Button btn_back;
    ImageView iv_image1;
    public static Context context_mouth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouth_image1);

        context_mouth = this;
        iv_image1 = (ImageView) findViewById(R.id.iv_image);
        btn_back = (Button) findViewById(R.id.btn_back);

        iv_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MouthImageActivity1.this, MouthCameraActivity1.class);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MouthImageActivity1.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}