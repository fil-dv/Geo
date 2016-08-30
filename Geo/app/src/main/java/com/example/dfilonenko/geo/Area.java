package com.example.dfilonenko.geo;

import java.math.BigDecimal;

/**
 * Created by D.Filonenko on 30.08.2016.
 */

public class Area {
    public int _areaID;
    //public int _areaTypeID;
    //public String _areaDescription;
    public String _ownerName;
    public String _contactaName;
    public String _contactaPhone1;
    //public String _contactaPhone2;
    //public String _contactaPhone3;
    //public String _legalAddressRegion;
    //public String _legalAddressCity;
    //public String _legalAddressStreet;
    public String _rentAreaAddressRegion;
    public String _rentAreaAddressCity;
    public String _rentAreaAddressStreet;
    public BigDecimal _squareArea;
    public BigDecimal _monthPrice;
    //public Boolean _isAvailable;
    //public int _rating;
    public BigDecimal _latitude;
    public BigDecimal _longitude;

    public Area(int areaID, /*int areaTypeID, String areaDescription,*/ String ownerName,
                String contactaName,String contactaPhone1,/*String contactaPhone2,
                String contactaPhone3, String legalAddressRegion, String legalAddressCity,
                String legalAddressStreet,*/ String rentAreaAddressRegion, String rentAreaAddressCity,
                String rentAreaAddressStreet, BigDecimal squareArea,BigDecimal monthPrice,
                /*Boolean isAvailable, int rating,*/BigDecimal latitude, BigDecimal longitude){

        _areaID = areaID;
        //_areaTypeID = areaTypeID;
        //_areaDescription = areaDescription;
        _ownerName = ownerName;
        _contactaName = contactaName;
        _contactaPhone1 = contactaPhone1;
        //_contactaPhone2 = contactaPhone2;
        //_contactaPhone3 = contactaPhone3;
        //_legalAddressRegion = legalAddressRegion;
        //_legalAddressCity = legalAddressCity;
        //_legalAddressStreet = legalAddressStreet;
        _rentAreaAddressRegion = rentAreaAddressRegion;
        _rentAreaAddressCity = rentAreaAddressCity;
        _rentAreaAddressStreet = rentAreaAddressStreet;
        _squareArea = squareArea;
        _monthPrice = monthPrice;
        //_isAvailable = isAvailable;
        //_rating = rating;
        _latitude = latitude;
        _longitude = longitude;
    }
}
