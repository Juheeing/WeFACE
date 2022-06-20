package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BrowImageActivity1 extends AppCompatActivity {

    Button btn_back;
    ImageView iv_image1;
    public static Context context_brow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brow_image1);

        context_brow = this;
        iv_image1 = (ImageView) findViewById(R.id.iv_image);
        btn_back = (Button) findViewById(R.id.btn_back);

        iv_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowImageActivity1.this, BrowCameraActivity1.class);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowImageActivity1.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}