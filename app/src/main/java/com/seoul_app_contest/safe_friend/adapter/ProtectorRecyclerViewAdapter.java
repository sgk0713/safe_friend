package com.seoul_app_contest.safe_friend.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.RequestModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProtectorRecyclerViewAdapter extends RecyclerView.Adapter<ProtectorRecyclerViewAdapter.ProtectorRecyclerViewHolder>{
    private Context context;
    private ArrayList<RequestModel> arrayList = new ArrayList<>();

    public ProtectorRecyclerViewAdapter(Context context, ArrayList<RequestModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ProtectorRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.protector_list, null, false);
        return new ProtectorRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProtectorRecyclerViewHolder protectorRecyclerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProtectorRecyclerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.protector_list_time_tv)TextView timeTv;
        @BindView(R.id.protector_list_location_tv)TextView locationTv;
        @BindView(R.id.protector_list_confirm_btn)Button confirmBtn;

        public ProtectorRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
