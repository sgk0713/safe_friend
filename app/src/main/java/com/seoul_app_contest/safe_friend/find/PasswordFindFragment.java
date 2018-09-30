package com.seoul_app_contest.safe_friend.find;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordFindFragment extends Fragment implements PasswordContract.View {

    PasswordContract.Presenter presenter;

    @BindView(R.id.find_password_name_edt)EditText nameEdt;
    @BindView(R.id.find_password_email_edt)EditText emailEdt;
    @OnClick(R.id.find_password_confirm_btn)void passwordConfirmBtn() {
        presenter.findPassword(nameEdt.getText().toString(), emailEdt.getText().toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_find, null, false);
        ButterKnife.bind(this, view);
        presenter = new PasswordPresenter(this);
        return view;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
