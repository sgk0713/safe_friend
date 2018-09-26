package com.seoul_app_contest.safe_friend.Main;

public interface MainContract {
    interface View {
        void redirectCall();
        void redirectSearchPlaceActivity();
    }
    interface Presenter {
        void signOut();
    }
}
