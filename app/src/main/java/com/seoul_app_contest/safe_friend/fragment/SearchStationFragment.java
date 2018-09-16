package com.seoul_app_contest.safe_friend.fragment;

import android.content.Context;
import android.content.Intent;
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

        busInfoArray= (ArrayList<StationDto>) getArguments().get("busInfoArray");
        Log.d("arraySIZE", busInfoArray.size()+"");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stationRecyclerViewAdapter = new StationRecyclerViewAdapter(getContext(), resultArray, BUS);
        recyclerView.setAdapter(stationRecyclerViewAdapter);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("textwatcher", "check");
                resultArray.clear();
                for(StationDto item: busInfoArray){
                    if(item.getStNm().contains(s))
                        resultArray.add(item);
                }
                stationRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        stationRecyclerViewAdapter.setItemClick(new StationRecyclerViewAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(activity, SetTimeActivity.class);
                intent.putExtra("stationName", resultArray.get(position).getStNm());
                Log.d("station_name", resultArray.get(position).getStNm());

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
