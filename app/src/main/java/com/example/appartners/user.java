package com.example.appartners;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class user {
    private String userId;
    private String userName;
    private String userGender;
    private String userCity;
    private int userBirthday;
    private String uId;
    private String email;
    private String aprPrt;

    public user(){}

    public user(String userId,String userName, String userGender, String userCity, int userBirthday,String uid,String email,String aprPrt) {
        this.userId = userId;
        this.userName = userName;
        this.userGender = userGender;
        this.userCity = userCity;
        this.userBirthday = userBirthday;
        this.uId=uid;
        this.email=email;
        this.aprPrt = aprPrt;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public int getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(int userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAprPrt() {
        return aprPrt;
    }

    public void setAprPrt(String aprPrt) {
        this.aprPrt = aprPrt;
    }

    @Override
    public String toString() {
        return "user{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userBirthday=" + userBirthday +
                ", uId='" + uId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
