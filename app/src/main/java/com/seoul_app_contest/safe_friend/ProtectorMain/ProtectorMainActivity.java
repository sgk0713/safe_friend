package com.seoul_app_contest.safe_friend.ProtectorMain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.seoul_app_contest.safe_friend.R;

import butterknife.BindView;

public class ProtectorMainActivity extends AppCompatActivity {
    @BindView(R.id.protector_location_tv)TextView protectorLocationTv;
    @BindView(R.id.protector_num_tv)TextView protectorNumTv;
    @BindView(R.id.protector_rv)RecyclerView protectorRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector_main);
    }
}
