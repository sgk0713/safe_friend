package com.seoul_app_contest.safe_friend.map;


import android.Manifest;
import android.content.ActivityNotFoundException;

import android.content.DialogInterface;
import android.content.Intent;

import android.location.Location;

import android.net.Uri;

import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.seoul_app_contest.safe_friend.PermissionUtil;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.UserModel;
import com.seoul_app_contest.safe_friend.dto.UserDto;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Activity 생명주기
 * onCreate(Activity가 최초 생성할 때 호출) -> onStart(사용자에게 보여지기 직전에 호출) -> onResume(상호작용을 하기 직전에 호출)
 **/
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String USERNAME,PHONENO,LOCATION;
    private String TYPE, UID, PID;

    private boolean mMoveMapByUser = false;
    private boolean mMoveMapByFollower = false;

    private static FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();

    private FusedLocationProviderClient mFusedLocationClient = null;
    private GoogleMap mGoogleMap = null;
    private boolean mLocationPermssionGranted = false;

    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    private MapController mMapController;//관리자와 사용자의 맵을 구분지어주는 구분자.
    private Marker mMarker = null;

    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            drawMarker(dataSnapshot.getValue(MapModel.class));
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            drawMarker(dataSnapshot.getValue(MapModel.class));
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private LocationRequest mLocationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            Location mCurrentLocation = locationResult.getLastLocation();
            mMapController.setLocation(mCurrentLocation);
            mMapController.needToActivate();

            if(mMoveMapByUser)
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMapController.getLocation().getLatitude(),mMapController.getLocation().getLongitude()), 17));
            mDatabaseReference.child(UID + "/Location").setValue(new MapModel(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
        }
    };

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("onCreate", "@@@@@@@@@@@@@");

        setContentView(R.layout.activity_map);

        final String TYPE = getIntent().getStringExtra("TYPE");
        //테스트코드
        final UserModel mUserModel = new UserModel();
        UID = mUserModel.getUID();
        TextView tobBarTextView = findViewById(R.id.topBarTextView);
        tobBarTextView.setText((TYPE == "user" ? "지킴이":USERNAME)+"님의 현재 위치입니다.");

        ((Button)findViewById(R.id.mapRefreadButtonUser)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMapController.getLocation().getLatitude(),mMapController.getLocation().getLongitude()), 17));
                    mMoveMapByUser = true;
                    mMoveMapByFollower = false;
            }
        });

        ((Button)findViewById(R.id.mapRefreadButtonFollower)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMapController.getOpponentLocation().getLatitude(),mMapController.getOpponentLocation().getLongitude()), 17));
                    mMoveMapByUser = false;
                    mMoveMapByFollower = true;
            }
        });

        mMapController = getController(TYPE);
        //mMapController.changeViewInfo(mUserDto.getUid(),mUserDto.getName(),mUserDto.getLocation(), mUserDto.getPhoneNum());
        //거리에 따라 버튼을 보여주기 위함.
        ImageButton cancelButton = findViewById(R.id.mapCancelButton);
        cancelButton.setOnClickListener(mMapController.mOnClickListener);
        //cancelButton.setVisibility(View.GONE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(TYPE.equals("user")){
        mUserModel.getPid(mUserModel.getCurrentUserEmail(), new UserModel.GetPidCallbackListener() {
            @Override
            public void onSuccess(String pid) {
                PID = pid;
                mDatabaseReference.child(pid).addChildEventListener(mChildEventListener);
            }

            @Override
            public void onFail() {

            }
        });
        }else{
            PID = getIntent().getStringExtra("UID");
        }

    }

    private void drawMarker(MapModel mMapModel) {
        if (mMarker != null) mMarker.setPosition(new LatLng(mMapModel.getmLat(), mMapModel.getmLng()));
        else {
            mMarker = mGoogleMap.addMarker(mMapController.createMaker(mMapModel));
        }
        mMapController.setOpponentLocation(mMapModel);

        if(mMoveMapByFollower)
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMapController.getOpponentLocation().getLatitude(),mMapController.getOpponentLocation().getLongitude()), 17));
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "@@@@@@@@@@@@@");
        getCurrentLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "@@@@@@@@@@@@@");
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mFusedLocationClient = null;
                }
            });

            mDatabaseReference.child(PID).removeEventListener(mChildEventListener);


        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (mLocationPermssionGranted) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                @Override
                public void onCameraMoveStarted(int reason) {
                    if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE || reason == GoogleMap.OnCameraMoveStartedListener
                            .REASON_API_ANIMATION) {
                            mMoveMapByUser = false;
                            mMoveMapByFollower = false;

                    }
                }
            });
            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    return true;
                }
            });
            //mGoogleMap.setInfoWindowAdapter(mMapController.getInfoWindowAdapter(this));
            //mGoogleMap.setOnInfoWindowClickListener(mMapController.getOnInfoWindowClickListener());
        }

    }

    private void getLocationPermission() {
        if (PermissionUtil.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            mLocationPermssionGranted = true;
        } else {
            PermissionUtil.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermssionGranted = false;
        if (requestCode == PermissionUtil.REQUEST_LOCATION) {
            if (PermissionUtil.verifyPermission(grantResults)) {
                mLocationPermssionGranted = true;
            } else {
                showRequestAgainDialog();
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getCurrentLocation() {
        if (mLocationPermssionGranted) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    private void showRequestAgainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("이 권한은 꼭 필요한 권한이므로, 설정에서 활성화 부탁드립니다.");
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = null;
                try {
                    intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getPackageName()));
                } catch (ActivityNotFoundException e) {
                    intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                } finally {
                    startActivity(intent);
                }
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //취소 처리
            }
        });

        builder.create();
    }

    private MapController getController(String type) {
        if (type.equals("follower")) {
            return new FollowerMapController(this);
        } else {

            return new UserMapController(this);
        }
    }


}

