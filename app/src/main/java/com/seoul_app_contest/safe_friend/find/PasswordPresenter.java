package com.seoul_app_contest.safe_friend.find;

import com.seoul_app_contest.safe_friend.UserModel;

public class PasswordPresenter implements PasswordContract.Presenter {

    PasswordContract.View view;
    UserModel model;

    public PasswordPresenter(PasswordContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void findPassword(String name, String email) {
        model.sendPasswordResetEmail(email, new UserModel.SendPasswordResetEmailCallbackListener() {
            @Override
            public void onSuccess() {
                view.showToast("이메일이 전송되었습니다.");
            }
        });
    }
}
