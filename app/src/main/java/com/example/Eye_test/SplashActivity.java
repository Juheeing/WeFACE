package com.example.Eye_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    View firstLayout;
    View secondlayout;
    View thirdLayout;
    View fourthLayout;
    View appName;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animationView = findViewById(R.id.lottie);
        animationView.setAnimation("wizard1.json");
        animationView.loop(true);   //로티 애니메이션 무한반복
        animationView.playAnimation();

        firstLayout = findViewById(R.id.firstLayout);
        secondlayout = findViewById(R.id.secondlayout);
        thirdLayout = findViewById(R.id.thirdLayout);
        fourthLayout = findViewById(R.id.fourthLayout);
        appName = findViewById(R.id.appName);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {     // 시간 지난 후 실행할 코딩

                animateMoveTo(firstLayout, 11.0f,-33.0f);
                animateMoveTo(secondlayout, 1.0f,-66.0f);
                animateMoveTo(thirdLayout, -1.0f,-66.0f);
                animateMoveTo(fourthLayout, -11.0f,-33f);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Animation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);    //애니메이션 투명도
                        fadeInAnimation.setDuration(1000);    //애니메이션 실행 시간
                        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {
                                // TODO Auto-generated method stub
                                animationView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation)
                            {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Animation fadeInAnimation2 = new AlphaAnimation(0.0f, 1.0f);    //애니메이션 투명도
                                        fadeInAnimation2.setDuration(3000);   //애니메이션 실행 시간
                                        fadeInAnimation2.setAnimationListener(new Animation.AnimationListener() {

                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                                appName.setVisibility(View.VISIBLE);    //appName 나타나기
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation)
                                            {
                                                StartMainActivity();
                                            }
                                        });

                                        appName.startAnimation(fadeInAnimation2);
                                    }
                                }, 20);
                            }
                        });

                        animationView.startAnimation(fadeInAnimation);
                    }
                }, 1000);    //1초 후 실행
            }
        }, 2000);    //2초 후 실행
    }

    private void animateMoveTo(@NonNull View view, float locationX, float locationY) {

        view.animate().setDuration(1000);
        view.animate().translationY(locationY * getDPI());
        view.animate().translationX(locationX * getDPI());
        view.animate().start();
    }

    private void StartMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private float getDPI() {
        double value = 1;
        DisplayMetrics displaymetrics = new DisplayMetrics();    //디스플레이의 너비 및 높이 얻기
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        value = getResources().getDisplayMetrics().density;
        String result = String.valueOf(value);
        return Float.parseFloat(result);
    }
}
