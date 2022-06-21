package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BeforeNoseActivity extends AppCompatActivity {

    ImageView iv_before;
    Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_nose);

        iv_before = findViewById(R.id.iv_before);
        btn_close = findViewById(R.id.btn_close);

        iv_before.setImageBitmap(((NoseCameraActivity1) NoseCameraActivity1.context_nose_camera1).rotatedBitmap1);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}