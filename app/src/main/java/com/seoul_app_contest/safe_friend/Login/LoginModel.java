package com.seoul_app_contest.safe_friend.Login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel {
    private String email;
    private String password;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    void setUserData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    boolean existCurrentUser(){
        return firebaseAuth.getCurrentUser() != null;
    }

    void requestFirebaseAuth(final AuthCallback authCallback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                authCallback.onSuccess(email, password);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authCallback.onFail();
            }
        });

    }

    void checkUserType(){
        //관리자인지 일반 유저인지 체크
    }

    interface AuthCallback{
        void onSuccess(String email, String password);
        void onFail();
    }
}
