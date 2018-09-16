package com.seoul_app_contest.safe_friend;

import com.google.firebase.auth.FirebaseAuth;

public class UserModel {
    String email;
    String password;
    String name;
    String birthDay;
    String phoneNum;
    String authNum;

    private FirebaseAuth firebaseAuth;

    public UserModel() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean existCurrentUser(){
        return firebaseAuth.getCurrentUser() != null;
    }

    public boolean checkProtector(){
        return false;
    }
}
