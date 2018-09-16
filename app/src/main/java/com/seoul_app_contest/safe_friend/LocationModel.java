package com.seoul_app_contest.safe_friend;

import com.google.android.gms.maps.model.LatLng;


public class LocationModel {
    private LatLng clientLocation, follower;

    public LatLng getClientLocation() {
        return clientLocation;
    }

    public LatLng getFollower() {
        return follower;
    }

    public LocationModel setClientLocation(LatLng clientLocation) {
        this.clientLocation = clientLocation;
        return this;
    }

    public LocationModel setFollower(LatLng follower) {
        this.follower = follower;
        return this;
    }
}
