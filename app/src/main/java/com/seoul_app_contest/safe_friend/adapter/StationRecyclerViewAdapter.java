package com.seoul_app_contest.safe_friend.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.dto.StationDto;
import java.util.ArrayList;

public class StationRecyclerViewAdapter extends RecyclerView.Adapter<StationRecyclerViewAdapter.StationRecyclerViewHolder>{


    Context mContext;
    ArrayList<StationDto> arrayList;
    int stationType;

    public StationRecyclerViewAdapter(Context mContext, ArrayList<StationDto> arrayList, int stationType) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.stationType = stationType;
    }


    @NonNull
    @Override
    public StationRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_of_station, viewGroup, false);
        return new StationRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StationRecyclerViewHolder holder, int i) {
        final int position = i;
        if(stationType == 0){//bus
            holder.icon.setImageResource(R.drawable.ic_bus);
            holder.stationName.setText(arrayList.get(i).stop_nm);
            holder.stationId.setText(arrayList.get(i).stop_no + " | ");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(v, position);
                    }
                }
            });
        }else if(stationType == 1){//subway
            holder.icon.setImageResource(R.drawable.ic_subway);
            holder.stationId.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<StationDto> arrayList) {
        this.arrayList = arrayList;
    }

    public class StationRecyclerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parentLinearLayout;
        ImageView icon;
        TextView stationName;
        TextView stationId;

        public StationRecyclerViewHolder(final View itemView) {
            super(itemView);
            parentLinearLayout = itemView.findViewById(R.id.item_of_station_parent_ll);
            icon = itemView.findViewById(R.id.item_of_station_icon_iv);
            stationName = itemView.findViewById(R.id.item_of_station_station_name_tv);
            stationId = itemView.findViewById(R.id.item_of_station_station_id_tv);
        }
    }

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view,int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }



}
