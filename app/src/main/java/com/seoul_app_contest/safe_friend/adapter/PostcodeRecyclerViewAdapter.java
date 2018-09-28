package com.seoul_app_contest.safe_friend.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.Register.RegisterActivity;
import com.seoul_app_contest.safe_friend.dto.PostDto;
import com.seoul_app_contest.safe_friend.postcode.PostcodeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostcodeRecyclerViewAdapter extends RecyclerView.Adapter<PostcodeRecyclerViewAdapter.PostcodeRecyclerViewHolder> {
    private Context context;
    private ArrayList<PostDto> arrayList = new ArrayList<>();

    public PostcodeRecyclerViewAdapter(Context context, ArrayList<PostDto> arrayList, ReturnDataListener returnDataListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.returnDataListener = returnDataListener;
    }

    @NonNull
    @Override
    public PostcodeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.postcode, null, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new PostcodeRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostcodeRecyclerViewHolder postcodeRecyclerViewHolder, final int i) {
        postcodeRecyclerViewHolder.zipNoTv.setText(arrayList.get(i).getZipNo());
        postcodeRecyclerViewHolder.lnmTv.setText(arrayList.get(i).getLnmAdres());
        postcodeRecyclerViewHolder.rnTv.setText(arrayList.get(i).getRnAdres());
        postcodeRecyclerViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, RegisterActivity.class);
//                intent.putExtra("post", arrayList.get(i));
                returnDataListener.returnData(arrayList.get(i));
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PostcodeRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.postcode_zip_num_tv)
        TextView zipNoTv;
        @BindView(R.id.postcode_lnm_tv)
        TextView lnmTv;
        @BindView(R.id.postcode_rn_tv)
        TextView rnTv;
        @BindView(R.id.postcode_ll)
        LinearLayout linearLayout;

        public PostcodeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    ReturnDataListener returnDataListener;

    public interface ReturnDataListener {
        void returnData(PostDto postDto);
    }
}
