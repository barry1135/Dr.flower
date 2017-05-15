package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.*;
import nfu.csie.newdrflower.view.DataPreview;

/**
 * Created by barry on 2017/5/9.
 */

public class DataPreviewActivity extends Activity {

    int page = 1,max,res,now=0,id,order,t;
    private DataPreview Dataview;
    private DBPicShow getPic;
    private ArrayList<HashMap<String, Object>> user = new ArrayList<HashMap<String, Object>>();
    private DatabasesConnect DBConnect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dataview);
        Dataview = new DataPreview(this);
        //getPic = new DBPicShow();
        DBConnect = new DatabasesConnect();
        Log.d("text2","past");
        show();

    }

    private void show(){
        try {
            user = DBConnect.DBConnectPicReturn();
            DBConnect.wait();
            Dataview.setImageView(user);
        }
        catch (Exception e)
        {
            Log.e("log_tag", e.toString());
        }
    }



}
