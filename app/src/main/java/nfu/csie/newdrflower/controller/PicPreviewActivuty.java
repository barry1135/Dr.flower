package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.SettingPic;
import nfu.csie.newdrflower.view.PicPreview;

/**
 * Created by barry on 2017/5/26.
 */

public class PicPreviewActivuty extends Activity {
    private byte[] pic=null;
    private PicPreview picpreview;
    private SettingPic settingpic = new SettingPic();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pic_preview);

        Bundle bData = this.getIntent().getExtras();
        pic = bData.getByteArray("pic");
        picpreview = new PicPreview(this);

        picpreview.setPic(settingpic.changepic(pic,picpreview.getMaxwidth(),picpreview.getMaxheight()));


    }
}
