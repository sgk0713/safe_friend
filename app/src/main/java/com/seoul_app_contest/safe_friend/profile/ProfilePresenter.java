package com.seoul_app_contest.safe_friend.profile;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.UserModel;
import com.seoul_app_contest.safe_friend.dto.PostDto;
import com.seoul_app_contest.safe_friend.dto.UserDto;

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View view;
    UserModel model;

    boolean isPhoneAuth = true;

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
                view.hideModifyBtn();
                view.hideWithdrawalBtn();
                view.hideAddress();
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
                    public void getProfile(String profile) {
                        view.setProfile(profile);
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
                    public void getLocation(String location) {
                        view.setLocation(location);
                    }

                    @Override
                    public void getDto(UserDto userDto) {
                        view.setStickerNum("좋아요 " + userDto.getLikeNum() + "개  " + "친절해요 " + userDto.getKindNum() + "개 " + "최고에요 " + userDto.getBestNum() + "개");
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
                    public void getProfile(String profile) {
                        view.setProfile(profile);
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
                    public void getLocation(String location) {
                        view.setLocation(location);
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
                view.redirectLoginActivity();
                model.withdrawalFirestore();
            }

            @Override
            public void onFail(String e) {
                view.showToast("탈퇴에 실패했습니다...");
            }
        });
    }

    @Override
    public void modifyMode() {
        view.modifyMode();
    }

    @Override
    public void showMode() {
        view.showMode();
    }

    @Override
    public void requestAuthNum(String phoneNum) {
        isPhoneAuth = false;
        if (model.checkPhoneNumType(phoneNum)) {
            view.showToast("요청중..");
            model.sendAuthNum(phoneNum, new UserModel.SendAuthNumCallbackListener() {
                @Override
                public void onSuccess() {
                    view.showToast("문자메시지가 전송되었습니다.");
                }

                @Override
                public void onFail(String e) {
                    view.showToast("문자메시지 발송에 실패했습니다.");
                }
            });
        }else {

        }

    }

    @Override
    public void checkAuthNum(String authNum) {
        if (model.checkAuthNum(authNum)) {
            view.showToast("확인되었습니다.");
            isPhoneAuth = true;
        } else {
            view.showToast("올바른 인증번호를 입력해주세요.");
        }
    }

    @Override
    public void modifyProfile() {
        if (isPhoneAuth) {

            model.updateUserData(model.getAddress(), model.getPhoneNum());
            view.showToast("수정되었습니다.");
            view.showMode();
        } else {
            view.showToast("전화번호 인증을 완료해주세요.");
        }
    }

    @Override
    public void setModifyAddress(PostDto postDto) {
        view.changeAddress(postDto.getLnmAdres());
    }

    @Override
    public void setModifyAddress(String modifyAddress) {
        model.setAddress(modifyAddress);
    }

    @Override
    public void setModifyPhoneNum(String phoneNum) {
        model.setPhoneNum(phoneNum);
    }

    @Override
    public void setProfile(byte[] bytes) {
        model.setUserProfile(bytes, new UserModel.SetUserProfileCallbackListener() {
            @Override
            public void onSuccess(String url) {
                view.setProfile(url);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
