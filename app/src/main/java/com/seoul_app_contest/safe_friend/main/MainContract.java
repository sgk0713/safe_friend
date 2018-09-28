package com.seoul_app_contest.safe_friend.main;

public interface MainContract {
    interface View {
        void redirectCall();
        void redirectSearchPlaceActivity();
        void showExplanationDialog();
    }
    interface Presenter {
        void showExplanationDialog();
        void signOut();
    }
}
