package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Dialog;
import android.app.ProgressDialog;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.*;
import nfu.csie.newdrflower.view.DataPreview;

/**
 * Created by barry on 2017/5/9.
 */

public class DataPreviewActivity extends Activity {

    private DataPreview Dataview;
    private ArrayList<HashMap<String, Object>> user = new ArrayList<HashMap<String, Object>>();
    private DatabasesConnect DBConnect;
    private Handler mUI_Handler = new Handler();
    private HandlerThread mThread;
    //繁重執行序用的 (時間超過3秒的)
    private android.os.Handler mThreadHandler;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dataview);
        Dataview = new DataPreview(this);
        DBConnect = new DatabasesConnect();
        mThread = new HandlerThread("datapreview");
        mThread.start();
        DialogWaitView();
        show();

    }

    private void show() {

        mThreadHandler = new Handler(mThread.getLooper());
        mThreadHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                user = DBConnect.DBConnectPicReturn();
                mUI_Handler.post(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        Dataview.FirstsetImageView(user);

                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void DialogWaitView(){
        dialog = ProgressDialog.show(DataPreviewActivity.this,
                "讀取中", "請等待...", true);
    }



}
