package com.example.dfilonenko.geo;

import java.util.Date;

/**
 * Created by D.Filonenko on 01.09.2016.
 */

public class Pending {

    private int _pendingID;
    private int _areaID;
    private Date _start;
    private Date _stop;

    public int GetPendingID(){
        return _pendingID;
    }

    public void SetPendingID(int pendingID){
        _pendingID = pendingID;
    }

    public int GetAreaID(){
        return _areaID;
    }

    public void SetAreaID(int areaID){
        _areaID = areaID;
    }

    public Date GetStart(){
        return _start;
    }

    public void SetStart(Date start){
        _start = start;
    }

    public Date GetStop(){
        return _stop;
    }

    public void SetStop(Date stop){
        _stop = stop;
    }
}
