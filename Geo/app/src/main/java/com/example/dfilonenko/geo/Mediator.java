package com.example.dfilonenko.geo;

import java.util.ArrayList;

/**
 * Created by D.Filonenko on 01.09.2016.
 */

public final class Mediator {

    private ArrayList<Area> _areaList;

    public void SetAreaList(ArrayList<Area> areaList){

        _areaList = areaList;
    }

    public Area GetArea(int index){

        if(index > 0 && index < _areaList.size()){

            return _areaList.get(index);
        }else{
            return null;
        }
    }


}
