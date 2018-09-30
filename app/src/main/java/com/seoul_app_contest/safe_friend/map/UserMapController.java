package com.seoul_app_contest.safe_friend.map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seoul_app_contest.safe_friend.R;

class UserMapController extends MapController {

    UserMapController(Context context) {
        super(context);
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.mapCancelButton: {
                        CancelDialog addCateDialog = new CancelDialog(mContext);
                        addCateDialog.show();
                    }
                    break;
                }
            }
        };

        ((Activity)context).findViewById(R.id.mapBottomBarInfo_A_CallButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClickA", "@@@@@@");
            }
        });

        ((Activity)context).findViewById(R.id.mapBottomBarInfo_B_CallButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClickB", "@@@@@@");
            }
        });
    }

    @Override
    public MarkerOptions createMaker(MapModel mMapModel) {
        LatLng mLatLng = new LatLng(mMapModel.getmLat(),mMapModel.getmLng());
        markerOptions.position(mLatLng);
        markerOptions.title("여성지키미");
        markerOptions.snippet("현재 위치");
        return markerOptions;
    }

    @Override
    public GoogleMap.OnMarkerClickListener getOnMarkerClickListener() {
        GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("OnInfoWindowListener", "@@@@@@@@@@@@@@@@@@");
                return false;
            }
        };

        return mOnMarkerClickListener;
    }

    @Override
    public GoogleMap.OnInfoWindowClickListener getOnInfoWindowClickListener() {
        GoogleMap.OnInfoWindowClickListener mOnInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.d("OnInfoWindowListener", "@@@@@@@@@@@@@@@@@@");
            }
        };

        return mOnInfoWindowClickListener;
    }

    @Override
    public GoogleMap.InfoWindowAdapter getInfoWindowAdapter(final Context context) {

        GoogleMap.InfoWindowAdapter mInfoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                return inflater.inflate(R.layout.marker_info_window, null);
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        };
        return mInfoWindowAdapter;
    }

    @Override
    void setLocation(Location location) {
        mClientLocation = location;
    }

    @Override
    Location getLocation() {
        return mClientLocation;
    }

    @Override
    void setOpponentLocation(MapModel mapModel) {
        mFollowerLocation.setLatitude(mapModel.getmLat());
        mFollowerLocation.setLongitude(mapModel.getmLng());
    }

    @Override
    Location getOpponentLocation() {
        return mFollowerLocation;
    }

    @Override
    void needToActivate() {
        //유저는 가까우면 취소 버튼을 활성화 할 수 없음.
        if(distanceChecker && mClientLocation.getLatitude() != 0.0) {
            Log.d("needToActivate1","@@@@@"+mClientLocation.getLatitude());
            if (mClientLocation.distanceTo(mFollowerLocation) <= 100) {
                Log.d("needToActivate2","@@@@@"+mClientLocation.distanceTo(mFollowerLocation));
                ((Activity) mContext).findViewById(R.id.mapCancelButton).setVisibility(View.GONE);
                distanceChecker = false;
            } else {
                ((Activity) mContext).findViewById(R.id.mapCancelButton).setVisibility(View.VISIBLE);
            }
        }
    }


}
