package com.seoul_app_contest.safe_friend.Main;

import com.seoul_app_contest.safe_friend.UserModel;

public class MainPresenter implements MainContract.Presenter {
    MainContract.View view;
    UserModel model;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    @Override
    public void signOut() {
        model.signOut();
    }
}
