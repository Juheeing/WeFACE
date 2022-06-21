package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Loading_Mouth extends AppCompatActivity {

    LottieAnimationView animationView, wait;
    String result;
    ImageView iv_image, iv_image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_mouth);

        animationView = findViewById(R.id.lottie);
        wait = findViewById(R.id.wait);
        iv_image = findViewById(R.id.iv_image);
        iv_image2 = findViewById(R.id.iv_image2);

        iv_image.setImageBitmap(((MouthCameraActivity1) MouthCameraActivity1.context_mouth_camera1).rotatedBitmap1);
        iv_image2.setImageBitmap(((MouthCameraActivity2) MouthCameraActivity2.context_mouth_camera2).rotatedBitmap2);

        Animation animation_right = AnimationUtils.loadAnimation(this, R.anim.anim_translate_right);
        Animation animation_left = AnimationUtils.loadAnimation(this, R.anim.anim_translate_left);

        iv_image.startAnimation(animation_right);
        iv_image2.startAnimation(animation_left);

        animationView.setAnimation("wizard2.json");
        animationView.playAnimation();
        animationView.loop(true);

        wait.setAnimation("loading.json");
        wait.playAnimation();
        wait.loop(true);

        new Loading_Mouth.RestAPITask("http://52.79.174.94:8000/change/check/").execute();
    }

    public class RestAPITask extends AsyncTask {
        // Variable to store url
        protected String mURL;

        // Constructor
        public RestAPITask(String url) {
            mURL = url;
        }

        // Background work
        @Override
        protected Object doInBackground(Object[] objects) {

            while(true) {

                try {
                    result = null;
                    URL url = new URL(mURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream is = conn.getInputStream();

                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line;
                    if ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    result = builder.toString();
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (result.equals("{\"text\":\"1\"}")) {
                    Intent intent = new Intent(Loading_Mouth.this, MouthResultActivity.class);
                    startActivity(intent);
                    break;
                }
            }
            return null;
        }
    }
}