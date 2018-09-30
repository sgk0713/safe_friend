package com.seoul_app_contest.safe_friend.protector_main;

import com.seoul_app_contest.safe_friend.UserModel;
import com.seoul_app_contest.safe_friend.dto.UserDto;

public class ProtectorMainPresenter implements ProtectorMainContract.Presenter {
    ProtectorMainContract.View view;
    UserModel model;

    public ProtectorMainPresenter(ProtectorMainContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void setUserData() {
        view.setNavEmail(model.getCurrentUserEmail());
        model.getCurrentUserData("PROTECTORS", new UserModel.GetCurrentUserCallbackListener() {
            @Override
            public void getName(String name) {
                view.setNavName(name);
            }

            @Override
            public void getGender(String gender) {

            }

            @Override
            public void getBirthDay(String birthday) {

            }

            @Override
            public void getAddress(String address) {

            }

            @Override
            public void getPhoneNum(String phoneNum) {

            }

            @Override
            public void getProfile(String profile) {

            }

            @Override
            public void getUseNum(int useNum) {

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
        view.redirectLoginActivity();
    }
}
