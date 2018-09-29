package com.seoul_app_contest.safe_friend.waitingncomfirm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.google.firebase.firestore.FirebaseFirestore;
import com.seoul_app_contest.safe_friend.R;


public class WatingActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ConfirmationFragment mConfirmationFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        mConfirmationFragment = new ConfirmationFragment();

        if(isConfirmed()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmet, mConfirmationFragment).commit();
        }

    }

    boolean isConfirmed(){
        return false;
    }
}
