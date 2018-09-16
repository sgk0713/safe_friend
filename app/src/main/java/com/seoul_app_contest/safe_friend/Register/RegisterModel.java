package com.seoul_app_contest.safe_friend.Register;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class RegisterModel {
    private String email;
    private String password;
    private String name;
    private String birthDay;
    private String phoneNum;
    private String authNum;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    void setData(String email, String password, String name, String birthDay, String phoneNum) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDay = birthDay;
        this.phoneNum = phoneNum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }

    boolean isValidAuthNum(String authNum) {
        if (this.authNum.equals(authNum)){
            return true;
        }else {
            return false;
        }
    }

    void sendAuthNum(final AuthCallbackListener callbackListener) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+082" + phoneNum,
                60,
                TimeUnit.SECONDS,
                (Executor) this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        callbackListener.onSuccess(phoneAuthCredential.getSmsCode());
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        callbackListener.onFail();
                    }
                }
        );
    }

    interface AuthCallbackListener {
        void onSuccess(String authNum);

        void onFail();
    }

    void registerToFirestore(){

    }

    void register(){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
