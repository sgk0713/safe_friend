package com.seoul_app_contest.safe_friend.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.Main.MainActivity;
import com.seoul_app_contest.safe_friend.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @BindView(R.id.register_email_edt)
    EditText registerEmailEdt;
    @BindView(R.id.register_password_edt)
    EditText registerPasswordEdt;
    @BindView(R.id.register_name_edt)
    EditText registerNameEdt;
    @BindView(R.id.register_phone_num_edt)
    EditText registerPhoneNumEdt;
    @BindView(R.id.register_auth_num_edt)
    EditText registerAuthNumEdt;

    @OnClick(R.id.register_confirm_btn)
    void registerBtn() {
        presenter.addFirestore();
    }

    RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        presenter = new RegisterPresenter(this);
        ButterKnife.bind(this);
    }

    @Override
    public void redirectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showErrorToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}