package nfu.csie.newdrflower.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.EnableCamera;
import nfu.csie.newdrflower.view.CameraView;

/**
 * Created by barry on 2017/5/22.
 */

public class CameraActivity extends Activity{

    private final int PERMISSION_WRITE_STORAGE = 0;

    private CameraView cameraview;
    private EnableCamera enableCamera;
    private TextureView mTextureView;
    private byte[] picdat;
    private Camera mCamera;
    private boolean focus = false;


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



        cameraview.getButton().setOnClickListener(press);

        picdat = null;



    }

    public static void getPermission(Activity activity, String permission, int permissionCode) {

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionCode);
        }
    }

    /*Camera.ShutterCallback camShutterCallback = new Camera.ShutterCallback(){
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

    };*/


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

    /*protected void onPause() {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        super.onPause();
    }*/

    protected void onResume() {

        setFilter();


        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            enableCamera = new EnableCamera(mTextureView,this);
        }
        else{
            showDialog("請提供授權", "聽音樂需要存放檔案的權限才能順利播放",
                    "重新提供", getPermissionListener, "不要提供", closeActivityListener);
        }
    }
//@Override
    /*public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(focus)
                mCamera.autoFocus(enableCamera.onCamAutoFocus);
        }
        return super.onTouchEvent(event);
    }*/

    private ImageView.OnClickListener press = new ImageView.OnClickListener(){
        public void onClick(View v){
            enableCamera.takepic();
        }
    };

    private void sendpicdata(){
        cameraview.change(enableCamera.getPicdata());
    }

    public  void showDialog(String title, String message,
                                  String confirm1, DialogInterface.OnClickListener listener1,
                                  String confirm2, DialogInterface.OnClickListener listener2) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title).setMessage(message)
                .setPositiveButton(confirm1, listener1)
                .setNegativeButton(confirm2, listener2)
                .show();
    }

    private DialogInterface.OnClickListener getPermissionListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //取得檔案寫入的權限
                    getPermission(CameraActivity.this,
                            Manifest.permission.CAMERA,
                            PERMISSION_WRITE_STORAGE);
                    enableCamera = new EnableCamera(mTextureView,CameraActivity.this);
                }
            };

    private DialogInterface.OnClickListener closeActivityListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(CameraActivity.this, "無法播放音樂", Toast.LENGTH_SHORT).show();
                }
            };

}
