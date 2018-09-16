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
    private final String SERVICEKEY = "ihMOsX64n9aSddXcmGiG5z%2FMpo6B8m85NS8XOzlb%2Bdv7TccIsphc1nVGkuttxtWGNMgn1Pw8GLMlakZ4HTI0hQ%3D%3D";
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


        StrictMode.enableDefaults();//이거 없음 파싱오류뜸
        parseStationInfo();

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

    private void parseStationInfo(){
        Boolean isAsrId, isPosX, isPosY, isStId, isStNm, isTmX, isTmY;
        isAsrId = isPosX = isPosY = isStId = isStNm = isTmX = isTmY = false;
        String asrId, posX, posY, stId, stNm, tmX, tmY;
        asrId = posX = posY = stId = stNm = tmX = tmY = null;
        try{
            Log.d("PARSING", "PARSING1");
            URL url = new URL("http://ws.bus.go.kr/api/rest/stationinfo/getLowStationByName?"
                    +"ServiceKey=" + SERVICEKEY
                    +"&stSrch=%EC%84%9C%EB%B6%80");
            Log.d("PARSING", "PARSING2");

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            Log.d("PARSING", "PARSING3");
            XmlPullParser parser = parserCreator.newPullParser();
            Log.d("PARSING", "PARSING4");
            parser.setInput(url.openStream(), "UTF-8");
            Log.d("PARSING", "PARSING5");

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");
            Log.d("PARSING", "PARSING6");
            while (parserEvent != XmlPullParser.END_DOCUMENT){
                Log.d("PARSING", "PARSING7");
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        Log.d("PARSING", "START");
                        if(parser.getName().equals("arsId")){
                            isAsrId = true;

                        }
                        if(parser.getName().equals("posX")){
                            isPosX = true;
                        }
                        if(parser.getName().equals("posY")){
                            isPosY = true;
                        }
                        if(parser.getName().equals("stId")){
                            isStId = true;
                        }
                        if(parser.getName().equals("stNm")){
                            isStNm = true;
                        }
                        if(parser.getName().equals("tmX")){
                            isTmX = true;
                        }
                        if(parser.getName().equals("tmY")){
                            isTmY = true;
                        }
                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                            System.out.println("에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        Log.d("PARSING", "TEXT");
                        if(isAsrId){ //isTitle이 true일 때 태그의 내용을 저장.
                            asrId = parser.getText();
                            isAsrId = false;
                        }
                        if(isPosX){ //isAddress이 true일 때 태그의 내용을 저장.
                            posX = parser.getText();
                            isPosX = false;
                        }
                        if(isPosY){ //isMapx이 true일 때 태그의 내용을 저장.
                            posY = parser.getText();
                            isPosY = false;
                        }
                        if(isStId){ //isMapy이 true일 때 태그의 내용을 저장.
                            stId = parser.getText();
                            isStId = false;
                        }
                        if(isStNm){ //isMapy이 true일 때 태그의 내용을 저장.
                            stNm = parser.getText();
                            isStNm = false;
                        }
                        if(isTmX){ //isMapy이 true일 때 태그의 내용을 저장.
                            tmX = parser.getText();
                            isTmX = false;
                        }
                        if(isTmY){ //isMapy이 true일 때 태그의 내용을 저장.
                            tmY = parser.getText();
                            isTmY = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d("PARSING", "END");
                        if(parser.getName().equals("itemList")){
                            busInfoArray.add(new StationDto(asrId, posX, posY, stId, stNm, tmX, tmY));
                        }
                        break;
                }
                parserEvent = parser.next();
                Log.d("PARSING", "NEXT");
            }
        }catch (Exception e){
            System.out.println("while문 에러");
        }

    }
}


