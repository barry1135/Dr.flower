package nfu.csie.newdrflower.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.PicPreviewActivuty;

/**
 * Created by barry on 2017/5/22.
 */

public class CameraView  {
    private Activity activity;
    private SurfaceView sfv;
    private ImageView filter,button;
    private TextView prompt;
    private boolean focus = false;
    private DisplayMetrics metrics;
    int maxwidth,maxheight;

    public CameraView (Activity activity){
        this.activity = activity;

        initview();

        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        maxwidth = metrics.widthPixels;
        maxheight = metrics.heightPixels;
    }

    private void initview() {
        sfv = (SurfaceView) activity.findViewById(R.id.surfaceView1);
        filter = (ImageView) activity.findViewById(R.id.imageView1);
        button = (ImageView) activity.findViewById(R.id.imageView2);
        prompt = (TextView) activity.findViewById(R.id.textView1);
    }


    public void change(byte[] pic){
        Intent it = new Intent(activity, PicPreviewActivuty.class);
        Bundle bData = new Bundle();
        bData.putByteArray("pic", pic);
        it.putExtras(bData);
        activity.startActivity(it);
        activity.finish();
    }



    public SurfaceView getSfv(){
        return sfv;
    }

    public int getMaxwidth(){
        return maxwidth;
    }

    public int getMaxheight() {
        return maxheight;
    }

    public ImageView getFilter(){
        return filter;
    }

    public ImageView getButton(){
        return button;
    }

}
