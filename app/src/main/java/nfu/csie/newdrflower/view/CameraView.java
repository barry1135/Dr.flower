package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.PicPreviewActivity;

/**
 * Created by barry on 2017/5/22.
 */

public class CameraView  {
    private Activity activity;
    private TextureView mTextureView;
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
        mTextureView = (TextureView) activity.findViewById(R.id.camera_texture_view);
        filter = (ImageView) activity.findViewById(R.id.imageView1);
        button = (ImageView) activity.findViewById(R.id.imageView2);
        prompt = (TextView) activity.findViewById(R.id.textView1);
    }


    public void change(byte[] pic){

    }



    public TextureView gettextureview(){
        return mTextureView;
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
