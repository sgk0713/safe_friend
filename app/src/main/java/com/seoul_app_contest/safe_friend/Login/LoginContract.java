package com.seoul_app_contest.safe_friend.Login;

public interface LoginContract {
    interface View {
        void showErrorToast();
        void redirectMainActivity();
        void redirectRegisterActivity();
    }

    interface Presenter {
        void setUserData(String email, String password);
        void autoLogin();
        void login();
    }
}
