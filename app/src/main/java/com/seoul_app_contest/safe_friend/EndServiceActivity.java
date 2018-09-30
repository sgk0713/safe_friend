package com.seoul_app_contest.safe_friend;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.seoul_app_contest.safe_friend.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class EndServiceActivity extends AppCompatActivity {
    final String TYPE = "user";
    boolean click = false;
    private TimerTask mTimerTask;
    private Timer mTimer;
    Activity mActivity = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
                finish();
                this.cancel();
            }
        };
        mTimer = new Timer();

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

            findViewById(R.id.endServiceGoodButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button)v;
                    button.setTextColor(Color.parseColor("#000000"));
                    mTimer.schedule(mTimerTask,2000);
                    findViewById(R.id.endServiceKindButton).setClickable(false);
                    findViewById(R.id.endServiceSmileButton).setClickable(false);
                }
            });
            findViewById(R.id.endServiceKindButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button)v;
                    button.setTextColor(Color.parseColor("#000000"));
                    mTimer.schedule(mTimerTask,2000);
                    findViewById(R.id.endServiceGoodButton).setClickable(false);
                    findViewById(R.id.endServiceSmileButton).setClickable(false);
                }
            });
            findViewById(R.id.endServiceSmileButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button)v;
                    button.setTextColor(Color.parseColor("#000000"));
                    mTimer.schedule(mTimerTask,2000);
                    findViewById(R.id.endServiceKindButton).setClickable(false);
                    findViewById(R.id.endServiceGoodButton).setClickable(false);
                }
            });

        }
        else {
            setContentView(R.layout.activity_end_service_follower);
            mTimer.schedule(mTimerTask,2000);
        }

    }
}
