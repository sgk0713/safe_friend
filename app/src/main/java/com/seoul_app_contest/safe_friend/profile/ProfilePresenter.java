package com.seoul_app_contest.safe_friend.profile;

import com.seoul_app_contest.safe_friend.UserModel;
import com.seoul_app_contest.safe_friend.dto.UserDto;

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View view;
    UserModel model;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void setUserData() {
        view.setEmail(model.getCurrentUserEmail());
        model.isProtector(model.getCurrentUserEmail(), new UserModel.IsProtectorCallbackListener() {
            @Override
            public void exist() {
                view.changeSubTitle();
                view.showSticker();

                model.getCurrentUserData("PROTECTORS", new UserModel.GetCurrentUserCallbackListener() {
                    @Override
                    public void getName(String name) {
                        view.setName(name);
                    }

                    @Override
                    public void getGender(String gender) {
                        view.setGender(gender);
                    }

                    @Override
                    public void getBirthDay(String birthday) {
                        view.setBirthDay(birthday);
                    }

                    @Override
                    public void getAddress(String address) {
                        view.setAddress(address);
                    }

                    @Override
                    public void getPhoneNum(String phoneNum) {
                        view.setPhoneNum(phoneNum);
                    }

                    @Override
                    public void getUseNum(int useNum) {
                        view.setUseNum(String.valueOf(useNum));
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
                        view.setStickerNum("좋아요 " + userDto.getLikeNum() + "개  " + "친절해요 " + userDto.getKindNum() + "최고에요 " + userDto.getBestNum());
                    }
                });
            }

            @Override
            public void notExist() {
                model.getCurrentUserData("USERS", new UserModel.GetCurrentUserCallbackListener() {
                    @Override
                    public void getName(String name) {
                        view.setName(name);
                    }

                    @Override
                    public void getGender(String name) {
                        view.setGender(name);
                    }

                    @Override
                    public void getBirthDay(String name) {
                        view.setBirthDay(name);
                    }

                    @Override
                    public void getAddress(String name) {
                        view.setAddress(name);
                    }

                    @Override
                    public void getPhoneNum(String name) {
                        view.setPhoneNum(name);
                    }

                    @Override
                    public void getUseNum(int name) {
                        view.setUseNum(String.valueOf(name));
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
        });

    }

    @Override
    public void withdrawal(String password) {
        model.signIn(model.getCurrentUserEmail(), password, new UserModel.SignInCallbackListener() {
            @Override
            public void onSuccess() {
                model.withdrawalFirestore();
            }

            @Override
            public void onFail(String e) {
                view.showToast("탈퇴에 실패했습니다...");
            }
        });
    }
}
