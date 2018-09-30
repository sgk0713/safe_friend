package com.seoul_app_contest.safe_friend.waitingncomfirm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.seoul_app_contest.safe_friend.R;

public class WatingFragment extends Fragment {
    View mView;
    private ImageView imgAndroid,imgAndroid2,imgAndroid3,imgAndroid4;
    private Animation anim,anim2,anim3,anim4;

    public WatingFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting,container,false);
        mView = view;
        AnimationSet set = new AnimationSet(true);

        imgAndroid = mView.findViewById(R.id.firstLoading);
        anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.loading_first);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgAndroid2.startAnimation(anim2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imgAndroid.setAnimation(anim);

        imgAndroid2 = mView.findViewById(R.id.secondLoading);
        anim2 = AnimationUtils.loadAnimation(view.getContext(), R.anim.loading_second);
        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgAndroid3.startAnimation(anim3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imgAndroid3 = mView.findViewById(R.id.thridLoading);
        anim3 = AnimationUtils.loadAnimation(view.getContext(), R.anim.loading_third);
        anim3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgAndroid4.startAnimation(anim4);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        imgAndroid4 = mView.findViewById(R.id.lastLoading);
        anim4 = AnimationUtils.loadAnimation(view.getContext(), R.anim.loading_last);
        anim4.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgAndroid.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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
