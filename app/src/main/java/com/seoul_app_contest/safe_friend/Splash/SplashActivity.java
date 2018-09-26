package com.seoul_app_contest.safe_friend.Splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seoul_app_contest.safe_friend.Login.LoginActivity;
import com.seoul_app_contest.safe_friend.Main.MainActivity;
import com.seoul_app_contest.safe_friend.R;

public class SplashActivity extends AppCompatActivity implements SplashContract.View{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashContract.Presenter presenter = new SplashPresenter(this);
        presenter.checkRemote();
//        presenter.checkAutoLogin();
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

    @Override
    public void showDialog(String title, String msg) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }
}
