package com.seoul_app_contest.safe_friend.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.seoul_app_contest.safe_friend.login.LoginActivity;
import com.seoul_app_contest.safe_friend.main.MainActivity;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.protector_main.ProtectorMainActivity;



public class SplashActivity extends AppCompatActivity implements SplashContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashContract.Presenter presenter = new SplashPresenter(this);
        presenter.checkRemote();

//        TextView textView = findViewById(R.id.splash_tv);
//        final SpannableStringBuilder sp = new SpannableStringBuilder("세이프랜드");
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.title_green)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.title_blue)), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.mainColor)), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.append(sp);

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
    public void redirectProtectorMainActivity() {
        Intent intent = new Intent(this, ProtectorMainActivity.class);
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
