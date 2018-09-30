package com.seoul_app_contest.safe_friend.find;

public interface EmailFindContract {
    interface View {
        void showEmailDialog();
        void setEmail(String email);
    }
    interface Presenter {
        void requestAuthNum(String phoneNum);
        void checkAuthNum(String authNum);
        void findEmail(String name, String phoneNum);
    }
}
