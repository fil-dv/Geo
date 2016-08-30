package com.example.dfilonenko.geo;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView tvEnabledGPS;
    TextView tvStatusGPS;
    TextView tvLocationGpsLat;
    TextView tvLocationGpsLong;
    TextView tvLocationGpsTime;
    TextView tvLocationNetLat;
    TextView tvLocationNetLong;
    TextView tvEnabledNet;
    //TextView tvStatusNet;
    TextView tvLocationNet;

    private LocationManager locationManager;
    StringBuilder sbGPS = new StringBuilder();
    StringBuilder sbNet = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvEnabledGPS = (TextView) findViewById(R.id.txt_GPS);
        //tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
        tvLocationGpsLat = (TextView) findViewById(R.id.LocationGpsLat);
        tvLocationGpsLong = (TextView) findViewById(R.id.LocationGpsLong);
        tvLocationGpsTime = (TextView) findViewById(R.id.LocationGpsTime);
        tvLocationNetLat = (TextView) findViewById(R.id.LocationNetLat);
        tvLocationNetLong = (TextView) findViewById(R.id.LocationNetLong);
        tvEnabledNet = (TextView) findViewById(R.id.txt_Net);
        //tvStatusNet = (TextView) findViewById(R.id.tvStatusNet);
        tvLocationNet = (TextView) findViewById(R.id.tvLocationNet);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        public void onStatusChanged(String provider, int status, Bundle extras) {
           /* if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                tvStatusNet.setText("Status: " + String.valueOf(status));
            }*/
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGpsLat.setText("Широта: " + location.getLatitude());
            tvLocationGpsLong.setText("Долгота: " + location.getLongitude());
            //tvLocationGpsTime.setText(String.format("%3$tT", new Date(location.getTime())));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            tvLocationNetLat.setText("Широта: " + location.getLatitude());
            tvLocationNetLong.setText("Долгота: " + location.getLongitude());
        }
    }

    private String formatLocation(Location location) {
        if (location == null) {
            return "";
        }
        else {
            return String.format("%3$tT", new Date(location.getTime()));
        }
    }

    private void checkEnabled() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            tvEnabledGPS.setTextColor(Color.GREEN);
            tvEnabledGPS.setText("GPS доступен.");
        }else{
            tvEnabledGPS.setTextColor(Color.RED);
            tvEnabledGPS.setText("GPS не доступен.");
        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            tvEnabledNet.setTextColor(Color.GREEN);
            tvEnabledNet.setText("Интернет доступен.");
        }else{
            tvEnabledNet.setTextColor(Color.RED);
            tvEnabledNet.setText("Интернет не доступен.");
        }
    }
    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

}


   /* private void checkEnabled() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            tvEnabledGPS.setTextColor(Color.GREEN);
            tvEnabledGPS.setText("GPS доступен.");
        }else{
            tvEnabledGPS.setTextColor(Color.RED);
            tvEnabledGPS.setText("GPS не доступен.");
        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            tvEnabledNet.setTextColor(Color.GREEN);
            tvEnabledNet.setText("Интернет доступен.");
        }else{
            tvEnabledGPS.setTextColor(Color.RED);
            tvEnabledGPS.setText("Интернет не доступен.");
        }
    }
       */