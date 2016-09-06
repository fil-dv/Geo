package com.example.dfilonenko.geo;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity {

    static EditText txtRegion;
    static EditText txtCity;
    static EditText txtStreet;
    static EditText txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        txtRegion = (EditText) findViewById(R.id.txt_edit_region);
        txtCity = (EditText) findViewById(R.id.txt_edit_city);
        txtStreet = (EditText) findViewById(R.id.txt_edit_street);
        txtPhone = (EditText) findViewById(R.id.txt_edit_phone);
        int index = Mediator.GetSelectedArea();
        initViews(index);

    }

    void initViews(int index){

        if (index >= 0 && index <3){

            Area area = Mediator.GetArea(index);

            txtRegion.setText(area.GetRentAreaAddressRegion());
            txtCity.setText(area.GetRentAreaAddressCity());
            txtStreet.setText(area.GetRentAreaAddressStreet());
            txtPhone.setText(area.GetContactaPhone1());
        }
    }

    public void onClickPhoto(View view) {
        Intent intent = new Intent(EditorActivity.this, PhotoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };

}
