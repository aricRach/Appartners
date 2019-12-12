package com.example.appartners;

import java.util.ArrayList;

public class apartment {

    private double price;
    private int occupants;
    private String street;
    private String roomType;
    private int numOfRooms;

    private ArrayList<String> imagesUri;

    public apartment(){

        this.price=0;
        this.occupants=0;
        this.street="";
        this.roomType ="";
        this.numOfRooms=0;
        this.imagesUri=new ArrayList<String>();
    }

    public apartment(double price, int occupants, String street, String roomType, int numOfRooms)
    {

        this.price=price;
        this.occupants=occupants;
        this.street=street;
        this.roomType = roomType;
        this.numOfRooms=numOfRooms;
        this.imagesUri=new ArrayList<String>();
    }

    public apartment(apartment other){

        this.price=other.price;
        this.occupants=other.occupants;
        this.street=other.street;
        this.roomType =other.roomType;
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

    public String getRoomType() {
        return roomType;
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

    public void setRoomType(String roomType) {
        this.roomType = roomType;
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