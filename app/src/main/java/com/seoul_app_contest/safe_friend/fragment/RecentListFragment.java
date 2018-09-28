package com.seoul_app_contest.safe_friend.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seoul_app_contest.safe_friend.Helper.DBHelperLastItem;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.SearchPlaceActivity;
import com.seoul_app_contest.safe_friend.SetTimeActivity;
import com.seoul_app_contest.safe_friend.adapter.StationRecyclerViewAdapter;
import com.seoul_app_contest.safe_friend.dto.StationDto;

import java.util.ArrayList;


public class RecentListFragment extends Fragment {

    SearchPlaceActivity activity;

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

    RecyclerView recyclerView;
    StationRecyclerViewAdapter stationRecyclerViewAdapter;
    ArrayList<StationDto> resultArray = new ArrayList<>();
    DBHelperLastItem dbHelperLastItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_recent_list, container, false);

        recyclerView = rootView.findViewById(R.id.fragment_recent_list_rv);


        dbHelperLastItem = new DBHelperLastItem(activity, "lastitem.db", null, 1);


        resetArray();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stationRecyclerViewAdapter = new StationRecyclerViewAdapter(activity, resultArray, -1);
        recyclerView.setAdapter(stationRecyclerViewAdapter);

        stationRecyclerViewAdapter.setItemClick(new StationRecyclerViewAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {

                if(resultArray.get(position).line == null) {
                    dbHelperLastItem.sortAsLRU(resultArray.get(position).stop_no, resultArray.get(position).stop_nm,
                            Float.valueOf(resultArray.get(position).xcode), Float.valueOf(resultArray.get(position).ycode));
                }else{
                    dbHelperLastItem.sortAsLRU(resultArray.get(position).line, resultArray.get(position).stop_nm,
                            Float.valueOf(resultArray.get(position).xcode), Float.valueOf(resultArray.get(position).ycode));
                }
                resetArray();
                stationRecyclerViewAdapter.notifyDataSetChanged();
                Intent intent = new Intent(activity, SetTimeActivity.class);
                intent.putExtra("stop_nm", resultArray.get(position).stop_nm);
                intent.putExtra("stop_no", resultArray.get(position).stop_no);
                intent.putExtra("xcode", resultArray.get(position).xcode);
                intent.putExtra("ycode", resultArray.get(position).ycode);
                Log.d("station_name", resultArray.get(position).stop_nm);
                startActivity(intent);
            }
        });
        return rootView;
    }

    void resetArray(){
        resultArray.clear();
        for(int i = 1; i<=dbHelperLastItem.getTotal();i++){
            resultArray.add(new StationDto(dbHelperLastItem.getStopNo(i)
                    , dbHelperLastItem.getStopNm(i)
                    , dbHelperLastItem.getXcode(i)
                    , dbHelperLastItem.getYcode(i)
                    , null));
        }
    }
}
