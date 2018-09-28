package com.seoul_app_contest.safe_friend.Login;

public interface LoginContract {
    interface View {
        void showErrorToast(String msg);

        void redirectMainActivity();

        void redirectRegisterActivity();

        void changeLoginMode();

        void selectUserLogin();
        void selectProtectorLogin();
    }

    interface Presenter {
        void signIn(String email, String password);

        void changeLoginMode(int id);

    }
}
