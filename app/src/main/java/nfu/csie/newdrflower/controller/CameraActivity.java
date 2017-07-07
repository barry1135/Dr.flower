package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.EnableCamera;
import nfu.csie.newdrflower.view.CameraView;

/**
 * Created by barry on 2017/5/22.
 */

public class CameraActivity extends Activity{

    private CameraView cameraview;
    private EnableCamera enableCamera;
    private TextureView mTextureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.camera);

        cameraview = new CameraView(this);
        mTextureView = cameraview.gettextureview();
        enableCamera = new EnableCamera(mTextureView,CameraActivity.this);
        cameraview.getButton().setOnClickListener(press);


        Log.d("text2","onCreate");

    }

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


    protected void onResume() {

        setFilter();
        enableCamera.CheckTextureViewListener();

        super.onResume();
    }

    private ImageView.OnClickListener press = new ImageView.OnClickListener(){
        public void onClick(View v){
            enableCamera.takepic();
        }
    };

    @Override
    protected void onPause(){
        super.onPause();
        enableCamera.stopBackgroundThread();
    }
}
