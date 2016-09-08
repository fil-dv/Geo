package com.example.dfilonenko.geo;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditorActivity extends AppCompatActivity {

    EditText txtRegion;
    EditText txtCity;
    EditText txtStreet;
    EditText txtPhone;
    Button btnSave;
    Button btnPhoto;

    MySaveTask mst;
    Area area;

    boolean isNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        txtRegion = (EditText) findViewById(R.id.txt_edit_region);
        txtCity = (EditText) findViewById(R.id.txt_edit_city);
        txtStreet = (EditText) findViewById(R.id.txt_edit_street);
        txtPhone = (EditText) findViewById(R.id.txt_edit_phone);
        btnSave = (Button) findViewById(R.id.btn_edit_submit);
        btnPhoto = (Button) findViewById(R.id.btn_edit_photo);
        int index = Mediator.GetSelectedArea();
        isNew = false;
        initViews(index);
    }

    void initViews(int index) {

        if (index >= 0 && index < 3) {

            area = Mediator.GetArea(index);

            txtRegion.setText(area.GetRentAreaAddressRegion());
            txtCity.setText(area.GetRentAreaAddressCity());
            txtStreet.setText(area.GetRentAreaAddressStreet());
            txtPhone.setText(area.GetContactaPhone1());
        } else {
            btnPhoto.setEnabled(false);
            isNew = true;
        }
    }


    public void onClickSave(View view) {

        mst = new MySaveTask();
        mst.execute();
    }


    public void onClickPhoto(View view) {
        Intent intent = new Intent(EditorActivity.this, PhotoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public class MySaveTask extends AsyncTask<Void, Void, Void> {

        String GetUrl(){

            String idStr = "";
            //0!Киевская!Бровары!ул. Воссоединения д. 11, кв. 148!0501909882/50.5090258/30.7827258/
            if(isNew){
                idStr = String.valueOf(0);
            }
            else{
                idStr = String.valueOf(area.GetAreaID());
            }
            String coordStr = "/" + Mediator.GetLatitude() + "/" + Mediator.GetLongtitude() + "/";

            String areaStr = idStr + "g"
                                   + txtRegion.getText() + "g"
                                   + txtCity.getText() + "g"
                                   + txtStreet.getText() + "g"
                                   + txtPhone.getText() + coordStr;
            String url = "http://www.geo.somee.com/api/Coord/AddOrUpdateArea/" + areaStr;
            return url;
        }

        @Override
        protected Void doInBackground(Void... params) {

            String responseString = null;
            String myUrl = GetUrl();

            responseString = updateArea(myUrl, 10000000);

            if(isNew){
                int areaId = Integer.parseInt(responseString);
                area.SetAreaID(areaId);
            }
            return null;
        }

        public String updateArea(String url, int timeout) {
            HttpURLConnection c = null;
            try {
                URL u = new URL(url);
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                //c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(timeout);
                c.setReadTimeout(timeout);
                c.connect();
                int status = c.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Toast.makeText(EditorActivity.this, "Данные успешно внесены в базу.", Toast.LENGTH_LONG).show();
            //TimeUnit.SECONDS.sleep(1);
            btnPhoto.setEnabled(true);
        }
    }
}


