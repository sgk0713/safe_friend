package com.seoul_app_contest.safe_friend.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seoul_app_contest.safe_friend.EndServiceActivity;
import com.seoul_app_contest.safe_friend.R;

class FollowerMapController extends MapController {

    FollowerMapController(Context context) {
        super(context);

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.mapCancelButton: {
                            Intent intent = new Intent(mContext,EndServiceActivity.class);
                            mContext.startActivity(intent);
                             ((Activity)mContext).finish();
                    }
                    break;
                }
            }
        };
        ((Button)mActivity.findViewById(R.id.mapRefreadButtonFollower)).setText("사용자");
        ((TextView)mActivity.findViewById(R.id.mapInfoBottomTextView)).setText("사용자 상세정보");
        mActivity.findViewById(R.id.mapBottomBarInfo_B).setVisibility(View.GONE);
        Guideline guideLine = mActivity.findViewById(R.id.mainMapBottomBarGuideline);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) guideLine.getLayoutParams();
        params.guidePercent = 0.85f; // 45% // range: 0 <-> 1
        guideLine.setLayoutParams(params);

    }

    @Override
    public MarkerOptions createMaker(MapModel mMapModel) {
        LatLng mLatLng = new LatLng(mMapModel.getmLat(),mMapModel.getmLng());
        markerOptions.position(mLatLng);
        markerOptions.title("사용자");
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
//                redirectCall();
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
                View view =  inflater.inflate(R.layout.marker_info_window, null);
                ((TextView)view.findViewById(R.id.infoA_Type)).setText("신청자");
                ((ViewManager)view).removeView(view.findViewById(R.id.info_b));
                return view;
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
        mFollowerLocation = location;
    }

    @Override
    Location getLocation() {
        return mFollowerLocation;
    }

    @Override
    void setOpponentLocation(MapModel mapModel) {
        mClientLocation.setLatitude(mapModel.getmLat());
        mClientLocation.setLongitude(mapModel.getmLng());
    }

    @Override
    Location getOpponentLocation() {
        return mClientLocation;
    }

    @Override
    void needToActivate() {
        //지키미는 멀면 취소(종료) 버튼을 활성화 할 수 없음.
        if(distanceChecker && mClientLocation.getLatitude() != 0.0) {
            if (mClientLocation.distanceTo(mFollowerLocation) <= 100) {
                ((Activity) mContext).findViewById(R.id.mapCancelButton).setVisibility(View.VISIBLE);
                distanceChecker = false;
            } else {
                ((Activity) mContext).findViewById(R.id.mapCancelButton).setVisibility(View.GONE);
            }
        }
    }

}
