package com.seoul_app_contest.safe_friend.waitingncomfirm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.seoul_app_contest.safe_friend.R;

import java.util.Timer;
import java.util.TimerTask;


public class WatingActivity extends AppCompatActivity {
    FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        fm = getFragmentManager();

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                ComfirmationFragmet mComfirmationFragmet = new ComfirmationFragmet();
                FragmentTransaction tran = fm.beginTransaction();
                tran.replace(R.id.mainFragmet,mComfirmationFragmet,"");
                tran.commit();
                this.cancel();
            }
        };

        Timer timer = new Timer();
        timer.schedule(tt, 10000, 10000);
    }
}
