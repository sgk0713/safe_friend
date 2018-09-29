package com.seoul_app_contest.safe_friend.confirmmap;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.SetTimeActivity;

import java.util.ArrayList;
import java.util.List;

public class ConfirmMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mGoogleMap = null;
    private SQLiteDatabase db = null;
    private Marker mMarker = null;
    private double lat = 0.0;//xcode
    private double lng = 0.0;//ycode
    String stop_nm, stop_no;

    Button confirmBtn;
    TextView stationTv;
    List<Marker> previous_marker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("onCreate", "@@@@@@@@@@@@@");

        setContentView(R.layout.activity_confirmmap);


        previous_marker = new ArrayList<Marker>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.confirmMap);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        stop_nm = intent.getStringExtra("stop_nm");
        stop_no = intent.getStringExtra("stop_no");
        lat = Double.valueOf(intent.getStringExtra("xcode"));
        lng = Double.valueOf(intent.getStringExtra("ycode"));

        confirmBtn = findViewById(R.id.activity_confirmmap_confirm_btn);
        stationTv = findViewById(R.id.activity_confirmmap_station_tv);

        stationTv.setText(stop_nm);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmMapActivity.this, SetTimeActivity.class);
                intent.putExtra("stop_nm", stop_nm);
                intent.putExtra("stop_no", stop_no);
                intent.putExtra("xcode", lat);
                intent.putExtra("ycode", lng);
                startActivity(intent);
            }
        });

/*
        DataAdapter mDbHelper = new DataAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        double lat = 37.49189789397172;
        double lng = 127.00792003422976;

        double a= Math.acos(Math.cos(37.4685225));
        double b = Math.cos(Math.toRadians(lat));
        double c = Math.cos(Math.toRadians(lng)-Math.toRadians(126.8943311));
        double d = Math.sin(Math.toRadians(37.4685225));

        String sql = "SELECT * FROM subwayinfo where ";

        Cursor cursor = mDbHelper.getDataWithQuery(sql);//테이블은 businfo와 subwayinfo  두가지가 있음
        mDbHelper.close();
        for (int i = 0; i < cursor.getCount(); i++) {
            String stop_no = cursor.getString(1);
            String stop_nm = cursor.getString(2);
            String xcode = String.valueOf(cursor.getFloat(3));
            String ycode = String.valueOf(cursor.getFloat(4));
            Log.d("asd", "" + stop_no + ", " + stop_nm + ", " + ", " + xcode + ", " + ycode);
            cursor.moveToNext();
        }
        */
    }
    boolean check = true;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("onMapReady", "@@@@@@@@@@@@@");
        mGoogleMap = googleMap;
        LatLng SEOUL = new LatLng(lat, lng);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title(stop_nm);
        markerOptions.snippet(stop_no);
        mGoogleMap.addMarker(markerOptions);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        Log.d("Center Latitude", "" + mGoogleMap.getCameraPosition().target.latitude);
        Log.d("Center longitude", "" + mGoogleMap.getCameraPosition().target.longitude);

        mGoogleMap.setMaxZoomPreference(17);
        mGoogleMap.setMinZoomPreference(17);
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("setOnMapClickListener","@@@@@");
            }
        });

        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int reason) {
                if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE || reason == GoogleMap.OnCameraMoveStartedListener
                        .REASON_API_ANIMATION) {
                    Log.d("onCameraMoveStarted","@@@@@");
                    if(check) {
                        check = !check;
                    }
                }
                else{
                    Log.d("onMoveStarteadasdadd","@@@@@");
                }
            }
        });
        mGoogleMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {
                Log.d("onCameraMoveCanceled","@@@@@");
            }
        });
        //mGoogleMap.setInfoWindowAdapter(mMapController.getInfoWindowAdapter(this));
        //mGoogleMap.setOnInfoWindowClickListener(mMapController.getOnInfoWindowClickListener());
    }
}
