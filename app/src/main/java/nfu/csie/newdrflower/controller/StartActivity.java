package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.DatabasesConnect;
import nfu.csie.newdrflower.view.StartView;


/**
 * Created by barry on 2017/5/8.
 */

public class StartActivity extends Activity {

    private StartView startview;
    private DatabasesConnect DBConnect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);

        startview = new StartView(this);

    }

    public void onReceive(Context context, Intent intent)
    {
        Intent mainIntent = new Intent(context,StartActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(mainIntent);
    }




}
