package nfu.csie.newdrflower.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.CameraActivity;
import nfu.csie.newdrflower.controller.DataPreviewActivity;
import nfu.csie.newdrflower.controller.StartActivity;

/**
 * Created by barry on 2017/5/11.
 */

public class StartView {
    private Activity activity;
    private ImageView EnterButton,ExitButton,DataButton;

    public StartView (StartActivity activity){
        this.activity = activity;

        initview();
        setlisten();

    }


    private void initview() {
        EnterButton = (ImageView) activity.findViewById(R.id.enter);
        ExitButton = (ImageView) activity.findViewById(R.id.exit);
        DataButton = (ImageView) activity.findViewById(R.id.data);

    }

    private void setlisten() {
        EnterButton.setOnClickListener(EnterListener);
        ExitButton.setOnClickListener(ExitListener);
        DataButton.setOnClickListener(DataListener);
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
}
