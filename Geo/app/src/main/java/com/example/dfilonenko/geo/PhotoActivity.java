package com.example.dfilonenko.geo;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoActivity extends Activity {

    File directory;
    final int TYPE_PHOTO = 1;
    //final int TYPE_VIDEO = 2;

    final int REQUEST_CODE_PHOTO = 1;
    //final int REQUEST_CODE_VIDEO = 2;

    final String TAG = "myLogs";

    ImageView ivPhoto;
    TextView txtPhoto;
    int counter;

    SetStopTime sst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        createDirectory();
        //ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        txtPhoto = (TextView) findViewById(R.id.txtPhoto);
        counter = 0;
    }

    public void onClickPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    /*public void onClickVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_VIDEO));
        startActivityForResult(intent, REQUEST_CODE_VIDEO);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {

                counter++;
                txtPhoto.setText("      Добавлено " + counter + " фото.");

                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Photo uri: " + intent.getData());
                    /*Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d(TAG, "bitmap " + bitmap.getWidth() + " x " + bitmap.getHeight());
                            bitmap.setHeight(200);
                            bitmap.setWidth(150);
                            ivPhoto.setImageBitmap(bitmap);
                        }
                    }*/
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }

        /*if (requestCode == REQUEST_CODE_VIDEO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Video uri: " + intent.getData());
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }*/
    }

    private Uri generateFileUri(int type) {
        File file = null;
        switch (type) {
            case TYPE_PHOTO:
                file = new File(directory.getPath() + "/" + "geoPhoto_"
                        + System.currentTimeMillis() + ".jpg");
                break;
            /*case TYPE_VIDEO:
                file = new File(directory.getPath() + "/" + "video_"
                        + System.currentTimeMillis() + ".mp4");
                break;*/
        }
        Log.d(TAG, "fileName = " + file);
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "GeoPhoto");
        if (!directory.exists()) {

            directory.mkdirs();
        }
    }

    public void onClickFinish(View view) {
        sst = new SetStopTime();
        sst.execute();
    }

    public class SetStopTime extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String responseString = null;
            String pendingID = Mediator.GetPending();

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("www.geo.somee.com")
                    .appendPath("api")
                    .appendPath("coord")
                    .appendPath("SetTime")
                    .appendQueryParameter("id", pendingID + "k");

            String myUrl = builder.build().toString();
            responseString = getResponse(myUrl, 10000000);

            return responseString;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                Toast.makeText(PhotoActivity.this, "Данные успешно внесены в базу.", Toast.LENGTH_LONG).show();
                //TimeUnit.SECONDS.sleep(1);
                Intent intent = new Intent(PhotoActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }catch (Exception ex){}
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
                            sb.append(line);
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
}