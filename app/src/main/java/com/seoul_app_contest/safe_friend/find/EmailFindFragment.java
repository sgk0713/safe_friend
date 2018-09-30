package com.seoul_app_contest.safe_friend.find;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailFindFragment extends Fragment implements EmailFindContract.View {

    private EmailFindContract.Presenter presenter;
    private TextView emailTv;
    TextView textView;
    @BindView(R.id.find_email_name_edt)
    EditText nameEdt;
    @BindView(R.id.find_email_phone_num_edt)
    EditText phoneNumEdt;
    @BindView(R.id.find_email_auth_num_edt)
    EditText authNumEdt;

    @OnClick(R.id.find_email_phone_num_btn)
    void phoneNumBtn() {
        presenter.requestAuthNum(phoneNumEdt.getText().toString());
    }

    @OnClick(R.id.find_email_auth_num_btn)
    void authNumBtn() {
        presenter.checkAuthNum(authNumEdt.getText().toString());
    }

    @OnClick(R.id.find_email_confirm_btn)
    void confirmBtn() {
        presenter.findEmail(nameEdt.getText().toString(), phoneNumEdt.getText().toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_find, null, false);
        ButterKnife.bind(this, view);
        presenter = new EmailFindPresenter(this);
        return view;
    }

    @Override
    public void showEmailDialog() {
        View dialogView = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_email_confirm, null, false);
        textView = dialogView.findViewById(R.id.dialog_tv);
        emailTv = dialogView.findViewById(R.id.dialog_email_tv);
        Button dialogConfirmBtn = dialogView.findViewById(R.id.dialog_email_confirm_btn);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this.getContext());
        builder.setView(dialogView);
        final android.support.v7.app.AlertDialog alertDialog = builder.show();
        dialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    @Override
    public void setEmail(String email) {
        emailTv.setText(email);
    }

    @Override
    public void setConfirmEmail(String msg) {
        textView.setText(msg);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
