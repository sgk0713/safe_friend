package com.seoul_app_contest.safe_friend;

import android.os.Debug;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoul_app_contest.safe_friend.adapter.StationRecyclerViewAdapter;
import com.seoul_app_contest.safe_friend.dto.StationDto;
import com.seoul_app_contest.safe_friend.fragment.RecentListFragment;
import com.seoul_app_contest.safe_friend.fragment.SearchStationFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.spec.ECField;
import java.util.ArrayList;


public class SearchPlaceActivity extends AppCompatActivity implements Serializable{
    private final String SERVICE_KEY = "716271596273676b39356d6e6c7851";

//    private final int BUS = 0;
//    private final int SUBWAY = 1;

    RecentListFragment recentListFragment;
    SearchStationFragment searchStationFragment;

//    StationRecyclerViewAdapter stationRecyclerViewAdapter;

    ImageView searchImageView;
    EditText searchEditText;
    RecyclerView recyclerView;

    private boolean isSearching = false;

    ArrayList<StationDto> busInfoArray = new ArrayList<>();
    ArrayList<StationDto> resultArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        searchImageView = findViewById(R.id.activity_search_place_search_iv);
        searchEditText = findViewById(R.id.activity_search_place_search_edt);
//        recyclerView = findViewById(R.id.activity_search_place_rv);

        recentListFragment = new RecentListFragment();
        searchStationFragment = new SearchStationFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.activity_search_place_fl, recentListFragment).commit();

//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        stationRecyclerViewAdapter = new StationRecyclerViewAdapter(getApplicationContext(), resultArray, BUS);
//        recyclerView.setAdapter(stationRecyclerViewAdapter);

        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(searchStationFragment);
            }
        });

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSearching){
                    switchFragment(recentListFragment);
                }else{
                    switchFragment(searchStationFragment);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(isSearching) {
            switchFragment(recentListFragment);
        }else{
            super.onBackPressed();
        }
    }

    void switchFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_search_place_fl, frag).commit();

        if(frag == searchStationFragment){
            searchImageView.setImageResource(android.R.drawable.ic_menu_revert);
            Bundle bundle = new Bundle();
            bundle.putSerializable("busInfoArray", busInfoArray);
            searchStationFragment.setArguments(bundle);
            isSearching = true;
        }else if(frag == recentListFragment){
            searchImageView.setImageResource(android.R.drawable.ic_menu_search);
            isSearching = false;
        }
    }
}


