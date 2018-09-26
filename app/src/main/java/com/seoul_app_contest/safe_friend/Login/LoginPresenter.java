package com.seoul_app_contest.safe_friend.Login;

import com.seoul_app_contest.safe_friend.UserModel;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private UserModel model;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        model = new UserModel();

    }


    @Override
    public void signIn(String email, String password) {
        if (email.length() != 0 && password.length() != 0) {
            model.signIn(email, password, new UserModel.SignInCallbackListener() {
                @Override
                public void onSuccess() {
                    view.redirectMainActivity();
                }

                @Override
                public void onFail(String e) {
                    view.showErrorToast("잘못된 정보입니다." + e);
                }
            });
        }else {
            view.showErrorToast("모든 정보를 입력해주세요.");
        }

    }
}
