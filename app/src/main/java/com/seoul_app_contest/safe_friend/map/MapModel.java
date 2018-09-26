package com.seoul_app_contest.safe_friend.map;

public class MapModel{
    private double mLat;
    private double mLng;

    MapModel(){}
    MapModel(double mLat, double mLng){
        this.mLat = mLat;
        this.mLng = mLng;
    }
    public double getmLat() {
        return mLat;
    }

    public double getmLng() {
        return mLng;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public void setmLng(double mLng) {
        this.mLng = mLng;

    }

    @Override
    public String toString() {
        return String.valueOf(mLat)+","+String.valueOf(mLng);
    }
}
