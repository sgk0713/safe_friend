package com.seoul_app_contest.safe_friend.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.SearchPlaceActivity;
import com.seoul_app_contest.safe_friend.SetTimeActivity;
import com.seoul_app_contest.safe_friend.adapter.DataAdapter;
import com.seoul_app_contest.safe_friend.adapter.StationRecyclerViewAdapter;
import com.seoul_app_contest.safe_friend.dto.StationDto;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class SearchStationFragment extends Fragment{


    SearchPlaceActivity activity;

    private final int BUS = 0;
    private final int SUBWAY = 1;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (SearchPlaceActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    StationRecyclerViewAdapter stationRecyclerViewAdapter;
    EditText searchEditText;

    ArrayList<StationDto> resultArray = new ArrayList<>();
    ArrayList<StationDto> busInfoArray;

    Button subwayButton;
    Button busButton;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_search_station, container, false);

        subwayButton = rootView.findViewById(R.id.fragment_search_station_subway_btn);
        busButton = rootView.findViewById(R.id.fragment_search_station_bus_btn);
        recyclerView = rootView.findViewById(R.id.fragment_search_station_rv);

        searchEditText = activity.findViewById(R.id.activity_search_place_search_edt);

        final DataAdapter mDbHelper = new DataAdapter(activity);
        mDbHelper.createDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stationRecyclerViewAdapter = new StationRecyclerViewAdapter(getContext(), resultArray, BUS);
        recyclerView.setAdapter(stationRecyclerViewAdapter);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("textwatcher", "check");
                resultArray.clear();
                String str = s.toString();
                if(str.length()>0) {
                    mDbHelper.open();
                    Cursor cursor = mDbHelper.getDataWithQuery("select * from businfo where stop_nm like '%" + str + "%'");
                    mDbHelper.close();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        String stop_no = cursor.getString(1);
                        String stop_nm = cursor.getString(2);
                        String xcode = String.valueOf(cursor.getFloat(3));
                        String ycode = String.valueOf(cursor.getFloat(4));
                        resultArray.add(new StationDto(stop_no, stop_nm, xcode, ycode, null));
                        cursor.moveToNext();
                    }
                }
                stationRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        stationRecyclerViewAdapter.setItemClick(new StationRecyclerViewAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(activity, SetTimeActivity.class);
                intent.putExtra("stationName", resultArray.get(position).stop_nm);
                Log.d("station_name", resultArray.get(position).stop_nm);

                startActivity(intent);
            }
        });

        subwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subwayButton.setBackgroundResource(R.drawable.selected_button);
                busButton.setBackgroundResource(R.drawable.nonselected_button);
            }
        });

        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subwayButton.setBackgroundResource(R.drawable.nonselected_button);
                busButton.setBackgroundResource(R.drawable.selected_button);
            }
        });

        return rootView;
    }
}
