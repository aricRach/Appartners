package com.example.appartners;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Users {
    String UserId;
    EditText UserName;
    RadioGroup UserGender;
    Spinner UserCity;
    DatePicker UserBirthday;

    public Users(){

    }

    public Users(String userId, EditText userName, RadioGroup userGender, Spinner userCity, DatePicker userBirthday) {
        UserId = userId;
        UserName = userName;
        UserGender = userGender;
        UserCity = userCity;
        UserBirthday = userBirthday;
    }

    public String getUserId() {
        return UserId;
    }

    public EditText getUserName() {
        return UserName;
    }

    public RadioGroup getUserGender() {
        return UserGender;
    }

    public Spinner getUserCity() {
        return UserCity;
    }

    public DatePicker getUserBirthday() {
        return UserBirthday;
    }
}
