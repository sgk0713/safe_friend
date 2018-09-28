package com.seoul_app_contest.safe_friend;

public class RequestModel {
    String time;
    String location;
    String uid;

    public RequestModel(String time, String location, String uid) {
        this.time = time;
        this.location = location;
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getUid() {
        return uid;
    }
}
