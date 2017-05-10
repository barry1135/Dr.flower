package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.model.DatabaseShow;

/**
 * Created by barry on 2017/5/9.
 */

public class DataPreview extends Activity {

    private ImageView NextBT,LastBT,BackMain;
    private ImageView[] IMGS = new ImageView[12];
    int page = 1,max,res,now=0,id,order,t;
    private DatabaseShow DBShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dataview);

        setcon();
        setlisten();
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

    private void setlisten(){
        for(int i = 1;i < IMGS.length;i++)
        {
            IMGS[i].setOnClickListener(tuch);
            IMGS[i].setId(i);
        }
        NextBT.setOnClickListener(NextBTListener);
        LastBT.setOnClickListener(LastBTListener);
        BackMain.setOnClickListener(BackMainListener);
    }

    private ImageView.OnClickListener tuch = new ImageView.OnClickListener(){
        public void onClick(View v) {
            switch(v.getId())
            {
                case 0:
                    order = (page-1)*12+1;
                    break;
                case 1:
                    order = (page-1)*12+2;
                    break;
                case 2:
                    order = (page-1)*12+3;
                    break;
                case 3:
                    order = (page-1)*12+4;
                    break;
                case 4:
                    order = (page-1)*12+5;
                    break;
                case 5:
                    order = (page-1)*12+6;
                    break;
                case 6:
                    order = (page-1)*12+7;
                    break;
                case 7:
                    order = (page-1)*12+8;
                    break;
                case 8:
                    order = (page-1)*12+9;
                    break;
                case 9:
                    order = (page-1)*12+10;
                    break;
                case 10:
                    order = (page-1)*12+11;
                    break;
                case 11:
                    order = (page-1)*12+12;
                    break;
            }
        }
    };

    private  ImageView.OnClickListener NextBTListener = new ImageView.OnClickListener(){
        public void onClick(View v){

        }
    };

    private ImageView.OnClickListener LastBTListener = new ImageView.OnClickListener(){
        public void onClick(View v){

        }
    };

    private ImageView.OnClickListener BackMainListener = new ImageView.OnClickListener(){
        public void onClick(View v){

        }
    };

}
