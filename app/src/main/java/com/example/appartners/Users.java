package com.example.appartners;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Users {
    String UserId;
    String UserName;
    String UserGender;
    String UserCity;
    String UserBirthday;

    public Users(){

    }

    public Users(String userId, String userName, String userGender, String userCity, String userBirthday) {
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

    public String getUserBirthday() {
        return UserBirthday;
    }
}
