package com.seoul_app_contest.safe_friend.protector_main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.RequestModel;
import com.seoul_app_contest.safe_friend.adapter.ProtectorRecyclerViewAdapter;
import com.seoul_app_contest.safe_friend.login.LoginActivity;
import com.seoul_app_contest.safe_friend.main.MainActivity;
import com.seoul_app_contest.safe_friend.profile.ProfileActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProtectorMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.protector_location_tv)TextView protectorLocationTv;
    @BindView(R.id.protector_num_tv)TextView protectorNumTv;
    @BindView(R.id.protector_rv)RecyclerView protectorRv;

    @BindView(R.id.protector_main_tb)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.protector_main_nv)NavigationView navigationView;
    @BindView(R.id.protector_main_drawer)DrawerLayout drawerLayout;

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


        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).findViewById(R.id.nav_logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProtectorMainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.nav_profile_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProtectorMainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
