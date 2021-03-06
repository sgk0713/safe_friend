package com.seoul_app_contest.safe_friend.splash;

public interface SplashContract {
    interface View {
        void redirectLoginActivity();
        void redirectMainActivity();
        void redirectProtectorMainActivity();
        void showDialog(String title, String msg);
        void redirectMapsActivity(String type);
    }
    interface Presenter{
        void checkRemote();
        void checkAutoLogin();
    }
}
