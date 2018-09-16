package com.seoul_app_contest.safe_friend.Splash;

public interface SplashContract {
    interface View {
        void redirectLoginActivity();
        void redirectMainActivity();
    }
    interface Presenter{
        void checkAutoLogin();
    }
}
