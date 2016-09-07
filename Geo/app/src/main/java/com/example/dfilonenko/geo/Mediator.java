package com.example.dfilonenko.geo;

import java.util.ArrayList;

/**
 * Created by D.Filonenko on 01.09.2016.
 */

public final class Mediator {

    private static ArrayList<Area> _areaList;
    private static int _selectedArea;

    public static int GetSelectedArea(){

        return  _selectedArea;
    }

    public static void SetSelectedArea(int index){

        _selectedArea = index;
    }

    public static ArrayList<Area> GetAreaList(){

        return  _areaList;
    }

    public static void SetAreaList(ArrayList<Area> areaList){

        _areaList = areaList;
    }

    public static Area GetArea(int index){

        if(index >= 0 && index < _areaList.size()){

            return _areaList.get(index);
        }else{
            return null;
        }
    }


}
