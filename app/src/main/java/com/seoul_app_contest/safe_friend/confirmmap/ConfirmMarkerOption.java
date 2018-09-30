package com.seoul_app_contest.safe_friend.confirmmap;

import android.app.LauncherActivity;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seoul_app_contest.safe_friend.R;

public class ConfirmMarkerOption{
    private MarkerOptions markerOptions = null;

    private boolean mainMarker;
    ConfirmMarkerOption(LatLng latLng, String title, String snippet, String line , boolean mainMarker){
        this.mainMarker = mainMarker;
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);

        if(line != null) {
            markerOptions.snippet(line+"호선");
        }
        else
            markerOptions.snippet(snippet);

        if(mainMarker)
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker));
        else
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_sub));

    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public boolean isMainMarker() {
        return mainMarker;
    }
}