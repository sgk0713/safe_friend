package com.seoul_app_contest.safe_friend.profile;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {

    private ProfileContract.Presenter presenter;

    @BindView(R.id.profile_tb)
    Toolbar toolbar;
    @BindView(R.id.profile_name_tv)
    TextView nameTv;
    @BindView(R.id.profile_email_tv)
    TextView emailTv;
    @BindView(R.id.profile_gender_tv)
    TextView genderTv;
    @BindView(R.id.profile_address_tv)
    TextView addressTv;
    @BindView(R.id.profile_address_tv_)
    TextView addressTv_;
    @BindView(R.id.profile_phone_num_tv)
    TextView phoneNumTv;
    @BindView(R.id.profile_use_num_tv)
    TextView useNumTv;
    @BindView(R.id.profile_use_num_tv_)
    TextView useNumTv_;
    @BindView(R.id.profile_birthday_tv)
    TextView birthDayTv;
    @BindView(R.id.profile_sticker_num_tv)
    TextView stickerNumTv;
    @BindView(R.id.profile_sticker_ll)
    LinearLayout profileStickerLl;

    @OnClick(R.id.profile_prev_btn)
    void prev() {
        finish();
    }

    @OnClick(R.id.profile_withdrawal_btn)
    void withdrawalBtn() {
        showWithdrawalDialog();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    private void init() {
        presenter = new ProfilePresenter(this);
        ButterKnife.bind(this);
        presenter.setUserData();
        setSupportActionBar(toolbar);
    }

    @Override
    public void setName(String name) {
        nameTv.setText(name);
    }

    @Override
    public void setEmail(String email) {
        emailTv.setText(email);
    }

    @Override
    public void setGender(String gender) {
        genderTv.setText(gender);
    }

    @Override
    public void setAddress(String address) {
        addressTv.setText(address);
    }

    @Override
    public void setPhoneNum(String phoneNum) {
        phoneNumTv.setText(phoneNum);
    }

    @Override
    public void setUseNum(String useNum) {
        useNumTv.setText(useNum + "회");
    }

    @Override
    public void setBirthDay(String birthDay) {
        birthDayTv.setText(birthDay);
    }

    @Override
    public void showSticker() {
        profileStickerLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void setStickerNum(String stickerNum) {
        stickerNumTv.setText(stickerNum);
    }

    @Override
    public void changeSubTitle() {
        addressTv_.setText("관할구역");
        useNumTv_.setText("스카우트 활동");
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWithdrawalDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_withdrawal, null, false);
        final EditText withdrawalPasswordEdt = dialogView.findViewById(R.id.withdrawal_password_edt);
        Button withdrawalCancelBtn = dialogView.findViewById(R.id.withdrawal_cancel_btn);
        Button withdrawalConfirmBtn = dialogView.findViewById(R.id.withdrawal_confirm_btn);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(dialogView)
                .show();
        final AlertDialog alertDialog = builder.show();
        withdrawalCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        withdrawalConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.withdrawal(withdrawalPasswordEdt.getText().toString());
            }
        });
    }
}
