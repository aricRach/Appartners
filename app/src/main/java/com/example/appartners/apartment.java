package com.example.appartners;

import java.util.ArrayList;

public class apartment {

    private double price;
    private int occupants;
    private String street;
    private String phone;
    private int numOfRooms;

    private ArrayList<String> imagesUri;

    public apartment(){

        this.price=0;
        this.occupants=0;
        this.street="";
        this.phone="";
        this.numOfRooms=0;
        this.imagesUri=new ArrayList<String>();
    }

    public apartment(double price,int occupants,String street,String phone,int numOfRooms)
    {

        this.price=price;
        this.occupants=occupants;
        this.street=street;
        this.phone=phone;
        this.numOfRooms=numOfRooms;
        this.imagesUri=new ArrayList<String>();
    }

    public apartment(apartment other){

        this.price=other.price;
        this.occupants=other.occupants;
        this.street=other.street;
        this.phone=other.phone;
        this.numOfRooms=other.numOfRooms;
        this.imagesUri=other.imagesUri;
    }

    public double getPrice() {
        return price;
    }

    public int getOccupants() {
        return occupants;
    }

    public String getStreet() {
        return street;
    }

    public String getPhone() {
        return phone;
    }

    public int getNumOfRooms() {
        return numOfRooms;
    }

    public ArrayList<String> getImagesUri() {
        return imagesUri;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOccupants(int occupants) {
        this.occupants = occupants;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public void setImagesUri(ArrayList<String> imagesUri) {
        this.imagesUri = imagesUri;
    }

    public void addImg(String img){

        imagesUri.add(img);
    }

    public String getImg(int i){

        return imagesUri.get(i);
    }


}
