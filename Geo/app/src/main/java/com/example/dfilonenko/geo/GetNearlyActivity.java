package com.example.dfilonenko.geo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class GetNearlyActivity extends AppCompatActivity implements View.OnClickListener {

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
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_obj_2);
        btn_1.setOnClickListener(this);
        btn_3 = (Button) findViewById(R.id.btn_obj_3);
        btn_1.setOnClickListener(this);
        btn_new = (Button) findViewById(R.id.btn_obj_new);

        areaList = getJson();
    }

    ArrayList<Area> getJson(){
        return  null;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(GetNearlyActivity.this, EditorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        if(areaList != null) {

            /*Intent intent = new Intent(GetNearlyActivity.this, EditorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
*/
            switch (view.getId()) {
                case R.id.btn_obj_1:
                    EditorActivity.initViews(areaList.get(0)); //InitEditorActivity(areaList.get(0));
                    break;
                case R.id.btn_obj_2:
                    EditorActivity.initViews(areaList.get(1)); //InitEditorActivity(areaList.get(1));
                    break;
                case R.id.btn_obj_3:
                    EditorActivity.initViews(areaList.get(2)); //InitEditorActivity(areaList.get(2));
                    break;
                case R.id.btn_obj_new:
                    EditorActivity.initViews(null); //InitEditorActivity(null);
                    break;
            }
        }
    }

    void InitEditorActivity(Area area){


    }


}
