package com.seoul_app_contest.safe_friend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestModel {
    String requestTime;
    String meetingTime;
    String location;
    String uid;


    public RequestModel(String meetingTime, String location, String uid) {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(new Date(now));

        this.requestTime = getTime;
        this.meetingTime = meetingTime;
        this.location = location;
        this.uid = uid;
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
}
