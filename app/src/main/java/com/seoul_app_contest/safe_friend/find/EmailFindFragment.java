package com.seoul_app_contest.safe_friend.find;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.seoul_app_contest.safe_friend.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailFindFragment extends Fragment implements EmailFindContract.View{

    private EmailFindContract.Presenter presenter;

    @BindView(R.id.find_email_name_edt)EditText nameEdt;
    @BindView(R.id.find_email_phone_num_edt)EditText phoneNumEdt;
    @BindView(R.id.find_email_auth_num_edt)EditText authNumEdt;

    @OnClick(R.id.find_email_phone_num_btn)void phoneNumBtn(){
        presenter.requestAuthNum(phoneNumEdt.getText().toString());
    }
    @OnClick(R.id.find_email_auth_num_btn)void authNumBtn(){
        presenter.checkAuthNum(authNumEdt.getText().toString());
    }
    @OnClick(R.id.find_email_confirm_btn)void confirmBtn(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_find, null, false);
        ButterKnife.bind(view);
        presenter = new EmailFindPresenter(this);
        return view;
    }

    @Override
    public void showEmailDialog() {

    }

    @Override
    public void setEmail(String email) {

    }
}
