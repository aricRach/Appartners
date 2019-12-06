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
    // not need to be in constructor because this is the elements that in personal details.
    private String tellAbout;
    private String phone;
    private String imgUri;

    public user(){}

    public user(String userId,String userName, String userGender, String userCity, int userBirthday,String uid,String email,String aprPrt) {
        this.userId = userId;
        this.userName = userName;
        this.userGender = userGender;
        this.userCity = userCity;
        this.userBirthday = userBirthday;
        this.uId=uid;
        this.email=email;
        this.aprPrt=aprPrt;
        this.tellAbout="";
        this.phone="";
        this.imgUri="";
    }

    public user(user other){

        this.userId=other.userId;
        this.userName=other.userName;
        this.userGender=other.userGender;
        this.userCity=other.userCity;
        this.userBirthday=other.userBirthday;
        this.uId=other.uId;
        this.email=other.email;

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

    public String getTellAbout() {
        return tellAbout;
    }

    public String getPhone() {
        return phone;
    }

    public String getImgUri() {
        return imgUri;
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

    public String getAprPrt() {
        return aprPrt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTellAbout(String tellAbout) {
        this.tellAbout = tellAbout;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
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
