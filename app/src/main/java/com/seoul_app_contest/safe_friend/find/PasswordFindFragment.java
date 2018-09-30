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

public class PasswordFindFragment extends Fragment {

    @BindView(R.id.find_password_name_edt)EditText nameEdt;
    @BindView(R.id.find_password_email_edt)EditText emailEdt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_find, null, false);
        return view;
    }
}
