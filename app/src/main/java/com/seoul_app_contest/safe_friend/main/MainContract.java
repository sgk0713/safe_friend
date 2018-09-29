package com.seoul_app_contest.safe_friend.main;

public interface MainContract {
    interface View {
        void redirectCall();
        void redirectSearchPlaceActivity();
        void showExplanationDialog();
        void setNavName(String name);
        void setNavEmail(String email);
    }
    interface Presenter {
        void showExplanationDialog();
        void setUserData();
        void signOut();
    }
}
