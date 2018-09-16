package com.seoul_app_contest.safe_friend.Register;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class RegisterModel {
     String email;
     String password;
     String name;
     String birthDay;
     String phoneNum;
     String authNum;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference firestore = FirebaseFirestore.getInstance().collection("USERS");

    public RegisterModel() {
    }

    public RegisterModel(String email, String password, String name, String birthDay, String phoneNum) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDay = birthDay;
        this.phoneNum = phoneNum;
    }

    void setData(String email, String password, String name, String birthDay) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDay = birthDay;
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
        if (this.authNum.equals(authNum)) {
            return true;
        } else {
            return false;
        }
    }

    void sendAuthNum(final CallbackListener callbackListener) {
        PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                callbackListener.onSuccess(phoneAuthCredential.getSmsCode());

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d("PHONE_AUTH", "ERR : " + e);
                callbackListener.onFail();

            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+82" + phoneNum.substring(1),
                60,
                TimeUnit.SECONDS,
                new RegisterActivity(),
                callbacks
        );
    }

    interface CallbackListener {
        void onSuccess(String authNum);

        void onSuccess();

        void onFail();
    }

    void registerToFirestore(final CallbackListener callbackListener) {
        firestore.add(new RegisterModel(email, password, name, birthDay, phoneNum)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                callbackListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callbackListener.onFail();
            }
        });
    }


    void register(final CallbackListener callbackListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                registerToFirestore(new CallbackListener() {

                    @Override
                    public void onSuccess(String authNum) {

                    }

                    @Override
                    public void onSuccess() {
                        callbackListener.onSuccess();
                    }

                    @Override
                    public void onFail() {
                        callbackListener.onFail();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
