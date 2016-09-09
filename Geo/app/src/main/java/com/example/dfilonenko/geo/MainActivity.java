package com.example.dfilonenko.geo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener {

    MyTask mt;

    TextView tvEnabledGPS;
    TextView tvLocationGpsLat;
    TextView tvLocationGpsLong;
    TextView tvEnabledNet;
    TextView tvLocationNetLat;
    TextView tvLocationNetLong;
    Button btnGetNearly;

    boolean isGpsEnable;
    boolean isNetEnable;
    boolean isLatSet;
    boolean isLongSet;

    double _latitudeGPS;
    double _longtitudeGPS;
    double _latitudeNet;
    double _longtitudeNet;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvEnabledGPS = (TextView) findViewById(R.id.txt_GPS);
        tvLocationGpsLat = (TextView) findViewById(R.id.LocationGpsLat);
        tvLocationGpsLong = (TextView) findViewById(R.id.LocationGpsLong);
        tvEnabledNet = (TextView) findViewById(R.id.txt_Net);
        tvLocationNetLat = (TextView) findViewById(R.id.LocationNetLat);
        tvLocationNetLong = (TextView) findViewById(R.id.LocationNetLong);
        btnGetNearly = (Button) findViewById(R.id.btn_get_nearly);

        isGpsEnable = false;
        isNetEnable = false;
        isLatSet = false;
        isLongSet = false;

        _latitudeGPS = 0;
        _longtitudeGPS = 0;
        _latitudeNet = 0;
        _longtitudeNet = 0;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        btnGetNearly.setOnClickListener(this);

        //ProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100 , 10, locationListener);
            checkEnabled();
        }catch (SecurityException ex){
                ex.printStackTrace();
            }
        }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            locationManager.removeUpdates(locationListener);
        }catch (SecurityException ex)
        {
            ex.printStackTrace();
        }
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
            if(isNetEnable == true || isGpsEnable == true){
                if(isLongSet == true  && isLatSet == true) {
                    btnGetNearly.setEnabled(true);
                }else{
                    btnGetNearly.setEnabled(false);
                }
            }else{
                btnGetNearly.setEnabled(false);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            try{
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
            }catch (SecurityException ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private void showLocation(Location location) {
        if (location == null) {
            isLatSet = false;
            isLongSet = false;
            return;
        }
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGpsLat.setText("Широта: " + location.getLatitude());
            _latitudeGPS = location.getLatitude();
            isLatSet = true;

            tvLocationGpsLong.setText("Долгота: " + location.getLongitude());
            isLongSet = true;
            _longtitudeGPS = location.getLongitude();

        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            tvLocationNetLat.setText("Широта: " + location.getLatitude());
            isLatSet = true;
            _latitudeNet = location.getLatitude();
            tvLocationNetLong.setText("Долгота: " + location.getLongitude());
            isLongSet = true;
            _longtitudeNet = location.getLongitude();
        }
    }

    private void checkEnabled() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            isGpsEnable = true;
            tvEnabledGPS.setTextColor(Color.GREEN);
            tvEnabledGPS.setText("GPS доступен.");
        }else{
            isGpsEnable = false;
            tvEnabledGPS.setTextColor(Color.RED);
            tvEnabledGPS.setText("GPS не доступен.");
        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            isNetEnable = true;
            tvEnabledNet.setTextColor(Color.GREEN);
            tvEnabledNet.setText("Интернет доступен.");
        }else{
            isNetEnable = false;
            tvEnabledNet.setTextColor(Color.RED);
            tvEnabledNet.setText("Интернет не доступен.");
        }
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

    @Override
    public void onClick(View view) {

        if (view == btnGetNearly) {
            mt = new MyTask();
            mt.execute();
        }
    }

    public class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String responseString = null;
            String lat = "";
            String lon = "";

            if(isGpsEnable){
                if(_latitudeGPS > 0 && _longtitudeGPS > 0){
                    lat = String.valueOf(_latitudeGPS);
                    lon = String.valueOf(_longtitudeGPS);
                }
            }
            if (lat == "" || lon == ""){
                lat = String.valueOf(_latitudeNet);
                lon = String.valueOf(_longtitudeNet);
            }

            Mediator.SetLatitude(lat);
            Mediator.SetLongtitude(lon);

            String myUrl = "http://www.geo.somee.com/api/Coord/GetNearlyAreas/" + lat + "/" + lon + "/";
            responseString = getJSON(myUrl, 10000000);

            return responseString;
        }


        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);


            //String str = "18:0979176312:Чернігівська:м. Ніжин:вул. Незалежності буд. 34 кв. 46#20:0673687411:Чернігівська:м. Ніжин:вул. Шевченка буд. 52#21:0669974095:Чернігівська:м. Ніжин#вул. Шевченка буд. 101 кв. 73#";

            String[] firstArr = str.split("#");
            String[] areaArr1 = firstArr[0].split(":");
            String[] areaArr2 = firstArr[1].split(":");
            String[] areaArr3 = firstArr[2].split(":");
            try {
                int id1  = Integer.parseInt(areaArr1[0].replaceFirst("\"",""));
                int id2  = Integer.parseInt(areaArr2[0]);
                int id3  = Integer.parseInt(areaArr3[0]);

                Area area1 = new Area(id1, areaArr1[1], areaArr1[2], areaArr1[3], areaArr1[4]);
                Area area2 = new Area(id2, areaArr2[1], areaArr2[2], areaArr2[3], areaArr2[4]);
                Area area3 = new Area(id3, areaArr3[1], areaArr3[2], areaArr3[3], areaArr3[4]);

                ArrayList<Area> areaList = new ArrayList<Area>();
                areaList.add(area1);
                areaList.add(area2);
                areaList.add(area3);

                Mediator.SetAreaList(areaList);
                TimeUnit.SECONDS.sleep(1);

            }catch(NumberFormatException nfe) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, nfe);
            }catch (Exception ex){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
            //JSONObject dataJsonObj = null;
            /*try {
                JSONObject areaList = new JSONObject(strJson);
                JSONArray areas = areaList.getJSONArray("areas");

                JSONObject area_1 = areas.getJSONObject(0);
                JSONObject area_2 = areas.getJSONObject(1);
                JSONObject area_3 = areas.getJSONObject(2);



            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            Intent intent = new Intent(MainActivity.this, GetNearlyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);

            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
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
}
