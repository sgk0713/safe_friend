package com.seoul_app_contest.safe_friend.Login;

public class LoginModel {
    private String email;
    private String password;

    void setUserData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    boolean existCurrentUser(){
        return true;
    }

    void requestFirebaseAuth(AuthCallback authCallback) {
        authCallback.onSuccessed(email, password);
        authCallback.onFailed();

    }


    interface AuthCallback{
        void onSuccessed(String email, String password);
        void onFailed();
    }
}
