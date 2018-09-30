package com.seoul_app_contest.safe_friend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestModel {
    String street;
    String requestTime;
    String meetingTime;
    String location;
    String uid;
    boolean isWaiting;

    public RequestModel() {
    }

    public RequestModel(String meetingTime, String location, String street, String uid) {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = sdf.format(new Date(now));

        this.requestTime = getTime;
        this.meetingTime = meetingTime;
        this.location = location;
        this.street = street;
        this.uid = uid;
        this.isWaiting = true;
    }

    public String getStreet() {
        return street;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public String getMeetingTime() {
        return meetingTime;
    }
    public String getLocation() {
        return location;
    }

    public String getUid() {
        return uid;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }
}
