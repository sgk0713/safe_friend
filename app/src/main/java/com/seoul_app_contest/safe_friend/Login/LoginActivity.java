package com.seoul_app_contest.safe_friend.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seoul_app_contest.safe_friend.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new LoginPresenter(this);
        presenter.autoLogin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
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
                presenter.setUserData(emailEdt.getText().toString(), passwordEdt.getText().toString());
                presenter.login();
            }
        });
    }

    @Override
    public void showErrorToast() {

    }

    @Override
    public void redirectMainActivity() {

    }

    @Override
    public void redirectRegisterActivity() {

    }
}
