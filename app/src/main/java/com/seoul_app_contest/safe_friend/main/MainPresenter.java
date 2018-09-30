package com.seoul_app_contest.safe_friend.main;

import com.seoul_app_contest.safe_friend.UserModel;
import com.seoul_app_contest.safe_friend.dto.UserDto;

public class MainPresenter implements MainContract.Presenter {
    MainContract.View view;
    UserModel model;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    @Override
    public void showExplanationDialog() {
        view.showExplanationDialog();
    }

    @Override
    public void setUserData() {
        view.setNavEmail(model.getCurrentUserEmail());
        model.getCurrentUserData("USERS", new UserModel.GetCurrentUserCallbackListener() {
            @Override
            public void getName(String name) {
                view.setNavName(name);
            }

            @Override
            public void getGender(String name) {

            }

            @Override
            public void getBirthDay(String name) {

            }

            @Override
            public void getAddress(String name) {

            }

            @Override
            public void getPhoneNum(String name) {

            }

            @Override
            public void getProfile(String profile) {
view.setNavProfile(profile);
            }

            @Override
            public void getUseNum(int name) {

            }

            @Override
            public void getLikeNum(int likeNum) {

            }

            @Override
            public void getKindNum(int kindNum) {

            }

            @Override
            public void getBestNum(int BsetNum) {

            }

            @Override
            public void getDto(UserDto userDto) {

            }
        });
    }

    @Override
    public void signOut() {
        model.signOut();
    }
}
