package com.seoul_app_contest.safe_friend.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seoul_app_contest.safe_friend.Login.LoginActivity;
import com.seoul_app_contest.safe_friend.Main.MainActivity;
import com.seoul_app_contest.safe_friend.R;

public class SplashActivity extends AppCompatActivity implements SplashContract.View{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashContract.Presenter presenter = new SplashPresenter(this);
        presenter.checkAutoLogin();
    }

    @Override
    public void redirectLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void redirectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
