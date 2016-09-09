package com.example.dfilonenko.geo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by D.Filonenko on 09.09.2016.
 */
public class SetStartTime extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
        String responseString = null;
        int index = Mediator.GetSelectedArea();
        Area area = Mediator.GetArea(index);
        String idStr = String.valueOf(area.GetAreaID());

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("www.geo.somee.com")
                .appendPath("api")
                .appendPath("coord")
                .appendPath("SetTime")
                .appendQueryParameter("id", idStr + "n");

        String myUrl = builder.build().toString();
        responseString = getResponse(myUrl, 10000000);

        return responseString;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Mediator.SetPending(result);

        //Intent intent = new Intent(this, PhotoActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
    }

    public String getResponse(String url, int timeout) {
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
