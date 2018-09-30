package com.seoul_app_contest.safe_friend.dto;

public class UserDto {
    String uid;
    String email;
    String name;
    String birthDay;
    String gender;
    String address;
    String phoneNum;
    String profile;
    int state;
    int useNum;
    int likeNum;
    int kindNum;
    int bestNum;

    public UserDto() {
    }

    public UserDto(String uid, String email, String name, String birthDay, String gender, String address, String phoneNum, int state) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.address = address;
        this.phoneNum = phoneNum;
        this.state = state;
        this.useNum = 0;
        this.profile = "";
    }

    public String getProfile() {
        return profile;
    }

    public int getUseNum() {
        return useNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public int getKindNum() {
        return kindNum;
    }

    public int getBestNum() {
        return bestNum;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public int getState() {
        return state;
    }
}
