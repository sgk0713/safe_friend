package com.seoul_app_contest.safe_friend;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.seoul_app_contest.safe_friend.Register.RegisterActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class UserModel {
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";
    private static final String WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps";
    private static final String WELCOME_TITLE_KEY = "welcome_title";

    String uid;
    String email;
    String password;
    String passwordConfirm;
    String name;
    String birthDay;
    String gender;
    String address;
    String phoneNum;
    String authNum;
    int state;
    boolean checkUseAgree = false;
    boolean checkPrivacyAgree = false;

    boolean isMan = false;
    boolean isWoman = false;

    private boolean checkEmail = false;

    private FirebaseAuth firebaseAuth;
    private CollectionReference firestore;
    private FirebaseRemoteConfig firebaseRemoteConfig;

    public UserModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance().collection("USERS");
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    }

    public void initRemote(final RemoteCallbackListener remoteCallbackListener){
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_default);

        firebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseRemoteConfig.activateFetched();
                        } else {

                        }
                        displayWelcomeMessage(remoteCallbackListener);
                    }
                });
    }

     private void displayWelcomeMessage(RemoteCallbackListener remoteCallbackListener) {
        // [START get_config_values]
        String welcomeMessage = firebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY);
        String welcomeTitle = firebaseRemoteConfig.getString(WELCOME_TITLE_KEY);
        Log.d("BEOM123", "title : " + welcomeTitle);
        // [END get_config_values]
        if (firebaseRemoteConfig.getBoolean(WELCOME_MESSAGE_CAPS_KEY)) {
            remoteCallbackListener.on(welcomeTitle, welcomeMessage);
        } else {
            remoteCallbackListener.off();
        }
    }

    public interface RemoteCallbackListener{
        void on(String title, String msg);
        void off();
    }

    public boolean existCurrentUser() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public boolean checkProtector() {
        return false;
    }

    public void setUserData(String email, String password, String name, String birthDay, String gender, String address, String phoneNum) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public boolean checkNotNull() {
        return email != null && password != null && name != null &&
                birthDay != null && gender != null && address != null
                && phoneNum != null && authNum != null;
    }

    public boolean checkPasswordConfirm(){
        return password.equals(passwordConfirm);
    }

    public boolean checkAuthNum(String authNum){
        return this.authNum.equals(authNum);
    }

    public boolean checkEmailType(String email) {
        return Pattern.matches("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", email);
    }

    public void checkEmail(final String email, final CheckEmailCallbackListener checkEmailCallbackListener) {
        firestore.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean flag = false;
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    if (email.equals(documentSnapshot.get("email"))){
                        flag = true;
                    }
                }
                if (flag){
                    checkEmailCallbackListener.exist();
                }else {
                    checkEmailCallbackListener.notExist();
                }
            }
        });
    }

    public interface CheckEmailCallbackListener{
        void exist();
        void notExist();
    }

    public boolean checkPhoneNumType(String phoneNum) {
        return Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phoneNum);
    }

    public void signIn(String email, String password, final SignInCallbackListener signInCallbackListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signInCallbackListener.onSuccess();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signInCallbackListener.onFail(e.toString());
            }
        });
    }

    public interface SignInCallbackListener {
        void onSuccess();
        void onFail(String e);
    }

    public void signUp(final SignUpCallbackListener signUpCallbackListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signUpCallbackListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signUpCallbackListener.onFail();
            }
        });
    }

    public interface SignUpCallbackListener {
        void onSuccess();
        void onFail();
    }

    public void sendAuthNum(String phoneNum, final SendAuthNumCallbackListener sendAuthNumCallbackListener) {
        PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                sendAuthNumCallbackListener.onSuccess();
                authNum = phoneAuthCredential.getSmsCode();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d("PHONE_AUTH", "ERR : " + e);
                sendAuthNumCallbackListener.onFail(e.toString());
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

    public interface SendAuthNumCallbackListener{
        void onSuccess();
        void onFail(String e);
    }

    public void addFirestore(final AddFirestoreCallbackListener addFirestoreCallbackListener){
        String uid = firestore.getId();
        this.uid = uid;
        firestore.document(uid).set(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addFirestoreCallbackListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addFirestoreCallbackListener.onFail();
            }
        });
    }

    public interface AddFirestoreCallbackListener{
        void onSuccess();
        void onFail();
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAuthNum() {
        return authNum;
    }

    public void setCheckEmail(boolean checkEmail) {
        this.checkEmail = checkEmail;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public void setCheckUseAgree() {
        this.checkUseAgree = !this.checkUseAgree;
    }

    public void setCheckPrivacyAgree() {
        this.checkPrivacyAgree = !this.checkPrivacyAgree;
    }

    public void setMan() {
        isMan = true;
        isWoman = false;
        this.gender = "남자";
    }

    public void setWoman() {
        isWoman = true;
        isMan = false;
        this.gender = "여자";
    }

    public void setBirthDay(String year, String month, String day) {
        this.birthDay = year + "." + month + "." + day;
    }

    public void signOut(){
        firebaseAuth.signOut();
    }
}
