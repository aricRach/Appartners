package com.example.appartners;

import java.util.ArrayList;

public class User implements Comparable<User> {


    private String id;
    private String name;
    private String gender;
    private String city;
    private int age;
    private String email;
    private String tellAbout; // tell about yourself/ your Apartment
    private String phone;
    private ArrayList<User> myFav;

    //private String aprPrt;
  //  private String uId;
    //private Apartment room;
    //private String userId;
    // not need to be in constructor because this is the elements that in personal details.
    //private String imgUrl;

    public User(){

        myFav=new ArrayList<User>();
    }

    // constructor for register page
    public User(String id,String name, String gender, String city, int age, String email) {

        this.id=id;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.age = age;
        this.email=email;
        this.tellAbout="";
        this.phone="";
        this.myFav=new ArrayList<User>();
    }

    public User(User other){

        this.id=other.id;
        this.name =other.name;
        this.gender =other.gender;
        this.city =other.city;
        this.age =other.age;
        this.email=other.email;
        this.tellAbout=other.tellAbout;
        this.phone=other.phone;
        this.myFav=other.myFav;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<User> getMyFav() {
        return myFav;
    }

    public void setMyFav(ArrayList<User> myFav) {
        this.myFav = myFav;
    }

    public void addFav(User fav){

        myFav.add(fav);
    }

    public User getFav(int i){

        return myFav.get(i);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public String getTellAbout() {
        return tellAbout;
    }

    public String getPhone() {
        return phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
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


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", tellAbout='" + tellAbout + '\'' +
                ", phone='" + phone + '\'' +
                ", myFav=" + myFav +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }


    @Override
    public int compareTo(User other) {

        if (other.getEmail()==this.getEmail()){

            return 0;
        }else{

            return -1;
        }
    }


}
