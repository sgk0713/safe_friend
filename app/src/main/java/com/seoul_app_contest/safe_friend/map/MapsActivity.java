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
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seoul_app_contest.safe_friend.PermissionUtil;
import com.seoul_app_contest.safe_friend.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Activity 생명주기
 * onCreate(Activity가 최초 생성할 때 호출) -> onStart(사용자에게 보여지기 직전에 호출) -> onResume(상호작용을 하기 직전에 호출)
 **/
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String TYPE = "follower";
    private final String USERNAME = "사용자";
    private final String UID = "qwertyui123456789";
    private final String FID = "qwertyui987654321";

    private boolean mMoveMapByUser = false;

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
            Log.d("mLocationCallback", "@@@@@@@@@@@@@");

            Location mCurrentLocation = locationResult.getLastLocation();
            mMapController.setLocation(mCurrentLocation);
            mMapController.needToActivate();
            mDatabaseReference.child(UID + "/Location").setValue(new MapModel(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
        }
    };

    private String uid = null;

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("onCreate", "@@@@@@@@@@@@@");

        setContentView(R.layout.activity_main);
        TYPE = getIntent().getStringExtra("TYPE");
        TextView tobBarTextView = findViewById(R.id.topBarTextView);
        tobBarTextView.setText((TYPE == "user" ? "지킴이":USERNAME)+"님의 현재 위치입니다.");

        //상단 Layout을  두 번 눌렀을 때 지도가 클릭되는 것을 방지하기 위한 리스너
        ConstraintLayout app_layer = findViewById (R.id.topBarLayout);
        app_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.mapRefreshButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMoveMapByUser) {
                    mDatabaseReference.child(FID).addChildEventListener(mChildEventListener);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMarker.getPosition(), 17));
                    mMoveMapByUser = false;
                }
            }
        });
        mMapController = getController(TYPE);

        //거리에 따라 버튼을 보여주기 위함.
        ImageButton cancelButton = findViewById(R.id.mapCancelButton);
        cancelButton.setOnClickListener(mMapController.mOnClickListener);
        cancelButton.setVisibility(View.GONE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();

        mDatabaseReference.child(FID).addChildEventListener(mChildEventListener);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                testFollower();
            }
        };

        Timer timer = new Timer();
        timer.schedule(tt, 0, 3000);
    }

    private void drawMarker(MapModel mMapModel) {
        if (mMarker != null) mMarker.remove();
        mMarker = mGoogleMap.addMarker(mMapController.createMaker(mMapModel));
        mMapController.setOpponentLocation(mMapModel);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMapModel.getmLat(), mMapModel.getmLng()), 17));
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
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mFusedLocationClient = null;
                }
            });
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("onMapReady", "@@@@@@@@@@@@@");
        mGoogleMap = googleMap;
        if (mLocationPermssionGranted) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

            mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                @Override
                public void onCameraMoveStarted(int reason) {
                    if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE || reason == GoogleMap.OnCameraMoveStartedListener
                            .REASON_API_ANIMATION) {
                        if(!mMoveMapByUser) {
                            mDatabaseReference.child(FID).removeEventListener(mChildEventListener);
                            mMoveMapByUser = true;
                        }
                    }
                }
            });

            mGoogleMap.setInfoWindowAdapter(mMapController.getInfoWindowAdapter(this));
            mGoogleMap.setOnInfoWindowClickListener(mMapController.getOnInfoWindowClickListener());
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

    double ln = 127.0886328;
    double la = 37.5624628;

    private void testFollower() {
        MapModel mMapModel = new MapModel(la, ln);
        ln -= 0.00001;
        la -= 0.00001;
        mDatabaseReference.child(FID + "/Location").setValue(mMapModel);
    }

    //    public String getCurrentAddress(LatLng latlng) {
//        //지오코더... GPS를 주소로 변환
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//        List<Address> addresses;
//
//        try {
//
//            addresses = geocoder.getFromLocation(
//                    latlng.latitude,
//                    latlng.longitude,
//                    1);
//        } catch (IOException ioException) {
//            //네트워크 문제
//            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
//            return "지오코더 서비스 사용불가";
//        } catch (IllegalArgumentException illegalArgumentException) {
//            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
//            return "잘못된 GPS 좌표";
//
//        }
//
//
//        if (addresses == null || addresses.size() == 0) {
//            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
//            return "주소 미발견";
//
//        } else {
//            Address address = addresses.get(0);
//            return address.getAddressLine(0).toString();
//        }
//
//    }
    private MapController getController(String type) {
        if (type.equals("follower")) {
            return new FollowerMapController(this);
        } else {
            return new UserMapController(this);
        }
    }


}
