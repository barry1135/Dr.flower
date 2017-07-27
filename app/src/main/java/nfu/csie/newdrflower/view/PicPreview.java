package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.CameraActivity;
import nfu.csie.newdrflower.controller.SelectActivity;
import nfu.csie.newdrflower.model.CoordinateDataBases;

/**
 * Created by barry on 2017/5/26.
 */

public class PicPreview {

    private Activity activity;
    private ImageView Pic;
    private Button OKButton,BackButton;
    private DisplayMetrics metrics;
    int maxwidth,maxheight;
    private byte[] picdata;
    private double latitude,longitude;
    private CoordinateDataBases SqliteDB;
    private String Base64Pic;


    public PicPreview(Activity activity){
        this.activity = activity;
        init();
        setlisten();

        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        maxwidth = metrics.widthPixels;
        maxheight = metrics.heightPixels;


    }

    public int getMaxwidth(){
        return maxwidth;
    }

    public int getMaxheight() {
        return maxheight;
    }

    public void setPic(Bitmap pic){
        Pic.setImageBitmap(pic);
    }

    public void setPicByte(byte[] picdata){
        this.picdata = picdata;
    }

    private void setlisten() {
        OKButton.setOnClickListener(OK);
        BackButton.setOnClickListener(Back);
    }

    private void init() {
        Pic = (ImageView) activity.findViewById(R.id.imageView1);
        OKButton = (Button) activity.findViewById(R.id.button1);
        BackButton = (Button) activity.findViewById(R.id.button2);


    }

    private Button.OnClickListener OK = new Button.OnClickListener(){
        public void onClick (View v){
            SqliteDB = new CoordinateDataBases(activity);
            SQLiteDatabase DB = SqliteDB.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("_Picture",Base64Pic);
            values.put("_Latitude",latitude);
            values.put("Longitude",longitude);
            DB.insert("FlowerCoordinate", null, values);
            SqliteDB.close();

            Intent it = new Intent(activity,SelectActivity.class);
            Bundle bData = new Bundle();
            bData.putByteArray("pic",picdata);
            it.putExtras(bData);
            activity.startActivity(it);
            activity.finish();
        }
    };

    private Button.OnClickListener Back = new Button.OnClickListener(){
        public void onClick (View v){
            Intent it = new Intent(activity, CameraActivity.class);
            activity.startActivity(it);
            activity.finish();
        }
    };

    public void setLocation(Double latitude,Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setBase64Pic(String textpic){
        Base64Pic = textpic;
    }

}
