package com.seoul_app_contest.safe_friend.Register;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.Login.LoginActivity;
import com.seoul_app_contest.safe_friend.Main.MainActivity;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.dto.PostDto;
import com.seoul_app_contest.safe_friend.postcode.PostcodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    PostDto postDto;

    @BindView(R.id.register_email_edt)
    EditText registerEmailEdt;
    @BindView(R.id.register_password_edt)
    EditText registerPasswordEdt;
    @BindView(R.id.register_password_confirm_edt)
    EditText registerPasswordConfirmEdt;

    @BindView(R.id.register_name_edt)
    EditText registerNameEdt;
    @BindView(R.id.register_phone_num_edt)
    EditText registerPhoneNumEdt;
    @BindView(R.id.register_auth_num_edt)
    EditText registerAuthNumEdt;

    @BindView(R.id.register_year_edt)
    EditText registerYearEdt;
    @BindView(R.id.register_month_edt)
    EditText registerMonthEdt;
    @BindView(R.id.register_day_edt)
    EditText registerDayEdt;

    @BindView(R.id.register_postcode_edt)
    EditText registerPostcodeEdt;
    @BindView(R.id.register_address_edt)
    EditText registerAddressEdt;


    @BindView(R.id.register_country_code_sp)
    Spinner registerCountryCodeSp;

    @OnClick(R.id.register_postcode_btn)
    void postcodeBtn() {
        redirectPostcodeActivity();
    }

    @OnClick({R.id.register_man_rb, R.id.register_woman_rb})
    void genderCheck(View view) {
        presenter.setGender(view.getId());
    }

    @OnClick(R.id.register_use_agree_rb)
    void useAgreeCheck() {
        presenter.setUseAgree();
    }

    @OnClick(R.id.register_privacy_agree_rb)
    void privacyAgreeCheck() {
        presenter.setPrivacyAgree();
    }

    @OnClick(R.id.register_email_valid_btn)
    void registerEmailValidBtn() {
        presenter.checkValidEmail(registerEmailEdt.getText().toString());
    }

    @OnClick(R.id.register_confirm_btn)
    void registerBtn() {
        presenter.setName(registerNameEdt.getText().toString());
        presenter.setPassword(registerPasswordEdt.getText().toString());
        presenter.setPasswordConfirm(registerPasswordConfirmEdt.getText().toString());
        presenter.setBirthDay(registerYearEdt.getText().toString(), registerMonthEdt.getText().toString(), registerDayEdt.getText().toString());
        presenter.setAddress(registerPostcodeEdt.getText().toString() + " " + registerAddressEdt.getText().toString());
        presenter.signUp();
    }

    @OnClick(R.id.register_phone_num_btn)
    void phoneNumBtn() {
        presenter.requestAuthNum(registerPhoneNumEdt.getText().toString());
    }

    @OnClick(R.id.register_auth_num_btn)
    void authNumBtn() {
        presenter.checkAuthNum(registerAuthNumEdt.getText().toString());
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
        registerCountryCodeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.setCountryCode(adapterView.getItemAtPosition(i).toString().substring(0, 3));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void redirectLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void redirectPostcodeActivity() {
        Intent intent = new Intent(this, PostcodeActivity.class);
        startActivityForResult(intent, 101);
    }

    @Override
    public void enableBtn(int id) {
        findViewById(id).setEnabled(true);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                Log.d("BEOM123", ((PostDto) data.getParcelableExtra("post")).getZipNo());
                postDto = (PostDto) data.getParcelableExtra("post");
                registerPostcodeEdt.setText(postDto.getZipNo() + " " + postDto.getLnmAdres());
            }
        }
    }
}
