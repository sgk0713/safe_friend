package com.seoul_app_contest.safe_friend.Login;

public interface LoginContract {
    interface View {
        void showErrorToast(String msg);
        void redirectMainActivity();
        void redirectRegisterActivity();
    }

    interface Presenter {
        void signIn(String email, String password);
    }
}
