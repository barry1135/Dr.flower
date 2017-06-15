package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.CameraActivity;
import nfu.csie.newdrflower.controller.DataPreviewActivity;
import nfu.csie.newdrflower.controller.GoogleMapActivity;
import nfu.csie.newdrflower.model.CoordinateDataBases;

/**
 * Created by barry on 2017/5/11.
 */

public class StartView {
    private Activity activity;
    private ImageView EnterButton,ExitButton,DataButton,GoogleMap;
    private CoordinateDataBases DH;

    public StartView (Activity activity){
        this.activity = activity;

        DH = new CoordinateDataBases(activity);
        SQLiteDatabase db = DH.getWritableDatabase();

        initview();
        setlisten();

    }


    private void initview() {
        EnterButton = (ImageView) activity.findViewById(R.id.enter);
        ExitButton = (ImageView) activity.findViewById(R.id.exit);
        DataButton = (ImageView) activity.findViewById(R.id.data);
        GoogleMap = (ImageView) activity.findViewById(R.id.googlemap);

    }

    private void setlisten() {
        EnterButton.setOnClickListener(EnterListener);
        ExitButton.setOnClickListener(ExitListener);
        DataButton.setOnClickListener(DataListener);
        GoogleMap.setOnClickListener(MapListener);
    }

    private ImageView.OnClickListener EnterListener = new ImageView.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent it = new Intent(activity, CameraActivity.class);
            activity.startActivity(it);
        }
    };

    private ImageView.OnClickListener ExitListener = new ImageView.OnClickListener(){
        public void onClick(View v){
            activity.finish();
        }
    };

    private ImageView.OnClickListener DataListener = new ImageView.OnClickListener(){
        public void onClick(View v){
            Intent it = new Intent(activity, DataPreviewActivity.class);
            activity.startActivity(it);
        }


    };

    private ImageView.OnClickListener MapListener = new ImageView.OnClickListener(){
      public void onClick(View v){
            Intent it = new Intent(activity, GoogleMapActivity.class);
            activity.startActivity(it);
      }
    };
}
