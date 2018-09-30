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

import com.seoul_app_contest.safe_friend.helper.DBHelperLastItem;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.SearchPlaceActivity;
import com.seoul_app_contest.safe_friend.adapter.StationRecyclerViewAdapter;
import com.seoul_app_contest.safe_friend.confirmmap.ConfirmMapActivity;
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

    String stop_nm, stop_no, xcode, ycode, line;
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
                stop_nm = resultArray.get(position).stop_nm;
                stop_no = resultArray.get(position).stop_no;
                xcode = resultArray.get(position).xcode;
                ycode = resultArray.get(position).ycode;
                line = resultArray.get(position).line;

                Intent intent = new Intent(activity, ConfirmMapActivity.class);
                intent.putExtra("stop_nm", stop_nm);
                intent.putExtra("stop_no", stop_no);
                intent.putExtra("xcode", xcode);
                intent.putExtra("ycode", ycode);
                intent.putExtra("line", line);
                Log.d("station_name", stop_nm);
                resetArray();
                stationRecyclerViewAdapter.notifyDataSetChanged();
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
