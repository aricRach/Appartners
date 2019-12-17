package com.example.appartners;

public class Partner extends User {

   private String imgUrl;


    public Partner(){

        super();
        imgUrl="";
    }

    public Partner(String id,String name, String gender, String city, int age, String email){

        super(id,name,gender,city,age,email);
        imgUrl="";

    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
