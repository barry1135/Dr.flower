package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

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

        cameraview.getButton().setOnClickListener(press);

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

            cameraview.change(picdat);
        }

    };


    public void setFilter() {

        Bitmap bm;
        int width;
        int height;
        float scaleWidth;
        float scaleHeight;
        Matrix matrix;
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
        setFilter();
        focus = true;

        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(focus)
                mCamera.autoFocus(enableCamera.onCamAutoFocus);
        }
        return super.onTouchEvent(event);
    }

    private ImageView.OnClickListener press = new ImageView.OnClickListener(){
        public void onClick(View v){
            focus = false;
            mCamera.takePicture(camShutterCallback, camRawDataCallback, camJpegCallback);
        }
    };

}
