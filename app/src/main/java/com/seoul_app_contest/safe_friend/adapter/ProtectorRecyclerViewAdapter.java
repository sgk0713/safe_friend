package com.seoul_app_contest.safe_friend.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.RequestModel;
import com.seoul_app_contest.safe_friend.UserModel;
import com.seoul_app_contest.safe_friend.dto.UserDto;
import com.seoul_app_contest.safe_friend.map.MapsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProtectorRecyclerViewAdapter extends RecyclerView.Adapter<ProtectorRecyclerViewAdapter.ProtectorRecyclerViewHolder>{
    private Context context;
    private ArrayList<RequestModel> arrayList = new ArrayList<>();
    private UserModel mUserModel = null;
    public ProtectorRecyclerViewAdapter(Context context, ArrayList<RequestModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ProtectorRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.protector_list, null, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ProtectorRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProtectorRecyclerViewHolder protectorRecyclerViewHolder, final int i) {
        protectorRecyclerViewHolder.timeTv.setText(arrayList.get(i).getMeetingTime());
        protectorRecyclerViewHolder.locationTv.setText(arrayList.get(i).getLocation());
//        protectorRecyclerViewHolder.confirmLl.setClickable(true);
//        protectorRecyclerViewHolder.confirmLl.setFocusableInTouchMode(true);
//        protectorRecyclerViewHolder.confirmLl.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    Log.d("BEOM123", "click.");
//                    Log.d("BEOM123", "arrayList.get(i).getMeetingTime() : " + arrayList.get(i).getMeetingTime());
//                    FirebaseFirestore.getInstance().collection("WAITING_LIST").document(arrayList.get(i).getMeetingTime()).update("isWaiting", false);
//                    return true;
//                }
//                return false;
//            }
//        });
        protectorRecyclerViewHolder.confirmLl_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BEOM123", "click.");
                Log.d("BEOM123", "arrayList.get(i).getMeetingTime() : " + arrayList.get(i).getMeetingTime());
                FirebaseFirestore.getInstance().collection("USERS").document(arrayList.get(i).getUid()).collection("WITH").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(new UserDto());

                FirebaseFirestore.getInstance().collection("WAITING_LIST").document(arrayList.get(i).getUid()).update("pUid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                FirebaseFirestore.getInstance().collection("WAITING_LIST").document(arrayList.get(i).getUid()).update("isWaiting", false);

                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("TYPE", "follower");
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProtectorRecyclerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.protector_list_time_tv)TextView timeTv;
        @BindView(R.id.protector_list_location_tv)TextView locationTv;
//        @BindView(R.id.protector_list_confirm_btn)Button confirmBtn;
        @BindView(R.id.protector_list_confirm_ll)LinearLayout confirmLl;
        LinearLayout confirmLl_;

        public ProtectorRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            confirmLl_ = itemView.findViewById(R.id.protector_list_confirm_ll);
            ButterKnife.bind(this, itemView);
        }
    }
}
