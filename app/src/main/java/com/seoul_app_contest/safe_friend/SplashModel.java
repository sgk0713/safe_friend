package com.seoul_app_contest.safe_friend;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class SplashModel {
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";
    private static final String WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps";
    private static final String WELCOME_TITLE_KEY = "welcome_title";
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseAuth firebaseAuth;

    public SplashModel() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void initRemote(final UserModel.RemoteCallbackListener remoteCallbackListener){
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

    private void displayWelcomeMessage(UserModel.RemoteCallbackListener remoteCallbackListener) {
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

}
