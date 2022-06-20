package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class MouthImageActivity2 extends AppCompatActivity {

    ImageView iv_image1, iv_image2;
    Button btn_start, btn_back;
    public static Context context_mouth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouth_image2);

        context_mouth2 = this;

        iv_image1 = (ImageView) findViewById(R.id.iv_image);
        iv_image2 = (ImageView) findViewById(R.id.iv_image2);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_back = (Button) findViewById(R.id.btn_back);

        iv_image1.setImageBitmap(((MouthCameraActivity1) MouthCameraActivity1.context_mouth_camera1).rotatedBitmap1);

        iv_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MouthImageActivity2.this, MouthCameraActivity2.class);
                startActivity(intent);
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MouthImageActivity2.this, MouthImageActivity1.class);
                startActivity(intent);
            }
        });
    }
}