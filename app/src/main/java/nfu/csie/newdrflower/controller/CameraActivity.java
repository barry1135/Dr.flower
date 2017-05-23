package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

    private ImageView.OnClickListener press = new ImageView.OnClickListener(){
        public void onClick(View v){

        }
    };

}
