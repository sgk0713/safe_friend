package com.seoul_app_contest.safe_friend.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.login.LoginActivity;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.dto.PostDto;
import com.seoul_app_contest.safe_friend.map.SpinnerAdapter;
import com.seoul_app_contest.safe_friend.postcode.PostcodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//
public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    PostDto postDto;
//    @BindView(R.id.register_logo_tv)TextView logoTv;

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
//    @BindView(R.id.register_address_edt)
//    EditText registerAddressEdt;
    @BindView(R.id.register_location_sp)Spinner registerLocationSp;

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
        String month = registerMonthEdt.getText().toString();
        String day = registerDayEdt.getText().toString();
        if (month.length() == 1) {
            month = "0"+month;
        }
        if (day.length() == 1) {
            day = "0"+day;
        }
        presenter.setBirthDay(registerYearEdt.getText().toString(), month, day);
//        presenter.setAddress(registerPostcodeEdt.getText().toString() + " " + registerAddressEdt.getText().toString());
        presenter.setAddress(registerPostcodeEdt.getText().toString());
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

    @OnClick(R.id.register_use_agree_ib)void useAgreeIb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("이용약관동의")
                .setMessage("~~")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }
    @OnClick(R.id.register_privacy_agree_ib)void privacyAgreeIb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("개인정보활용동의")
                .setMessage("~~")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
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
//        final SpannableStringBuilder sp = new SpannableStringBuilder("세이프랜드");
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.title_green)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.title_blue)), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new ForegroundColorSpan(getColor(R.color.mainColor)), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        logoTv.append(sp);

//        registerCountryCodeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                presenter.setCountryCode(adapterView.getItemAtPosition(i).toString().substring(0, 3));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        String[] data = {"동작구청", "서대문구청", "성동구청", "은평구청"};
        SpinnerAdapter adapter = new SpinnerAdapter(this, data);
        registerLocationSp.setAdapter(adapter);
        registerLocationSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.setLocation(adapterView.getItemAtPosition(i).toString());
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
