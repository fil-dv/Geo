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
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

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

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        btnGetNearly.setOnClickListener(this);
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
            isLatSet = true;
            tvLocationGpsLong.setText("Долгота: " + location.getLongitude());
            isLongSet = true;

        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            tvLocationNetLat.setText("Широта: " + location.getLatitude());
            isLatSet = true;
            tvLocationNetLong.setText("Долгота: " + location.getLongitude());
            isLongSet = true;
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
            Intent intent = new Intent(MainActivity.this, GetNearlyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
