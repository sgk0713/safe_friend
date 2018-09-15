package com.seoul_app_contest.safe_friend.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class StationDto implements Parcelable{


    String arsId;//48049
    String posX;//213971.35897030096
    String posY;//438724.7980466848
    String stId;//204000140
    String stNm;//(구)단대동주민센터
    String tmX;//127.1579118603
    String tmY;//37.4477630573


    public StationDto() {
    }

    protected StationDto(Parcel in) {
        arsId = (String) in.readValue(null);
        posX = (String) in.readValue(null);
        posY = (String) in.readValue(null);
        stId = (String) in.readValue(null);
        stNm = (String) in.readValue(null);
        tmX = (String) in.readValue(null);
        tmY = (String) in.readValue(null);

    }



    public StationDto(String arsId, String posX, String posY, String stId, String stNm, String tmX, String tmY) {
        this.arsId = arsId;
        this.posX = posX;
        this.posY = posY;
        this.stId = stId;
        this.stNm = stNm;
        this.tmX = tmX;
        this.tmY = tmY;
    }

    public String getArsId() {
        return arsId;
    }

    public String getPosX() {
        return posX;
    }

    public String getPosY() {
        return posY;
    }

    public String getStId() {
        return stId;
    }

    public String getStNm() {
        return stNm;
    }

    public String getTmX() {
        return tmX;
    }

    public String getTmY() {
        return tmY;
    }

    @Override
    public String toString() {
        return "arsId : " + arsId + "\n"
                +"posX : " + posX + "\n"
                +"posY : " + posY + "\n"
                +"stId : " + stId + "\n"
                +"stNm : " + stNm + "\n"
                +"tmX : " + tmX + "\n"
                +"tmY : " + tmY + "\n";
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
        dest.writeValue(arsId);
        dest.writeValue(posX);
        dest.writeValue(posY);
        dest.writeValue(stId);
        dest.writeValue(stNm);
        dest.writeValue(tmX);
        dest.writeValue(tmY);
    }
}
