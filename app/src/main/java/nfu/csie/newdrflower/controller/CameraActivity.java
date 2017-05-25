package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.EnableCamera;
import nfu.csie.newdrflower.view.CameraView;

/**
 * Created by barry on 2017/5/22.
 */

public class CameraActivity extends Activity {
    private CameraView cameraview;
    private EnableCamera enableCamera;
    private SurfaceView sfv;
    private byte[] picdat;
    private Camera mCamera;
    private boolean focus = false;

    private static final int MENU_1 = Menu.FIRST,
            MENU_2 = Menu.FIRST + 1,
            MENU_3 = Menu.FIRST + 2,
            MENU_4 = Menu.FIRST + 3;

    int select=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        setContentView(R.layout.camera);
        cameraview = new CameraView(this);

        sfv = cameraview.getSfv();

        enableCamera = new EnableCamera(this,sfv);



        picdat = null;



    }

    Camera.ShutterCallback camShutterCallback = new Camera.ShutterCallback(){
        public void onShutter(){

        }
    };

    Camera.PictureCallback camRawDataCallback = new Camera.PictureCallback(){
        public void onPictureTaken(byte[] data, Camera camera){

        }
    };

    Camera.PictureCallback camJpegCallback = new Camera.PictureCallback(){
        public void onPictureTaken(byte[] data, Camera camera){
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length,option);
            int iw = option.outWidth;
            int ih = option.outHeight;
            int scaleFactor = Math.min(iw/cameraview.getMaxwidth(), ih/cameraview.getMaxheight());
            option.inJustDecodeBounds = false;
            option.inSampleSize = scaleFactor;
            option.inPurgeable = true;
            bm = BitmapFactory.decodeByteArray(data, 0, data.length,option);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            bm = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, stream );
            picdat = stream.toByteArray();
        }

    };


    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);


        return super.onCreateOptionsMenu(menu);
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
            case R.id.MENU_1:
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
                width = bm.getWidth();
                height = bm.getHeight();
                scaleWidth = ((float) cameraview.getMaxwidth()) / width;
                scaleHeight = ((float) cameraview.getMaxheight()) / height;
                matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
                cameraview.getFilter().setImageBitmap(bm);
                cameraview.getFilter().setVisibility(View.VISIBLE);
                select = 1;
                break;
            case R.id.MENU_2:
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.a2);
                width = bm.getWidth();
                height = bm.getHeight();
                scaleWidth = ((float) cameraview.getMaxwidth()) / width;
                scaleHeight = ((float) cameraview.getMaxheight()) / height;
                matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
                cameraview.getFilter().setImageBitmap(bm);
                cameraview.getFilter().setVisibility(View.VISIBLE);
                select = 2;
                break;
            case R.id.MENU_3:
                select = 3;
                break;
            case R.id.MENU_4:
                select = 4;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        super.onPause();
    }

    protected void onResume() {


        mCamera = Camera.open();
        enableCamera.set(this, mCamera);
        cameraview.getFilter().setVisibility(View.INVISIBLE);

        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(false)
                mCamera.autoFocus(enableCamera.onCamAutoFocus);
        }
        return super.onTouchEvent(event);
    }



}
