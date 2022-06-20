package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BrowImageActivity2 extends AppCompatActivity {

    ImageView iv_image1, iv_image2;
    Button btn_start, btn_back;
    public static Context context_brow2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brow_image2);

        context_brow2 = this;

        iv_image1 = (ImageView) findViewById(R.id.iv_image);
        iv_image2 = (ImageView) findViewById(R.id.iv_image2);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_back = (Button) findViewById(R.id.btn_back);

        iv_image1.setImageBitmap(((BrowCameraActivity1) BrowCameraActivity1.context_brow_camera1).rotatedBitmap1);

        iv_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowImageActivity2.this, BrowCameraActivity2.class);
                startActivity(intent);
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowImageActivity2.this, BrowImageActivity1.class);
                startActivity(intent);
            }
        });
    }
}