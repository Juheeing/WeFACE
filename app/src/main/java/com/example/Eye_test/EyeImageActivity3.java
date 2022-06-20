package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EyeImageActivity3 extends AppCompatActivity {

    ImageView iv_image1, iv_image2;
    Button btn_start, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_image3);

        iv_image1 = (ImageView) findViewById(R.id.iv_image);
        iv_image2 = (ImageView) findViewById(R.id.iv_image2);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_back = (Button) findViewById(R.id.btn_back);

        iv_image1.setImageBitmap(((EyeCameraActivity1) EyeCameraActivity1.context_camera1).rotatedBitmap1);
        iv_image2.setImageBitmap(((EyeCameraActivity2) EyeCameraActivity2.context_camera2).rotatedBitmap2);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FileUploadUtils.send_eye(((EyeCameraActivity1) EyeCameraActivity1.context_camera1).file_1);
                FileUploadUtils.send_eye(((EyeCameraActivity2) EyeCameraActivity2.context_camera2).file_2);
                Intent intent = new Intent(EyeImageActivity3.this, Loading_Eye.class);
                startActivity(intent);
            }
        });
    }
}