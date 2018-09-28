package com.seoul_app_contest.safe_friend.dto;

public class UserDto {
    int userType;
    String uid;
    String email;
    String name;
    String birthDay;
    String gender;
    String address;
    String phoneNum;
    int state;

    public UserDto(int userType, String uid, String email, String name, String birthDay, String gender, String address, String phoneNum, int state) {
        this.userType = userType;
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.address = address;
        this.phoneNum = phoneNum;
        this.state = state;
    }
}
