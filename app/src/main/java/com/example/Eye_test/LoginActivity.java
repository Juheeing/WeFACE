package com.example.Eye_test;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

//FragmentActivity는 하나의 액티비티 안에 여러개의 액티비티를 보여줌
public class LoginActivity extends FragmentActivity {

    // 구글로그인 result 상수
    private static final int RC_SIGN_IN = 900;

    // 구글api클라이언트
    GoogleSignInClient googleSignInClient;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    public static Context context_login;
    FirebaseUser user;
    String name, email, uid;
    Uri photoUrl;
    boolean emailVerified;
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context_login = this;

        firebaseAuth = FirebaseAuth.getInstance();

        // 앱에 필요한 사용자 데이터를 요청하도록 구글로그인 옵션을 설정한다.
        // DEFAULT_SIGN_IN는 유저의 구글ID와 기본적인 프로필 정보를 요청하는데 사용된다.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        google = (FloatingActionButton) findViewById(R.id.fab_google);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //구글 로그인
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //화면 넘김 설정
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 구글로그인 버튼 응답
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 구글 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                name = user.getDisplayName();
                                email = user.getEmail();
                                photoUrl = user.getPhotoUrl();
                                emailVerified = user.isEmailVerified();
                                uid = user.getUid();
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, email + "로그인성공", Toast.LENGTH_SHORT).show();

                        } else {
                            // 로그인 실패
                            Toast.makeText(LoginActivity.this, "로그인실패", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}