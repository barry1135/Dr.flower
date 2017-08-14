package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.DataInfoConnect;
import nfu.csie.newdrflower.view.DataInfoView;

/**
 * Created by barry on 2017/5/18.
 */

public class DataInfoActivity extends Activity {
    private DataInfoView DataInfoview;
    private DataInfoConnect DBInfoConnect;
    private Handler mUI_Handler = new Handler();
    private HandlerThread mThread;
    //繁重執行序用的 (時間超過3秒的)
    private android.os.Handler mThreadHandler;
    private Dialog dialog;
    private ArrayList<HashMap<String, Object>> user = new ArrayList<HashMap<String, Object>>();
    private int order;
    private byte[] picdata;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.flower_data_info);

        Bundle bData = this.getIntent().getExtras();
        order = bData.getInt("order");
        picdata = bData.getByteArray("Pic");

        DataInfoview = new DataInfoView(this);
        DBInfoConnect = new DataInfoConnect();

        mThread = new HandlerThread("flower_data_info");
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
                user = DBInfoConnect.DBInforeturn(order);
                mUI_Handler.post(new Runnable()
                {

                    @Override
                    public void run()
                    {

                        DataInfoview.SetInfoView(user,picdata);
                        dialog.dismiss();
                    }
                });
            }
        });




    }

    public void DialogWaitView(){
        dialog = ProgressDialog.show(DataInfoActivity.this,
                "讀取中", "請等待...", true);
    }
}
