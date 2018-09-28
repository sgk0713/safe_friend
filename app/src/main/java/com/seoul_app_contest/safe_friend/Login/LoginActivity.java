package com.seoul_app_contest.safe_friend.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.Main.MainActivity;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.Register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;
    @BindView(R.id.login_user_btn)Button userBtn;
    @BindView(R.id.login_protector_btn)Button protectorBtn;

    @OnClick({R.id.login_user_btn, R.id.login_protector_btn})void loginModeBtn(View view){
        presenter.changeLoginMode(view.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
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
}
