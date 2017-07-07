package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.CameraActivity;
import nfu.csie.newdrflower.controller.SelectActivity;

/**
 * Created by barry on 2017/5/26.
 */

public class PicPreview {

    Activity activity;
    ImageView Pic;
    Button OKButton,BackButton;
    private DisplayMetrics metrics;
    int maxwidth,maxheight;
    private byte[] picdata;

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

}
