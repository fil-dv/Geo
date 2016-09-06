package com.example.dfilonenko.geo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class GetNearlyActivity extends AppCompatActivity{

    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_new;
    ArrayList<Area> areaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_nearly);

        btn_1 = (Button) findViewById(R.id.btn_obj_1);
        btn_2 = (Button) findViewById(R.id.btn_obj_2);
        btn_3 = (Button) findViewById(R.id.btn_obj_3);
        btn_new = (Button) findViewById(R.id.btn_obj_new);
        areaList = Mediator.GetAreaList();
        btn_1.setText(areaList.get(0).GetRentAreaAddressCity() + ", " + areaList.get(0).GetRentAreaAddressStreet());
        btn_2.setText(areaList.get(1).GetRentAreaAddressCity() + ", " + areaList.get(1).GetRentAreaAddressStreet());
        btn_3.setText(areaList.get(2).GetRentAreaAddressCity() + ", " +  areaList.get(2).GetRentAreaAddressStreet());
    }

    public void btn1_onClick(View view) {
        Mediator.SetSelectedArea(0);
        InitActivity();
    }

    public void btn2_onClick(View view) {
        Mediator.SetSelectedArea(1);
        InitActivity();
    }

    public void btn3_onClick(View view) {
        Mediator.SetSelectedArea(2);
        InitActivity();
    }

    public void btnNew_onClick(View view) {
        Mediator.SetSelectedArea(-1);
        InitActivity();
    }

    void InitActivity(){
        Intent intent = new Intent(GetNearlyActivity.this, EditorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
