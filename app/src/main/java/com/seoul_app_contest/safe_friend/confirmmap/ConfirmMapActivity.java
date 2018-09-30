package com.seoul_app_contest.safe_friend.confirmmap;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.SetTimeActivity;
import com.seoul_app_contest.safe_friend.adapter.DataAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConfirmMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mGoogleMap = null;

    private boolean isInfoWindowShown = false;

    private double range = 0.002;

    private LatLng currentPostion = null;
    private Marker currentMarker = null;

    String stop_nm, stop_no, line;

    Button confirmBtn;
    TextView stationTv;
    List<ConfirmMarkerOption> markerOptionList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("onCreate", "@@@@@@@@@@@@@");

        setContentView(R.layout.activity_confirmmap);

        markerOptionList = new ArrayList<ConfirmMarkerOption>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.confirmMap);
        mapFragment.getMapAsync(this);

        //getIntentData();
        findViewById(R.id.confirmMapSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmMarkerOption curruentMarker = new ConfirmMarkerOption(currentMarker.getPosition(),currentMarker.getTitle(),currentMarker.getSnippet(), line,true);
                markerOptionList.add(curruentMarker);
                mGoogleMap.clear();
                getMarkers("businfo",mGoogleMap.getCameraPosition().target);
                getMarkers("subwayinfo",mGoogleMap.getCameraPosition().target);

                for(ConfirmMarkerOption item :markerOptionList) {
                    Marker temp = mGoogleMap.addMarker(item.getMarkerOptions());
                    temp.setTag(item.isMainMarker());

                    if(item.isMainMarker())
                        currentMarker = temp;
                }

                markerOptionList.clear();

            }
        });
        confirmBtn = findViewById(R.id.activity_confirmmap_confirm_btn);
        stationTv = findViewById(R.id.activity_confirmmap_station_tv);

        stationTv.setText(stop_nm);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmMapActivity.this, SetTimeActivity.class);
                intent.putExtra("stop_nm", currentMarker.getTitle());
                intent.putExtra("stop_no", currentMarker.getSnippet());
                intent.putExtra("xcode", String.valueOf(currentPostion.latitude));
                intent.putExtra("ycode", String.valueOf(currentPostion.longitude));
                startActivity(intent);
            }
        });

        stop_no = "0224";
        stop_nm = "서초";
        currentPostion = new LatLng( 37.491898,127.00792);
        line = "2";

        ConfirmMarkerOption curruentMarker = new ConfirmMarkerOption(currentPostion,stop_nm,stop_no, line,true);
        markerOptionList.add(curruentMarker);

        getMarkers("businfo", currentPostion);
        getMarkers("subwayinfo",currentPostion);

    }
    private void getMarkers(String tableName, LatLng latLng){

        DataAdapter mDbHelper = new DataAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        String sql = "SELECT * FROM "
                +tableName+" where ( xcode >="
                +(latLng.latitude-range)+" and xcode <="
                +(latLng.latitude+range)+" ) and ( ycode >="
                +(latLng.longitude-range)+" and ycode <="
                +(latLng.longitude+range)+" )";

        Cursor cursor = mDbHelper.getDataWithQuery(sql);//테이블은 businfo와 subwayinfo  두가지가 있음
        mDbHelper.close();

        for (int i = 0; i < cursor.getCount(); i++) {
            ConfirmMarkerOption temp;
            LatLng sublatLng = new LatLng(cursor.getFloat(3),cursor.getFloat(4));

            if(cursor.getColumnCount() == 5)
                temp= new ConfirmMarkerOption(sublatLng,cursor.getString(2),cursor.getString(1),null,false);
            else
                temp= new ConfirmMarkerOption(sublatLng,cursor.getString(2),cursor.getString(1),cursor.getString(5),false);

            if(!stop_nm.equals(cursor.getString(2)) && !stop_no.equals(cursor.getString(1)))
                markerOptionList.add(temp);

            cursor.moveToNext();
        }
    }

    boolean check = true;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("onMapReady", "@@@@@@@@@@@@@");
        mGoogleMap = googleMap;

        for(ConfirmMarkerOption item :markerOptionList) {
            Marker temp = mGoogleMap.addMarker(item.getMarkerOptions());
            temp.setTag(item.isMainMarker());

            if(item.isMainMarker()) {
                currentMarker = temp;
            }
        }

        markerOptionList.clear();

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPostion));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        //mGoogleMap.setMaxZoomPreference(17);
        mGoogleMap.setMinZoomPreference(17);

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(!(Boolean) marker.getTag()){
                    changeMarkerState(currentMarker, BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_sub));
                    changeMarkerState(marker, BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker));

                    currentMarker = marker;
                    stationTv.setText(currentMarker.getTitle());
                    currentPostion = currentMarker.getPosition();
                    stop_nm = currentMarker.getTitle();
                    stop_no = currentMarker.getSnippet();
                    isInfoWindowShown = true;

                    return false;
                }else{
                    if(isInfoWindowShown)
                        marker.hideInfoWindow();
                    else
                        marker.showInfoWindow();

                    isInfoWindowShown = !isInfoWindowShown;
                    return true;
                }
            }
        });

    }
    private void changeMarkerState(Marker marker, BitmapDescriptor icon){
        marker.setIcon(icon);
        marker.setTag(!((Boolean) marker.getTag()));
    }
    private void getIntentData(){
        Intent intent = getIntent();
        stop_nm = intent.getStringExtra("stop_nm");
        stop_no = intent.getStringExtra("stop_no");
        currentPostion = new LatLng(Double.valueOf(intent.getStringExtra("xcode")), Double.valueOf(intent.getStringExtra("ycode")));
        line =  intent.getStringExtra("line");
    }

}