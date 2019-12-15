package com.example.appartners;

import java.util.ArrayList;

public class user implements Comparable<user> {

    private apartment room;
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
    private String imgUrl;
    private ArrayList<user> myFav;

    public user(){

        myFav=new ArrayList<user>();
    }

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
        this.imgUrl ="";
        this.room=new apartment();
        this.myFav=new ArrayList<user>();
    }

    public user(user other){

        this.userId=other.userId;
        this.userName=other.userName;
        this.userGender=other.userGender;
        this.userCity=other.userCity;
        this.userBirthday=other.userBirthday;
        this.uId=other.uId;
        this.email=other.email;
        this.room=other.room;
        this.myFav=other.myFav;

    }

    public ArrayList<user> getMyFav() {
        return myFav;
    }

    public void setMyFav(ArrayList<user> myFav) {
        this.myFav = myFav;
    }

    public void addFav(user fav){

        myFav.add(fav);
    }

    public user getFav(int i){

        return myFav.get(i);
    }

    public apartment getRoom() {
        return room;
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

    public String getImgUrl() {

        if(aprPrt!=null){

            if(aprPrt.equals("Searching partner")){

                if(getRoom().getImagesUri().size()>0){

                    return getRoom().getImg(0);

                }
            }
        }
        return imgUrl;
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

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setAprPrt(String aprPrt) {
        this.aprPrt = aprPrt;
    }

    public void setRoom(apartment room) {
        this.room = room;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        user user = (user) o;
        return email.equals(user.email);
    }


    @Override
    public int compareTo(user other) {

        if (other.getEmail()==this.getEmail()){

            return 0;
        }else{

            return -1;
        }
    }


}
