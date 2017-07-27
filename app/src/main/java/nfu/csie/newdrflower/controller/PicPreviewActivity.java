package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.Window;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.SettingPic;
import nfu.csie.newdrflower.view.PicPreview;

/**
 * Created by barry on 2017/5/26.
 */

public class PicPreviewActivity extends Activity {
    private byte[] pic=null;
    private PicPreview picpreview;
    private SettingPic settingpic = new SettingPic();
    private double latitude,longitude;
    private String base64Pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pic_preview);

        Bundle bData = this.getIntent().getExtras();
        pic = bData.getByteArray("pic");
        latitude = bData.getDouble("longitude");
        longitude = bData.getDouble("longitude");

        base64Pic = Base64.encodeToString(pic,Base64.DEFAULT);


        picpreview = new PicPreview(this);

        picpreview.setLocation(latitude,longitude);

        picpreview.setBase64Pic(base64Pic);

        picpreview.setPic(settingpic.changepic(pic,picpreview.getMaxwidth(),picpreview.getMaxheight()));

        picpreview.setPicByte(settingpic.Bitmap2Byte(settingpic.changepic(pic,picpreview.getMaxwidth(),picpreview.getMaxheight())));


    }
}
