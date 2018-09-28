package com.seoul_app_contest.safe_friend.register;

public interface RegisterContract {
    interface View {
        void redirectLoginActivity();
        void redirectPostcodeActivity();
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
        void setBirthDay(String year, String month, String day);
        void setName(String name);
        void setPassword(String password);
        void setPasswordConfirm(String passwordConfirm);
        void setAddress(String address);
        void setCountryCode(String countryCode);

    }
}
