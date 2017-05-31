package nfu.csie.newdrflower.view;

import nfu.csie.newdrflower.R;
import android.app.Activity;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by barry on 2017/5/31.
 */

public class SelectView {
    Activity activity;
    ImageView picshow1,picshow2,picshow3;
    Button retake;

    public SelectView(Activity activity){
        this.activity = activity;

        init();
    }

    private void init() {
        picshow1 = (ImageView) activity.findViewById(R.id.imageView1);
        picshow2 = (ImageView) activity.findViewById(R.id.imageView2);
        picshow3 = (ImageView) activity.findViewById(R.id.imageView3);
        retake = (Button) activity.findViewById(R.id.button1);
    }
}
