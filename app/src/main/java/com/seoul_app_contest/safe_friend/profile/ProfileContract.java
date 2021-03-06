package com.seoul_app_contest.safe_friend.profile;

import com.seoul_app_contest.safe_friend.dto.PostDto;

public interface ProfileContract {
    interface View {
        void setProfile(String url);
        void setName(String name);
        void setEmail(String email);
        void setGender(String gender);
        void setAddress(String address);
        void setPhoneNum(String phoneNum);
        void setUseNum(String useNum);
        void setLocation(String location);
        void setBirthDay(String birthDay);
        void showSticker();
        void setStickerNum(String stickerNum);
        void changeSubTitle();
        void showToast(String msg);
        void showWithdrawalDialog();
        void hideWithdrawalBtn();
        void hideModifyBtn();
        void modifyMode();
        void showMode();
        void redirectPostcodeActivity();
        void changeAddress(String address);
        void hideAddress();

        void redirectLoginActivity();
    }
    interface Presenter{
        void setUserData();
        void withdrawal(String password);
        void modifyMode();
        void showMode();
        void requestAuthNum(String phoneNum);
        void checkAuthNum(String authNum);
        void modifyProfile();
        void setModifyAddress(PostDto postDto);
        void setModifyAddress(String modifyAddress);
        void setModifyPhoneNum(String phoneNum);

        void setProfile(byte[] bytes);
    }
}
