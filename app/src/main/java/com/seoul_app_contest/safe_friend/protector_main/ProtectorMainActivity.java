package com.seoul_app_contest.safe_friend.protector_main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.RequestModel;
import com.seoul_app_contest.safe_friend.adapter.ProtectorRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProtectorMainActivity extends AppCompatActivity {
    @BindView(R.id.protector_location_tv)TextView protectorLocationTv;
    @BindView(R.id.protector_num_tv)TextView protectorNumTv;
    @BindView(R.id.protector_rv)RecyclerView protectorRv;

    private ArrayList<RequestModel> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector_main);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        arrayList.add(new RequestModel("11:30", "gggg", "dd"));
        arrayList.add(new RequestModel("11:30", "gggg", "dd"));
        arrayList.add(new RequestModel("11:30", "gggg", "dd"));
        ProtectorRecyclerViewAdapter adapter = new ProtectorRecyclerViewAdapter(this, arrayList);
        protectorRv.setLayoutManager(new LinearLayoutManager(this));
        protectorRv.setAdapter(adapter);
    }
}
