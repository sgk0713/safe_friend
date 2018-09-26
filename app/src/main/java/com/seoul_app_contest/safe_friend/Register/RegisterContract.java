package com.seoul_app_contest.safe_friend.Register;

public interface RegisterContract {
    interface View {
        void redirectMainActivity();
        void enableBtn(int id);
        void showToast(String msg);
    }
    interface Presenter {
        void setUserData(String email, String password, String name, String gender, String birthDay, String address, String phoneNum);
        void checkValidEmail(String email);
        void requestAuthNum(String phoneNum);
        void checkAuthNum(String authNum);
        void setUseAgree();
        void setPrivacyAgree();
        void setGender(int id);
        void signUp();

    }
}
