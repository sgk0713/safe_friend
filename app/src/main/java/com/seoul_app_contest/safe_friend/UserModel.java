package com.seoul_app_contest.safe_friend;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.seoul_app_contest.safe_friend.register.RegisterActivity;
import com.seoul_app_contest.safe_friend.dto.UserDto;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class UserModel {
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";
    private static final String WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps";
    private static final String WELCOME_TITLE_KEY = "welcome_title";

    int userType;
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
    String countryCode;
    boolean checkUseAgree = false;
    boolean checkPrivacyAgree = false;

    boolean isMan = false;
    boolean isWoman = false;

    private boolean checkEmail = false;

    private FirebaseAuth firebaseAuth;
    private CollectionReference firestore;
    private CollectionReference firestore_;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private StorageReference firestorage;

    public UserModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance().collection("USERS");
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    }

    public void initRemote(final RemoteCallbackListener remoteCallbackListener) {
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

    public interface RemoteCallbackListener {
        void on(String title, String msg);

        void off();
    }
    public String getCurrentUserEmail(){
        return firebaseAuth.getCurrentUser().getEmail();
    }
    public void getCurrentUserData(String coll, final GetCurrentUserCallbackListener getCurrentUserCallbackListener){
        firestore = FirebaseFirestore.getInstance().collection(coll);
        firestore.document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                UserDto userDto = task.getResult().toObject(UserDto.class);
                getCurrentUserCallbackListener.getName(userDto.getName());
                getCurrentUserCallbackListener.getGender(userDto.getGender());
                getCurrentUserCallbackListener.getAddress(userDto.getAddress());
                getCurrentUserCallbackListener.getBirthDay(userDto.getBirthDay());
                getCurrentUserCallbackListener.getPhoneNum(userDto.getPhoneNum());
                getCurrentUserCallbackListener.getProfile(userDto.getProfile());
                getCurrentUserCallbackListener.getUseNum(userDto.getUseNum());
                getCurrentUserCallbackListener.getLikeNum(userDto.getLikeNum());
                getCurrentUserCallbackListener.getKindNum(userDto.getKindNum());
                getCurrentUserCallbackListener.getBestNum(userDto.getBestNum());
                getCurrentUserCallbackListener.getDto(userDto);
            }
        });
    }

    public interface GetCurrentUserCallbackListener{
        void getName(String name);
        void getGender(String gender);
        void getBirthDay(String birthday);
        void getAddress(String address);
        void getPhoneNum(String phoneNum);
        void getProfile(String profile);
        void getUseNum(int useNum);
        void getLikeNum(int likeNum);
        void getKindNum(int kindNum);
        void getBestNum(int BsetNum);
        void getDto(UserDto userDto);
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
        Log.d("BEOM123", "email : " + email);
        Log.d("BEOM123", "password : " + password);
        Log.d("BEOM123", "name : " + name);
        Log.d("BEOM123", "birthDay : " + birthDay);
        Log.d("BEOM123", "gender : " + gender);
        Log.d("BEOM123", "address : " + address);
        Log.d("BEOM123", "phoneNum : " + phoneNum);
        Log.d("BEOM123", "authNum : " + authNum);
        Log.d("BEOM123", "checkUseAgree : " + checkUseAgree);
        Log.d("BEOM123", "checkPrivacyAgree : " + checkPrivacyAgree);
        return email != null && password != null && passwordConfirm != null && name != null &&
                birthDay != null && gender != null && address != null
                && phoneNum != null && authNum != null;
    }

    public boolean checkBirthDay() {
//        return Pattern.matches(" /^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[0-1])", birthDay);
        return Pattern.matches("^[1-2]{1}[0-9]{3}[0-1]{1}[0-9]{1}[0-3]{1}[0-9]{1}$", birthDay);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean checkAgree() {
        return checkUseAgree && checkPrivacyAgree;
    }

    public boolean checkPasswordLength() {
        return password.length() >= 6;
    }

    public boolean checkPasswordConfirm() {
        return password.equals(passwordConfirm);
    }

    public boolean checkAuthNum(String authNum) {
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
                    if (email.equals(documentSnapshot.get("email"))) {
                        flag = true;
                    }
                }
                if (flag) {
                    checkEmailCallbackListener.exist();
                } else {
                    checkEmailCallbackListener.notExist();
                }
            }
        });
    }

    public interface CheckEmailCallbackListener {
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

    public void protectorSignIn(String email, String password, final SignInCallbackListener signInCallbackListener) {
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

    public void isProtector(String email, final IsProtectorCallbackListener isProtectorCallbackListener){
        firestore_ = FirebaseFirestore.getInstance().collection("PROTECTORS");
        firestore_.whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty()){
                    isProtectorCallbackListener.notExist();
                }else {
                    isProtectorCallbackListener.exist();
                }
            }
        });
    }

    public interface IsProtectorCallbackListener{
        void exist();

        void notExist();
    }public void isUser(String email, final IsUserCallbackListener isUserCallbackListener){
        firestore = FirebaseFirestore.getInstance().collection("USERS");
        firestore.whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty()){
                    isUserCallbackListener.notExist();
                }else {
                    isUserCallbackListener.exist();
                }
            }
        });
    }

    public interface IsUserCallbackListener{
        void exist();

        void notExist();
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
                signUpCallbackListener.onFail(e.toString());
            }
        });
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public interface SignUpCallbackListener {
        void onSuccess();

        void onFail(String e);
    }

    public void sendAuthNum(String phoneNum, final SendAuthNumCallbackListener sendAuthNumCallbackListener) {
        Log.d("BEOM123", "countryCode : " + countryCode);

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

    public interface SendAuthNumCallbackListener {
        void onSuccess();

        void onFail(String e);
    }

    public void addFirestore(final AddFirestoreCallbackListener addFirestoreCallbackListener) {
        this.uid = firebaseAuth.getCurrentUser().getUid();
        firestore.document(uid).set(new UserDto(uid, email, name, birthDay, gender, address, phoneNum, 0)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addFirestoreCallbackListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addFirestoreCallbackListener.onFail(e.toString());
            }
        });
    }

    public interface AddFirestoreCallbackListener {
        void onSuccess();

        void onFail(String e);
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

    public void setPhoneNum(String phoneNum) {
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
        this.birthDay = year + "" + month + "" + day;
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserType() {
        return userType;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void withdrawalFirestore(){
        String uid = firebaseAuth.getCurrentUser().getUid();
        firestore.document(uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                firebaseAuth.getCurrentUser().delete();
                signOut();
            }
        });
    }

    public void updateUserData(String address, String phoneNum) {
        firestore.document(firebaseAuth.getCurrentUser().getUid()).update("address", address);
        firestore.document(firebaseAuth.getCurrentUser().getUid()).update("phoneNum", phoneNum);
    }


    public void findUserEmail(String name, String phoneNum, final FindUserEmailCallbackListener findUserEmailCallbackListener) {
        firestore.whereEqualTo("name", name).whereEqualTo("phoneNum", phoneNum).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty()) {
                    findUserEmailCallbackListener.onFail();
                }else {

                    findUserEmailCallbackListener.onSuccess(task.getResult().getDocuments().get(0).getString("email"));
                }
            }
        });
    }

    public interface FindUserEmailCallbackListener{
        void onSuccess(String email);
        void onFail();
    }

    public void setUserProfile(byte[] bytes, final SetUserProfileCallbackListener setUserProfileCallbackListener){
        firestorage = FirebaseStorage.getInstance().getReference().child("profile/"+firebaseAuth.getCurrentUser().getUid());
        firestorage.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                firestore.document(firebaseAuth.getCurrentUser().getUid()).update("profile", taskSnapshot.getDownloadUrl().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setUserProfileCallbackListener.onSuccess(taskSnapshot.getDownloadUrl().toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                setUserProfileCallbackListener.onFail();
            }
        });
    }

    public interface SetUserProfileCallbackListener{
        void onSuccess(String url);
        void onFail();
    }


    public void sendPasswordResetEmail(String email, final SendPasswordResetEmailCallbackListener sendPasswordResetEmailCallbackListener){
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                sendPasswordResetEmailCallbackListener.onSuccess();
            }
        });
    }

    public interface SendPasswordResetEmailCallbackListener{
        void onSuccess();
    }
}
