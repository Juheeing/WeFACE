package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PopupActivity extends AppCompatActivity {

    Button btn_eye, btn_nose, btn_mouth, btn_brow, btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        btn_eye = findViewById(R.id.btn_eye);
        btn_nose = findViewById(R.id.btn_nose);
        btn_mouth = findViewById(R.id.btn_mouth);
        btn_brow = findViewById(R.id.btn_brow);
        btn_close = findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopupActivity.this, EyeImageActivity1.class);
                startActivity(intent);
            }
        });

        btn_mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopupActivity.this, MouthImageActivity1.class);
                startActivity(intent);
            }
        });

        btn_nose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopupActivity.this, NoseImageActivity1.class);
                startActivity(intent);
            }
        });

        btn_brow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopupActivity.this, BrowImageActivity1.class);
                startActivity(intent);
            }
        });
    }
}