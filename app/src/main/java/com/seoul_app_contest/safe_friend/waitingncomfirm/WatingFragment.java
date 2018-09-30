package com.seoul_app_contest.safe_friend.waitingncomfirm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.seoul_app_contest.safe_friend.R;

public class WatingFragment extends Fragment {
    View mView;
    private ImageView imgAndroid,imgAndroid2,imgAndroid3,imgAndroid4;
    private Animation anim,anim2,anim3,anim4;

    public WatingFragment(){}

    WatingActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (WatingActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private String uid;
    String TAG = "Wating_DEBUG";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting,container,false);
        mView = view;
        AnimationSet set = new AnimationSet(true);

        uid = auth.getCurrentUser().getUid();

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
                Toast.makeText(activity, "취소되셨습니다.", Toast.LENGTH_SHORT).show();
                db.collection("WAITING_LIST")
                        .document(uid)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "파이어스토어 wating_list 삭제 성공");

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "파이어스토어 wating_list 삭제 실패");
                            }
                        });
                activity.finish();
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        db.collection("WAITING_LIST")
                .document(uid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "파이어스토어 wating_list 삭제 성공");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "파이어스토어 wating_list 삭제 실패");
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
