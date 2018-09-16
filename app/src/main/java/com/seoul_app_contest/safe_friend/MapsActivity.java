package com.seoul_app_contest.safe_friend;

import android.Manifest;
import android.content.ActivityNotFoundException;

import android.content.DialogInterface;
import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
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
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


/**
 Activity 생명주기
 onCreate(Activity가 최초 생성할 때 호출) -> onStart(사용자에게 보여지기 직전에 호출) -> onResume(상호작용을 하기 직전에 호출)
 **/
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    //추후 사용자 아이디값이 들어가야함
    private final String UID = "qwertyui123456789" ;
    private static FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();

    private FusedLocationProviderClient mFusedLocationClient = null;
    private GoogleMap mGoogleMap = null;
    private Location mCurrentLocation = null;
    private boolean mLocationPermssionGranted = false;
    private Marker mFollowerMarker = null;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초
    private  LocationModel mLocationModel = null;

    private LocationRequest mLocationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

    private LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            mCurrentLocation = locationResult.getLastLocation();

            //사용자 위치로 이동
            //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()),17));
            Log.d("mLocationCallback","@@@@@@@@@@@@@");
            mLocationModel.setClientLocation(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()));
            mDatabaseReference.child(UID).setValue(mLocationModel);
        }
    };

    private String uid = null;
    @SuppressWarnings("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate","@@@@@@@@@@@@@");
        setContentView(R.layout.activity_map);
        //uid = getIntent().getStringExtra("uid);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationModel = new LocationModel();
        getLocationPermission();

        mDatabaseReference.child(UID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("onChildAdded","@@@@@@@@@@@@@");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("onChildChanged","@@@@@@@@@@@@@");
                if(mFollowerMarker != null)
                    //mFollowerMarker.remove();

                if(mLocationModel.getFollower() == null)
                    return;
                LatLng currentLatLng = mLocationModel.getFollower();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(currentLatLng);
                markerOptions.title("여성지키미");
                markerOptions.snippet(getCurrentAddress(currentLatLng));
                markerOptions.draggable(true);

                mFollowerMarker = mGoogleMap.addMarker(markerOptions);
                Log.d("onChildChanged...OK","@@@@@@@@@@@@@");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("onChildRemoved","@@@@@@@@@@@@@");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("onChildMoved","@@@@@@@@@@@@@");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelled","@@@@@@@@@@@@@");
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //테스트용 코드****************************
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                testFollower();
            }
        };

        Timer timer = new Timer();
        timer.schedule(tt,0,3000);
        //**************************************
    }
    @SuppressWarnings("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume","@@@@@@@@@@@@@");
        getCurrentLocation();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mFusedLocationClient != null){
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
        Log.d("onMapReady","@@@@@@@@@@@@@");
        mGoogleMap = googleMap;

        if(mLocationPermssionGranted) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

    }

    private void getLocationPermission(){
        Log.d("getLocationPermission","@@@@@@@@@@@@@");
        if(PermissionUtil.checkPermissions(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            mLocationPermssionGranted = true;
        }else{
            PermissionUtil.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermssionGranted = false;
        if(requestCode == PermissionUtil.REQUEST_LOCATION){
            if(PermissionUtil.verifyPermission(grantResults)){
                mLocationPermssionGranted = true;
            }else{
                showRequestAgainDialog();
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getCurrentLocation(){
        Log.d("getCurrentLocation","@@@@@@@@@@@@@");
        if(mLocationPermssionGranted){
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback,Looper.myLooper());
        }
    }

    private void showRequestAgainDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("이 권한은 꼭 필요한 권한이므로, 설정에서 활성화 부탁드립니다.");
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = null;
                try{
                    intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:"+getPackageName()));
                }catch (ActivityNotFoundException e){
                    intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                }finally {
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

    //테스트용 코드************************************************
    double lo = 126.980409;
    double la =37.4755437;
    private void testFollower(){
        LatLng lalo = new LatLng(la,lo);
        lo -= 0.0001;
        la -= 0.0001;
        mLocationModel.setFollower(lalo);
        mDatabaseReference.child(UID).setValue(mLocationModel);
    }
    //*********************************************************

    public String getCurrentAddress(LatLng latlng) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }

}

