package com.seoul_app_contest.safe_friend.profile;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.dto.PostDto;
import com.seoul_app_contest.safe_friend.login.LoginActivity;
import com.seoul_app_contest.safe_friend.postcode.PostcodeActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {

    private ProfileContract.Presenter presenter;
    private byte[] imgData;

    @BindView(R.id.profile_address_ll)LinearLayout addressLl;

    @BindView(R.id.profile_tb)
    Toolbar toolbar;
    @BindView(R.id.profile_iv)ImageView profileIv;
    @BindView(R.id.profile_name_tv)
    TextView nameTv;
    @BindView(R.id.profile_email_tv)
    TextView emailTv;
    @BindView(R.id.profile_gender_tv)
    TextView genderTv;
    @BindView(R.id.profile_address_tv)
    EditText addressTv;
    @BindView(R.id.profile_address_tv_)
    TextView addressTv_;
    @BindView(R.id.profile_phone_num_tv)
    EditText phoneNumTv;
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
    @BindView(R.id.profile_set_cl)
    ConstraintLayout profileSetCl;

    @BindView(R.id.profile_withdrawal_btn)
    Button withdrawalBtn;

    @BindView(R.id.profile_address_btn)
    Button profileAddressBtn;
    @BindView(R.id.profile_phone_num_btn)
    Button profilePhoneNumBtn;
    @BindView(R.id.profile_auth_num_ll)
    LinearLayout profileAuthNumLl;

    @BindView(R.id.profile_auth_num_edt)
    EditText authNumEdt;

    @OnClick(R.id.profile_set_cl)
    void setProfile() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("접근 권한이 필요해요")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();


    }

    @OnClick(R.id.profile_prev_btn)
    void prev() {
        finish();
    }

    @OnClick(R.id.profile_phone_num_btn)
    void phoneNumBtn() {
        presenter.requestAuthNum(phoneNumTv.getText().toString());
        presenter.setModifyPhoneNum(phoneNumTv.getText().toString());
    }

    @OnClick(R.id.profile_auth_num_btn)
    void authNumBtn() {
        presenter.checkAuthNum(authNumEdt.getText().toString());
    }

    boolean isModfiyMode = false;

    @OnClick(R.id.profile_withdrawal_btn)
    void withdrawalBtn() {
        if (isModfiyMode) {
            presenter.setModifyAddress(addressTv.getText().toString());
            presenter.modifyProfile();
        } else {
            showWithdrawalDialog();
        }

    }

    @BindView(R.id.profile_modify_btn)
    Button modifyBtn;

    @OnClick(R.id.profile_modify_btn)
    void modifyBtn() {
        if (isModfiyMode) {
            presenter.showMode();
        } else {
            presenter.modifyMode();
        }
        isModfiyMode = !isModfiyMode;
    }

    @OnClick(R.id.profile_address_btn)
    void addressBtn() {
        redirectPostcodeActivity();
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
    public void setProfile(String url) {
        Glide.with(this).load(url).apply(new RequestOptions().circleCrop()).into(profileIv);
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
        final SpannableStringBuilder sp = new SpannableStringBuilder(stickerNum);
        sp.setSpan(new ForegroundColorSpan(getColor(R.color.mainColor)), stickerNum.indexOf("좋"), stickerNum.indexOf("좋") + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getColor(R.color.mainColor)), stickerNum.indexOf("친"), stickerNum.indexOf("친") + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getColor(R.color.mainColor)), stickerNum.indexOf("최"), stickerNum.indexOf("최") + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stickerNumTv.setText(sp);
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
        ImageButton withdrawalCancelBtn = dialogView.findViewById(R.id.withdrawal_cancel_btn);
        Button withdrawalConfirmBtn = dialogView.findViewById(R.id.withdrawal_confirm_btn);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(dialogView);
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

    @Override
    public void hideWithdrawalBtn() {
        withdrawalBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideModifyBtn() {
        modifyBtn.setVisibility(View.GONE);
    }

    @Override
    public void modifyMode() {
        withdrawalBtn.setText("확인");
        phoneNumTv.setEnabled(true);
        profilePhoneNumBtn.setVisibility(View.VISIBLE);
        profileAddressBtn.setVisibility(View.VISIBLE);
        profileAuthNumLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMode() {
        withdrawalBtn.setText("회원탈퇴");
        addressTv.setEnabled(false);
        phoneNumTv.setEnabled(false);
        profilePhoneNumBtn.setVisibility(View.GONE);
        profileAddressBtn.setVisibility(View.GONE);
        profileAuthNumLl.setVisibility(View.GONE);
    }

    @Override
    public void redirectPostcodeActivity() {
        Intent intent = new Intent(this, PostcodeActivity.class);
        startActivityForResult(intent, 102);
    }

    @Override
    public void changeAddress(String address) {
        addressTv.setEnabled(true);
        addressTv.setText(address);
    }

    @Override
    public void hideAddress() {
        addressLl.setVisibility(View.GONE);
    }

    @Override
    public void redirectLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendPicture(Uri imgUri) {
        String imagePath = getRealPathFromURI(imgUri);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        imgData = data;
        presenter.setProfile(data);
    }

    private String getRealPathFromURI(Uri imgUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imgUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 102) {
                presenter.setModifyAddress((PostDto) data.getParcelableExtra("post"));
            } else if (requestCode == 1000) {
                sendPicture(data.getData());
            }
        }
    }
}
