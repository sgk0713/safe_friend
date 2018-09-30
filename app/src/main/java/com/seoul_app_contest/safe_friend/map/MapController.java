package com.seoul_app_contest.safe_friend.map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.seoul_app_contest.safe_friend.R;

abstract class MapController {

    protected Location mClientLocation= new Location("ClientLocation");
    protected Location mFollowerLocation = new Location("FollowerLocation");
    protected View.OnClickListener mOnClickListener = null;
    protected MarkerOptions markerOptions = null;
    protected Context mContext;
    protected boolean distanceChecker = true;
    protected Activity mActivity = null;

    MapController(Context context){

        mContext = context;
        mActivity = (Activity)mContext;
        mClientLocation.setLatitude(0.0);
        markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_on_map_40));
        markerOptions.draggable(true);
        int imageViewHeight = mActivity.findViewById(R.id.mapBottomBarInfo_B).getHeight();
        ((ImageButton)mActivity.findViewById(R.id.mapBottomBarInfo_B_CallButton)).setMaxHeight(imageViewHeight);
        ((ImageButton)mActivity.findViewById(R.id.mapBottomBarInfo_B_CallButton)).setMaxWidth(imageViewHeight);
        ((ImageButton)mActivity.findViewById(R.id.mapBottomBarInfo_A_CallButton)).setMaxHeight(imageViewHeight);
        ((ImageButton)mActivity.findViewById(R.id.mapBottomBarInfo_A_CallButton)).setMaxWidth(imageViewHeight);

        ((ImageButton)mActivity.findViewById(R.id.mapBottomBarInfo_B_CallButton)).setMinimumHeight(imageViewHeight);
        ((ImageButton)mActivity.findViewById(R.id.mapBottomBarInfo_B_CallButton)).setMinimumWidth(imageViewHeight);
        ((ImageButton)mActivity.findViewById(R.id.mapBottomBarInfo_A_CallButton)).setMinimumHeight(imageViewHeight);
        ((ImageButton)mActivity.findViewById(R.id.mapBottomBarInfo_A_CallButton)).setMinimumWidth(imageViewHeight);

    }

    //상대방 위치에 마커를 찍어줘야함.
    abstract MarkerOptions createMaker(MapModel mLatLng);

    abstract GoogleMap.OnMarkerClickListener getOnMarkerClickListener();
    abstract GoogleMap.OnInfoWindowClickListener getOnInfoWindowClickListener();
    abstract GoogleMap.InfoWindowAdapter getInfoWindowAdapter(final Context context);
    abstract void setLocation(Location location);
    abstract Location getLocation();
    abstract void setOpponentLocation(MapModel mapModel);
    abstract Location getOpponentLocation();
    abstract void needToActivate();

    public void changeViewInfo(){
        ((TextView)mActivity.findViewById(R.id.mapBottomBarInfo_A_Name)).setText("관리자A/사용자");
        ((TextView)mActivity.findViewById(R.id.mapBottomBarInfo_A_Role)).setText("관리자A/사용자 지역");
        ((TextView)mActivity.findViewById(R.id.mapBottomBarInfo_A_Phone)).setText("관리자A/사용자 핸드폰 번호");

        ((TextView)mActivity.findViewById(R.id.mapBottomBarInfo_A_Name)).setText("관리자B");
        ((TextView)mActivity.findViewById(R.id.mapBottomBarInfo_A_Role)).setText("관리자B");
        ((TextView)mActivity.findViewById(R.id.mapBottomBarInfo_A_Phone)).setText("관리자B");
    }
//
//    //거리 계산을 위함.
//    abstract void setModel(MapModel mMapModel);
//    abstract void setOpponentModel(MapModel mMapModel);
//    abstract void getModel();
//    abstract void getOpponentModel();
}



