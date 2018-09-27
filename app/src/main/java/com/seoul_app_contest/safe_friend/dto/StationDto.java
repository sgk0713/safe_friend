package com.seoul_app_contest.safe_friend.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class StationDto implements Parcelable{


    public String stop_no;//204000140
    public String stop_nm;//(구)단대동주민센터
    public String xcode;//127.1579118603
    public String ycode;//37.4477630573
    public String line;//37.4477630573


    public StationDto() {
    }

    protected StationDto(Parcel in) {
        stop_no = (String) in.readValue(null);
        stop_nm = (String) in.readValue(null);
        xcode = (String) in.readValue(null);
        ycode = (String) in.readValue(null);
        line = (String) in.readValue(null);
    }


    public StationDto(String stop_no, String stop_nm, String xcode, String ycode, String line) {
        this.stop_no = stop_no;
        this.stop_nm = stop_nm;
        this.xcode = xcode;
        this.ycode = ycode;
        this.line = line;
    }

    public static final Creator<StationDto> CREATOR = new Creator<StationDto>() {
        @Override
        public StationDto createFromParcel(Parcel in) {
            return new StationDto(in);
        }

        @Override
        public StationDto[] newArray(int size) {
            return new StationDto[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(stop_no);
        dest.writeValue(stop_nm);
        dest.writeValue(xcode);
        dest.writeValue(ycode);
        dest.writeValue(line);
    }
}
