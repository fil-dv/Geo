package com.example.dfilonenko.geo;

import java.math.BigDecimal;

/**
 * Created by D.Filonenko on 30.08.2016.
 */

public class Area {
    private int _areaID;
    private String _contactaPhone1;
    private String _rentAreaAddressRegion;
    private String _rentAreaAddressCity;
    private String _rentAreaAddressStreet;
   // private BigDecimal _latitude;
   // private BigDecimal _longitude;

    public Area(int areaID, String contactaPhone1, String rentAreaAddressRegion,
                String rentAreaAddressCity, String rentAreaAddressStreet/*, BigDecimal latitude, BigDecimal longitude*/){

        _areaID = areaID;
        _contactaPhone1 = contactaPhone1;
        _rentAreaAddressRegion = rentAreaAddressRegion;
        _rentAreaAddressCity = rentAreaAddressCity;
        _rentAreaAddressStreet = rentAreaAddressStreet;
       // _latitude = latitude;
       // _longitude = longitude;
    }

    public Area(int areaID){

        _areaID = areaID;
    }

    public int GetAreaID(){
        return _areaID;
    }

    public void SetAreaID(int ID){
        _areaID = ID;
    }

    public String GetContactaPhone1(){
        return _contactaPhone1;
    }

    public void SetContactaPhone1(String contactaPhone1){
        _contactaPhone1 = contactaPhone1;
    }

    public String GetRentAreaAddressRegion(){
        return _rentAreaAddressRegion;
    }

    public void SetRentAreaAddressRegion(String rentAreaAddressRegion){
        _rentAreaAddressRegion = rentAreaAddressRegion;
    }

    public String GetRentAreaAddressCity(){
        return _rentAreaAddressCity;
    }

    public void SetRentAreaAddressCity(String rentAreaAddressCity){
        _rentAreaAddressCity = rentAreaAddressCity;
    }

    public String GetRentAreaAddressStreet(){
        return _rentAreaAddressStreet;
    }

    public void SetRentAreaAddressStreet(String rentAreaAddressStreet){
        _rentAreaAddressStreet = rentAreaAddressStreet;
    }

   /* public BigDecimal GetLatitude(){
        return _latitude;
    }

    public void SetLatitude(BigDecimal latitude){
        _latitude = latitude;
    }

    public BigDecimal GetLongitude(){
        return _longitude;
    }

    public void SetLongitude(BigDecimal longitude){
        _longitude = longitude;
    }*/
}
