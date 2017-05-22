package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.Window;

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
        setContentView(R.layout.camera);
        cameraview = new CameraView(this);

        sfv = cameraview.getSfv();

        enableCamera = new EnableCamera(this,sfv);



        picdat = null;

    }
}
