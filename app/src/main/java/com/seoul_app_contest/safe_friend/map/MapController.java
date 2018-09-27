package com.seoul_app_contest.safe_friend.map;

import android.content.Context;
import android.location.Location;
import android.view.View;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.MarkerOptions;
import com.seoul_app_contest.safe_friend.R;

abstract class MapController {
    protected Location mClientLocation= new Location("ClientLocation");
    protected Location mFollowerLocation = new Location("FollowerLocation");
    protected View.OnClickListener mOnClickListener = null;
    protected MarkerOptions markerOptions = null;
    protected Context mContext;

    MapController(Context context){
        mContext = context;
        markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_on_map_40));
        markerOptions.draggable(true);
    }

    //상대방 위치에 마커를 찍어줘야함.
    abstract MarkerOptions createMaker(MapModel mLatLng);

    abstract GoogleMap.OnMarkerClickListener getOnMarkerClickListener();
    abstract GoogleMap.OnInfoWindowClickListener getOnInfoWindowClickListener();
    abstract GoogleMap.InfoWindowAdapter getInfoWindowAdapter(final Context context);
    abstract void setLocation(Location location);
    abstract void setOpponentLocation(MapModel mapModel);
    abstract void needToActivate();

//
//    //거리 계산을 위함.
//    abstract void setModel(MapModel mMapModel);
//    abstract void setOpponentModel(MapModel mMapModel);
//    abstract void getModel();
//    abstract void getOpponentModel();
}


