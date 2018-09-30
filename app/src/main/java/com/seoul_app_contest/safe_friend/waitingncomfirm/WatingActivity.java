package com.seoul_app_contest.safe_friend.waitingncomfirm;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.RequestModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


public class WatingActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    ConfirmationFragment mConfirmationFragment;
    String TAG = "Wating_DEBUG";
    String stop_nm, stop_no, xcode, ycode, line, time, street;
    RequestModel requestModel;
    private String uid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        mConfirmationFragment = new ConfirmationFragment();

        Intent intent = getIntent();
        stop_nm = intent.getStringExtra("stop_nm");
        stop_no = intent.getStringExtra("stop_no");
        xcode = intent.getStringExtra("xcode");
        ycode = intent.getStringExtra("ycode");
        line = intent.getStringExtra("line");
        time = intent.getStringExtra("time");
        street = intent.getStringExtra("street");

        uid = auth.getCurrentUser().getUid();


        db.collection("WAITING_LIST")
                .document(uid)
                .set(requestModel = new RequestModel(time, stop_nm, street, uid))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "파이어스토어 wating_list 추가 성공");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "파이어스토어 wating_list 추가 실패");
                    }
                });

        db.collection("WAITING_LIST").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Boolean temp=true;
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    temp = documentSnapshot.getBoolean("isWaiting");
                    Log.d(TAG, "Current data: " + temp);
                    if(!temp){
                        db.collection("LOG_WAITING_LIST")
                                .document(requestModel.getRequestTime())
                                .set(requestModel)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "파이어스토어 LOG_WAITING_LIST 추가 성공");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "파이어스토어 LOG_WAITING_LIST 추가 실패");
                                    }
                                });
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmet, mConfirmationFragment).commit();
                    }

                } else {
                    Log.d(TAG, "Current data: null");
                }

            }
        });

    }

}
