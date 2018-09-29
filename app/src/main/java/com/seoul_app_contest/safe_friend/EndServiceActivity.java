package com.seoul_app_contest.safe_friend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class EndServiceActivity extends AppCompatActivity {
    final String TYPE = "user";
    boolean click = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(TYPE.equals("user")) {
            setContentView(R.layout.activity_end_service_user);

            final View coView = findViewById(R.id.endServiceBottomBar);
            findViewById(R.id.bottomBarTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(click){
                        BottomSheetBehavior.from(coView).setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }else{
                        BottomSheetBehavior.from(coView).setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                    click = !click;
                }
            });

            BottomSheetBehavior.from(coView).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    // React to state change
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        click = !click;
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    // React to dragging events
                }
            });

        }
        else
            setContentView(R.layout.activity_end_service_follower);

    }
}
