package com.seoul_app_contest.safe_friend.Register;

public interface RegisterContract {
    interface View {
        void redirectMainActivity();
        void showErrorToast(String msg);
    }
    interface Presenter {
        void setUserData(String email, String password, String name, String birthDay, String phoneNum);
        void requestAuthNum(String phoneNum);
        void checkAuthNum(String authNum);
        void addFirestore();
    }
}
