package com.seoul_app_contest.safe_friend.Splash;

import com.seoul_app_contest.safe_friend.UserModel;

public class SplashPresenter implements SplashContract.Presenter {
    SplashContract.View view;
    UserModel model;

    public SplashPresenter(SplashContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    @Override
    public void checkAutoLogin() {
        if (model.existCurrentUser()){
            view.redirectMainActivity();
        }else {
            view.redirectLoginActivity();
        }
    }
}
