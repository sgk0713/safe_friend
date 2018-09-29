package com.seoul_app_contest.safe_friend.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.seoul_app_contest.safe_friend.helper.DBHelperLastItem;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.SearchPlaceActivity;
import com.seoul_app_contest.safe_friend.adapter.DataAdapter;
import com.seoul_app_contest.safe_friend.adapter.StationRecyclerViewAdapter;
import com.seoul_app_contest.safe_friend.confirmmap.ConfirmMapActivity;
import com.seoul_app_contest.safe_friend.dto.StationDto;
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

    StationRecyclerViewAdapter busStationRecyclerViewAdapter, subwayStationRecyclerViewAdapter, currentAdapter;
    EditText searchEditText;

    ArrayList<StationDto> resultArray = new ArrayList<>();

    Button subwayButton;
    Button busButton;
    RecyclerView recyclerView;
    Boolean isBusClicked = true;

    DBHelperLastItem dbHelperLastItem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_search_station, container, false);

        subwayButton = rootView.findViewById(R.id.fragment_search_station_subway_btn);
        busButton = rootView.findViewById(R.id.fragment_search_station_bus_btn);
        recyclerView = rootView.findViewById(R.id.fragment_search_station_rv);

        searchEditText = activity.findViewById(R.id.activity_search_place_search_edt);
        searchEditText.setFocusableInTouchMode(true);
        searchEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchEditText, 0);


        //저장된 디비
        final DataAdapter mDbHelper = new DataAdapter(activity);
        mDbHelper.createDatabase();

        //로컬디비
        dbHelperLastItem = new DBHelperLastItem(activity, "lastitem.db", null, 1);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        busStationRecyclerViewAdapter = new StationRecyclerViewAdapter(getContext(), resultArray, BUS);
        subwayStationRecyclerViewAdapter = new StationRecyclerViewAdapter(getContext(), resultArray, SUBWAY);
        currentAdapter = busStationRecyclerViewAdapter;
        recyclerView.setAdapter(currentAdapter);
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
                    getResult(str, mDbHelper);
                }
                currentAdapter.notifyDataSetChanged();
            }
        });

        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBusClicked){
                    isBusClicked = true;
                    subwayButton.setBackgroundResource(R.drawable.nonselected_button);
                    busButton.setBackgroundResource(R.drawable.selected_button);
                    resultArray.clear();
                    currentAdapter = busStationRecyclerViewAdapter;
                    recyclerView.setAdapter(currentAdapter);
                    searchEditText.setText("");
                }

            }
        });

        subwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBusClicked){
                    isBusClicked = false;
                    subwayButton.setBackgroundResource(R.drawable.selected_button);
                    busButton.setBackgroundResource(R.drawable.nonselected_button);
                    resultArray.clear();
                    currentAdapter = subwayStationRecyclerViewAdapter;
                    recyclerView.setAdapter(currentAdapter);
                    searchEditText.setText("");
                }
            }
        });

        busStationRecyclerViewAdapter.setItemClick(new StationRecyclerViewAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {


                dbHelperLastItem.sortAsLRU(resultArray.get(position).stop_no, resultArray.get(position).stop_nm,
                        Float.valueOf(resultArray.get(position).xcode), Float.valueOf(resultArray.get(position).ycode));
                Log.d("TESTEST", "클릭됨");

                Intent intent = new Intent(activity, ConfirmMapActivity.class);
                intent.putExtra("stop_nm", resultArray.get(position).stop_nm);
                intent.putExtra("stop_no", resultArray.get(position).stop_no);
                intent.putExtra("xcode", resultArray.get(position).xcode);
                intent.putExtra("ycode", resultArray.get(position).ycode);
                intent.putExtra("line", resultArray.get(position).line);
                Log.d("station_name", resultArray.get(position).stop_nm);
                startActivity(intent);
            }
        });

        subwayStationRecyclerViewAdapter.setItemClick(new StationRecyclerViewAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                dbHelperLastItem.sortAsLRU(resultArray.get(position).line, resultArray.get(position).stop_nm,
                        Float.valueOf(resultArray.get(position).xcode), Float.valueOf(resultArray.get(position).ycode));

                Intent intent = new Intent(activity, ConfirmMapActivity.class);
                intent.putExtra("stop_nm", resultArray.get(position).stop_nm);
                intent.putExtra("stop_no", resultArray.get(position).stop_no);
                intent.putExtra("xcode", resultArray.get(position).xcode);
                intent.putExtra("ycode", resultArray.get(position).ycode);
                intent.putExtra("line", resultArray.get(position).line);
                Log.d("station_name", resultArray.get(position).stop_nm);
                startActivity(intent);
            }
        });
        return rootView;
    }//onCreateView() end;

    void getResult(String str, DataAdapter mDbHelper){
        if(isBusClicked) {//버스 검색일때
            mDbHelper.open();
            Cursor cursor = mDbHelper.getDataWithQuery("select * from businfo where stop_nm like '%" + str + "%' or stop_no like '" + str + "%'");
            mDbHelper.close();
            for (int i = 0; i < cursor.getCount(); i++) {
                String stop_no = cursor.getString(1);
                String stop_nm = cursor.getString(2);
                String xcode = String.valueOf(cursor.getFloat(3));
                String ycode = String.valueOf(cursor.getFloat(4));
                resultArray.add(new StationDto(stop_no, stop_nm, xcode, ycode, null));
                cursor.moveToNext();
            }//for() end
        }else{//지하철 검색일때
            mDbHelper.open();
            Cursor cursor = mDbHelper.getDataWithQuery("select * from subwayinfo where stop_nm like '%" + str + "%'");
            mDbHelper.close();
            for (int i = 0; i < cursor.getCount(); i++) {
                String stop_no = cursor.getString(1);
                String stop_nm = cursor.getString(2);
                String xcode = String.valueOf(cursor.getFloat(3));
                String ycode = String.valueOf(cursor.getFloat(4));
                String line = getLineName(cursor.getString(5));
                resultArray.add(new StationDto(stop_no, stop_nm, xcode, ycode, line));
                cursor.moveToNext();
            }//for() end
        }
    }//getResult() end

    String getLineName(String str){
        if(str.equals("1")){
            return "1호선";
        }else if(str.equals("2")){
            return "2호선";
        } else if(str.equals("3")){
            return "3호선";
        }else if(str.equals("4")){
            return "4호선";
        }else if(str.equals("5")){
            return "5호선";
        }else if(str.equals("6")){
            return "6호선";
        }else if(str.equals("7")){
            return "7호선";
        }else if(str.equals("8")){
            return "8호선";
        }else if(str.equals("9")){
            return "9호선";
        }else if(str.equals("A")){
            return "공항철도";
        }else if(str.equals("B")){
            return "분당선";
        }else if(str.equals("E")){
            return "용인경전철";
        }else if(str.equals("G")){
            return "경춘선";
        }else if(str.equals("I")){
            return "인천 1호선";
        }else if(str.equals("I2")){
            return "인천 2호선";
        }else if(str.equals("K")){
            return "경의중앙선";
        }else if(str.equals("KK")){
            return "경강선";
        }else if(str.equals("S")){
            return "신분당선";
        }else if(str.equals("SU")){
            return "수인선";
        }else if(str.equals("U")){
            return "의정부경전철";
        }
        return str;
    }//getLineName() end;

}
