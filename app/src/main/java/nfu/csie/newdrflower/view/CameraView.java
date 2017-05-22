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

    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        menu.add(0, MENU_1, 0, "濾鏡1");
        menu.add(0, MENU_2, 0, "濾鏡2");
        menu.add(0, MENU_3, 0, "濾鏡3");
        menu.add(0, MENU_4, 0, "濾鏡4");

        return activity.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        Bitmap bm;
        int width;
        int height;
        float scaleWidth;
        float scaleHeight;
        Matrix matrix;
        switch (item.getItemId()) {
            case MENU_1:
                bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.a1);
                width = bm.getWidth();
                height = bm.getHeight();
                scaleWidth = ((float) maxwidth) / width;
                scaleHeight = ((float) maxheight) / height;
                matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
                filter.setImageBitmap(bm);
                filter.setVisibility(View.VISIBLE);
                select = 1;
                break;
            case MENU_2:
                bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.a2);
                width = bm.getWidth();
                height = bm.getHeight();
                scaleWidth = ((float) maxwidth) / width;
                scaleHeight = ((float) maxwidth) / height;
                matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
                filter.setImageBitmap(bm);
                filter.setVisibility(View.VISIBLE);
                select = 2;
                break;
            case MENU_3:
                select = 3;
                break;
            case MENU_4:
                select = 4;
                break;
        }

        return activity.onOptionsItemSelected(item);
    }

    public SurfaceView getSfv(){
        return sfv;
    }

}
