package com.seoul_app_contest.safe_friend;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.seoul_app_contest.safe_friend.dto.StationDto;
import com.seoul_app_contest.safe_friend.fragment.RecentListFragment;
import com.seoul_app_contest.safe_friend.fragment.SearchStationFragment;

import java.io.Serializable;
import java.util.ArrayList;


public class SearchPlaceActivity extends AppCompatActivity implements Serializable{
    private final String TAG = "DEBUGGING_TEST";
    public static Activity _SearchPlaceActivity;
    RecentListFragment recentListFragment;
    SearchStationFragment searchStationFragment;

    ImageView searchImageView;
    EditText searchEditText;
    RecyclerView recyclerView;

    private boolean isSearching;

    ArrayList<StationDto> recentListArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        _SearchPlaceActivity = SearchPlaceActivity.this;

        isSearching = false;

        searchImageView = findViewById(R.id.activity_search_place_search_iv);
        searchEditText = findViewById(R.id.activity_search_place_search_edt);
        recyclerView = findViewById(R.id.activity_search_place_rv);

        recentListFragment = new RecentListFragment();
        searchStationFragment = new SearchStationFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.activity_search_place_fl, recentListFragment).commit();

//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        lastStationRecyclerViewAdapter = new StationRecyclerViewAdapter(getApplicationContext(), recentListArray, -1);
//        recyclerView.setAdapter(lastStationRecyclerViewAdapter);

        searchEditText.setFocusable(false);
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragmentTo(searchStationFragment);
            }
        });

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSearching){
                    switchFragmentTo(recentListFragment);
                }else if (!isSearching){
                    switchFragmentTo(searchStationFragment);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(isSearching) {
            switchFragmentTo(recentListFragment);
        }else{
            super.onBackPressed();
        }
    }

    void switchFragmentTo(Fragment frag){
        if(frag == searchStationFragment && !isSearching){//역검색화면으로 전환
            isSearching = true;
            searchImageView.setImageResource(R.drawable.ic_pin_point);
            searchImageView.setFocusableInTouchMode(false);
            searchEditText.setHint(R.string.station_example);
            searchEditText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            searchEditText.setText("");
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_search_place_fl, frag).commit();
        }else if(frag == recentListFragment && isSearching) {//최근 검색목록으로 전환
            isSearching = false;
            searchImageView.setImageResource(R.drawable.ic_search);
            InputMethodManager imm = (InputMethodManager) getSystemService(SearchPlaceActivity.this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
            searchEditText.setFocusableInTouchMode(false);
            searchImageView.setFocusableInTouchMode(true);
            searchImageView.requestFocus();
            searchEditText.setHint(R.string.recent_list);
            searchEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            searchEditText.setText("");
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_search_place_fl, frag).commit();
        }
    }
}


