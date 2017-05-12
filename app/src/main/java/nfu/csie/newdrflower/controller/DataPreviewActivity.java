package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.DBPicShow;
import nfu.csie.newdrflower.view.DataPreview;

/**
 * Created by barry on 2017/5/9.
 */

public class DataPreviewActivity extends Activity {

    int page = 1,max,res,now=0,id,order,t;
    private DataPreview Dataview;
    private DBPicShow getPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dataview);

        Dataview = new DataPreview(this);
        getPic = new DBPicShow();

        show();

    }

    private void show(){
        Dataview.setImageView(getPic.Picget());
    }



}
