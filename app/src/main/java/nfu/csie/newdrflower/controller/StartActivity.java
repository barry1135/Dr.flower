package nfu.csie.newdrflower.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);

        startview = new StartView(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);


        return true;
    }


}
