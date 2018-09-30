package com.seoul_app_contest.safe_friend.protector_main;

public interface ProtectorMainContract {
    interface View {
        void setNavName(String name);
        void setNavEmail(String email);
        void redirectLoginActivity();
        void redirectProfileActivity();
        void setProtectorLocation(String location);
        void setProtectorNum(String num);
        void setNavProfile(String url);

    }
    interface Presenter {
        void setUserData();
        void signOut();
    }
}
