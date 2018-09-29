package com.seoul_app_contest.safe_friend.protector_main;

public interface ProtectorMainContract {
    interface View {
        void setNavName(String name);
        void setNavEmail(String email);
        void redirectLoginActivity();
        void redirectProfileActivity();
    }
    interface Presenter {
        void setUserData();
        void signOut();
    }
}
