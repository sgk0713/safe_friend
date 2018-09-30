package com.seoul_app_contest.safe_friend.find;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.seoul_app_contest.safe_friend.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindActivity extends AppCompatActivity {

    @BindView(R.id.find_tb)Toolbar toolbar;
    @BindView(R.id.find_email_btn)Toolbar emailBtn;
    @BindView(R.id.find_password_btn)Toolbar passwordBtn;
    @OnClick(R.id.find_prev_ib)void prev(){
        finish();
    }

    @OnClick(R.id.find_email_btn)void emailBtn(){
        emailBtn.setTitleTextColor(getColor(R.color.mainColor));
        passwordBtn.setTitleTextColor(getColor(R.color.black));
        getSupportFragmentManager().beginTransaction().replace(R.id.find_fl, new EmailFindFragment()).commit();
    }
    @OnClick(R.id.find_password_btn)void passwordBtn(){
        passwordBtn.setTitleTextColor(getColor(R.color.mainColor));
        emailBtn.setTitleTextColor(getColor(R.color.black));
        getSupportFragmentManager().beginTransaction().replace(R.id.find_fl, new PasswordFindFragment()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.find_fl, new EmailFindFragment()).commit();

    }
}
