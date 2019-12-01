package com.example.appartners;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class user {
    private String UserId;
    private String UserName;
    private String UserGender;
    private String UserCity;
    private int UserBirthday;

    public user(){

    }

    public user(String userId, String userName, String userGender, String userCity, int userBirthday) {
        UserId = userId;
        UserName = userName;
        UserGender = userGender;
        UserCity = userCity;
        UserBirthday = userBirthday;
    }

    public String getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserGender() {
        return UserGender;
    }

    public String getUserCity() {
        return UserCity;
    }

    public int getUserBirthday() {
        return UserBirthday;
    }


}
