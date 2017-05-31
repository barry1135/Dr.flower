package nfu.csie.newdrflower.controller;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.view.SelectView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

/**
 * Created by barry on 2017/5/31.
 */

public class SelectActivity extends Activity {
    private byte[] pic = null;
    private SelectView selectView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pic_select);

        Bundle bData = this.getIntent().getExtras();
        pic = bData.getByteArray("pic");
        selectView = new SelectView(this);

    }
}
