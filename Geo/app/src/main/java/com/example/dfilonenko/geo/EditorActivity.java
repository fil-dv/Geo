package com.example.dfilonenko.geo;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity {

   // static EditText txtId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        //txtId = (EditText) findViewById(R.id.txt_edit_id);
    }

    static void initViews(Area area){
        if (area == null){
            //txtId.setText("1");
        }
    }

    public void onClickPhoto(View view) {
        Intent intent = new Intent(EditorActivity.this, PhotoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };

}
