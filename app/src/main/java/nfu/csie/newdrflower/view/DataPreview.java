package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;

/**
 * Created by barry on 2017/5/9.
 */

public class DataPreview extends Activity {

    private ImageView NextBT,LastBT,BackMain;
    private ImageView[] IMGS = new ImageView[12];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dataview);

        setcon();
    }

    private void setcon(){
        IMGS[0] = (ImageView)findViewById(R.id.iv1);
        IMGS[1] = (ImageView)findViewById(R.id.iv2);
        IMGS[2] = (ImageView)findViewById(R.id.iv3);
        IMGS[3] = (ImageView)findViewById(R.id.iv4);
        IMGS[4] = (ImageView)findViewById(R.id.iv5);
        IMGS[5] = (ImageView)findViewById(R.id.iv6);
        IMGS[6] = (ImageView)findViewById(R.id.iv7);
        IMGS[7] = (ImageView)findViewById(R.id.iv8);
        IMGS[8] = (ImageView)findViewById(R.id.iv9);
        IMGS[9] = (ImageView)findViewById(R.id.iv10);
        IMGS[10] = (ImageView)findViewById(R.id.iv11);
        IMGS[11] = (ImageView)findViewById(R.id.iv12);
        NextBT = (ImageView)findViewById(R.id.NextBT);
        LastBT = (ImageView)findViewById(R.id.lastBT);
        BackMain = (ImageView)findViewById(R.id.BackMainPage);

    }
}
