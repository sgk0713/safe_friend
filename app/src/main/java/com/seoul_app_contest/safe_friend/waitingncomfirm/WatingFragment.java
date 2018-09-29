package com.seoul_app_contest.safe_friend.waitingncomfirm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seoul_app_contest.safe_friend.R;

public class WatingFragment extends Fragment {
    View mView;
    public WatingFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting,container,false);
        mView = view;
        Glide.with(this).load(R.drawable.ic_wating_animation_loading).into((ImageView) view.findViewById(R.id.watingGIFImageView));
        Button watingButtnon = view.findViewById(R.id.watingButton);
        watingButtnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick","@@@@@@@@@@@");
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView","@@@@@@@@@");
        Button watingButtnon = mView.findViewById(R.id.watingButton);
        watingButtnon.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause","@@@@@@@@@");
        Button watingButtnon = mView.findViewById(R.id.watingButton);
        watingButtnon.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onStop","@@@@@@@@@");
        Button watingButtnon = mView.findViewById(R.id.watingButton);
        watingButtnon.setVisibility(View.GONE);

    }
}
