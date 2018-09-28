package com.seoul_app_contest.safe_friend.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class PostDto implements Parcelable {
    String zipNo;
    String lnmAdres;
    String rnAdres;

    public PostDto(String zipNo, String lnmAdres, String rnAdres) {
        this.zipNo = zipNo;
        this.lnmAdres = lnmAdres;
        this.rnAdres = rnAdres;
    }

    public String getZipNo() {
        return zipNo;
    }

    public String getLnmAdres() {
        return lnmAdres;
    }

    public String getRnAdres() {
        return rnAdres;
    }


    public PostDto(Parcel src) {
        this.zipNo = src.readString();
        this.lnmAdres = src.readString();
        this.rnAdres = src.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PostDto createFromParcel(Parcel src) {
            return new PostDto(src);
        }

        public PostDto[] newArray(int size) {
            return new PostDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(zipNo);
        parcel.writeString(lnmAdres);
        parcel.writeString(rnAdres);
    }
}
