package com.seoul_app_contest.safe_friend.profile;

public interface ProfileContract {
    interface View {
        void setName(String name);
        void setEmail(String email);
        void setGender(String gender);
        void setAddress(String address);
        void setPhoneNum(String phoneNum);
        void setUseNum(String useNum);
        void setBirthDay(String birthDay);
        void showSticker();
        void setStickerNum(String stickerNum);
        void changeSubTitle();
        void showToast(String msg);
        void showWithdrawalDialog();
    }
    interface Presenter{
        void setUserData();
        void withdrawal(String password);
    }
}
