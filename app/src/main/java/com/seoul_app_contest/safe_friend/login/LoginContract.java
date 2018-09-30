package com.seoul_app_contest.safe_friend.login;

public interface LoginContract {
    interface View {
        void showErrorToast(String msg);

        void redirectMainActivity();

        void redirectProtectorMainActivity();

        void redirectRegisterActivity();

        void changeLoginMode();

        void selectUserLogin();

        void selectProtectorLogin();
        void redirectFindActivity();
    }

    interface Presenter {
        void signIn(String email, String password);

        void changeLoginMode(int id);

    }
}
