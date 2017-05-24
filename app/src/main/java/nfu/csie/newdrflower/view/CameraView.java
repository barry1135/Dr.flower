package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import nfu.csie.newdrflower.R;

/**
 * Created by barry on 2017/5/22.
 */

public class CameraView  {
    private Activity activity;
    private SurfaceView sfv;
    private ImageView filter,button;
    private TextView prompt;
    private boolean focus = false;
    private static final int MENU_1 = Menu.FIRST,
            MENU_2 = Menu.FIRST + 1,
            MENU_3 = Menu.FIRST + 2,
            MENU_4 = Menu.FIRST + 3;
    int select=0;
    private DisplayMetrics metrics;
    int maxwidth,maxheight;

    public CameraView (Activity activity){
        this.activity = activity;

        initview();
        setlisten();

        filter.setVisibility(View.GONE);
        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        maxwidth = metrics.widthPixels;
        maxheight = metrics.heightPixels;
    }

    private void setlisten() {
        button.setOnClickListener(press);
    }

    private void initview() {
        sfv = (SurfaceView) activity.findViewById(R.id.surfaceView1);
        filter = (ImageView) activity.findViewById(R.id.imageView1);
        button = (ImageView) activity.findViewById(R.id.imageView2);
        prompt = (TextView) activity.findViewById(R.id.textView1);
    }

    private ImageView.OnClickListener press = new ImageView.OnClickListener(){
        public void onClick(View v){

        }
    };



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
}
