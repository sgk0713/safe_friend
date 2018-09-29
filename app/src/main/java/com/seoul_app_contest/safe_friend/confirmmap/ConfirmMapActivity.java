package com.seoul_app_contest.safe_friend.confirmmap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.adapter.DataAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class ConfirmMapActivity extends AppCompatActivity implements OnMapReadyCallback, PlacesListener {
    private GoogleMap mGoogleMap = null;
    private SQLiteDatabase db = null;
    private Marker mMarker = null;
    private double lat = 0.0;
    private double lng = 0.0;
    List<Marker> previous_marker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("onCreate", "@@@@@@@@@@@@@");

        setContentView(R.layout.activity_confirmmap);
        previous_marker = new ArrayList<Marker>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.confirmMap);
        mapFragment.getMapAsync(this);

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


    }
    boolean check = true;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("onMapReady", "@@@@@@@@@@@@@");
        mGoogleMap = googleMap;
        LatLng SEOUL = new LatLng(37.491898, 127.00792);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
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
                        showPlaceInformation(mGoogleMap.getCameraPosition().target);
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


    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }

    @Override
    public void onPlacesSuccess(final List<Place> places) {
        Log.d("onPlacesSuccess",places.toString());
        runOnUiThread(new Runnable() {

            @Override

            public void run() {

                for (noman.googleplaces.Place place : places) {

                    LatLng latLng

                            = new LatLng(place.getLatitude()

                            , place.getLongitude());


                    //String markerSnippet = getCurrentAddress(latLng);


                    MarkerOptions markerOptions = new MarkerOptions();

                    markerOptions.position(latLng);

                    markerOptions.title(place.getName());

                    markerOptions.snippet("test");

                    Marker item = mGoogleMap.addMarker(markerOptions);

                    previous_marker.add(item);


                }


                //중복 마커 제거
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_marker);
                previous_marker.clear();
                previous_marker.addAll(hashSet);


            }

        });
    }

    @Override
    public void onPlacesFinished() {

    }

    public void showPlaceInformation(LatLng location)
    {
        Log.d("showPlaceInformation",previous_marker.toString());
        mGoogleMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(ConfirmMapActivity.this)
                .key("AIzaSyDG3gvH_7KOGj-4tfXoVrgS1EEfVKUb-Ow")
                .latlng(location.latitude, location.longitude)//현재 위치
                .radius(500) //500 미터 내에서 검색
                .type(PlaceType.BUS_STATION)
                .build()
                .execute();

//        new NRPlaces.Builder()
//                .listener(ConfirmMapActivity.this)
//                .key("AIzaSyDG3gvH_7KOGj-4tfXoVrgS1EEfVKUb-Ow")
//                .latlng(location.latitude, location.longitude)//현재 위치
//                .radius(100) //500 미터 내에서 검색
//                .type(PlaceType.SUBWAY_STATION)
//                .build()
//                .execute();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        check = !check;
    }
}
