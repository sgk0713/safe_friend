package com.seoul_app_contest.safe_friend.Splash;

public interface SplashContract {
    interface View {
        void redirectLoginActivity();
        void redirectMainActivity();
        void showDialog(String title, String msg);
    }
    interface Presenter{
        void checkRemote();
        void checkAutoLogin();
    }
}
