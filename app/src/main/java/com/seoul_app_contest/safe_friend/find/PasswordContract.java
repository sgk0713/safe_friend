package com.seoul_app_contest.safe_friend.find;

public interface PasswordContract {
    interface View {
        void showToast(String msg);
    }
    interface Presenter{
        void findPassword(String name, String email);
    }
}
