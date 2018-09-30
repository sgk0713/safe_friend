package com.seoul_app_contest.safe_friend.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.find.FindActivity;
import com.seoul_app_contest.safe_friend.main.MainActivity;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.register.RegisterActivity;
import com.seoul_app_contest.safe_friend.protector_main.ProtectorMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements com.seoul_app_contest.safe_friend.login.LoginContract.View {

    private com.seoul_app_contest.safe_friend.login.LoginContract.Presenter presenter;
    @BindView(R.id.login_user_btn)Button userBtn;
    @BindView(R.id.login_protector_btn)Button protectorBtn;

    @OnClick({R.id.login_user_btn, R.id.login_protector_btn})void loginModeBtn(View view){
        presenter.changeLoginMode(view.getId());
    }

    @OnClick(R.id.login_find_btn)void findBtn(){
        redirectFindActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new com.seoul_app_contest.safe_friend.login.LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
//        final SpannableStringBuilder sp = new SpannableStringBuilder("세이프랜드");
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.title_green)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.title_blue)), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.mainColor)), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        loginLogoTv.append(sp);
        final EditText emailEdt = findViewById(R.id.login_email_edt);
        final EditText passwordEdt = findViewById(R.id.login_password_edt);
        Button registerBtn = findViewById(R.id.login_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectRegisterActivity();
            }
        });
        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signIn(emailEdt.getText().toString(), passwordEdt.getText().toString());
            }
        });
    }

    @Override
    public void showErrorToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
    public void redirectRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeLoginMode() {
        userBtn.setBackground(getDrawable(R.drawable.selected_login_button));
        protectorBtn.setBackground(getDrawable(R.drawable.default_login_button));
    }

    @Override
    public void selectUserLogin() {
        userBtn.setBackground(getDrawable(R.drawable.selected_login_button));
        protectorBtn.setBackground(getDrawable(R.drawable.default_login_button));
    }

    @Override
    public void selectProtectorLogin() {
        protectorBtn.setBackground(getDrawable(R.drawable.selected_login_button));
        userBtn.setBackground(getDrawable(R.drawable.default_login_button));
    }

    @Override
    public void redirectFindActivity() {
        Intent intent = new Intent(this, FindActivity.class);
        startActivity(intent);
    }
}
