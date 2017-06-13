package nfu.csie.newdrflower.controller;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.PicComparison;
import nfu.csie.newdrflower.view.SelectView;

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

/**
 * Created by barry on 2017/5/31.
 */

public class SelectActivity extends Activity {
    private byte[] pic = null;
    private SelectView selectView;
    private PicComparison piccomparison;
    private Handler mUI_Handler = new Handler();
    private HandlerThread mThread;
    //繁重執行序用的 (時間超過3秒的)
    private android.os.Handler mThreadHandler;
    private Dialog dialog;
    private ArrayList<HashMap<String, Object>> similar＿pic = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pic_select);

        Bundle bData = this.getIntent().getExtras();
        pic = bData.getByteArray("pic");
        selectView = new SelectView(this);
        piccomparison = new PicComparison();

        mThread = new HandlerThread("flower_Select");
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
                similar＿pic = piccomparison.PicComparison(pic);
                mUI_Handler.post(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        selectView.setimg(similar＿pic);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void DialogWaitView(){
        dialog = ProgressDialog.show(SelectActivity.this,
                "比對中", "請等待...", true);

    }


}
