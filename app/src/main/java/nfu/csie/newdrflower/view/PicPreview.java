package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.media.Image;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;

/**
 * Created by barry on 2017/5/26.
 */

public class PicPreview {

    Activity activity;
    ImageView Pic;
    Button OKButton,BackButton;
    private DisplayMetrics metrics;
    int maxwidth,maxheight;

    public PicPreview(Activity activity,byte[] pic){
        this.activity = activity;
        init();
        setlisten();
        setPic(pic);

        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        maxwidth = metrics.widthPixels;
        maxheight = metrics.heightPixels;
    }

    private void setPic(byte[] pic){

    }

    private void setlisten() {
        OKButton.setOnClickListener(OK);
        BackButton.setOnClickListener(Back);
    }

    private void init() {
        Pic = (ImageView) activity.findViewById(R.id.imageView1);
        OKButton = (Button) activity.findViewById(R.id.button1);
        BackButton = (Button) activity.findViewById(R.id.button2);
    }

    private Button.OnClickListener OK = new Button.OnClickListener(){
        public void onClick (View v){

        }
    };

    private Button.OnClickListener Back = new Button.OnClickListener(){
        public void onClick (View v){

        }
    };

}
