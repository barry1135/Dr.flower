package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.DataPreviewActivity;

/**
 * Created by barry on 2017/5/11.
 */

public class DataPreview {

    private Activity activity;
    private ImageView NextBT,LastBT,BackMain;
    private ImageView[] IMGS = new ImageView[12];
    int page = 1,max,res,now=0,id,order,t;




    public DataPreview(DataPreviewActivity activity){
        this.activity = activity;
        initView();
        setlisten();
    }

    public void setImageView(ArrayList<Bitmap> pic){
        for(int i = 0;i < 12 ;i++,now++){
            IMGS[i].setImageBitmap(pic.get(now));
        }

    }

    private void setlisten() {
        for(int i = 1;i < IMGS.length;i++)
        {
            IMGS[i].setOnClickListener(tuch);
            //IMGS[i].setId(i);
        }
        NextBT.setOnClickListener(NextBTListener);
        LastBT.setOnClickListener(LastBTListener);
        BackMain.setOnClickListener(BackMainListener);
    }

    private void initView() {
        IMGS[0] = (ImageView) activity.findViewById(R.id.iv1);
        IMGS[1] = (ImageView) activity.findViewById(R.id.iv2);
        IMGS[2] = (ImageView) activity.findViewById(R.id.iv3);
        IMGS[3] = (ImageView) activity.findViewById(R.id.iv4);
        IMGS[4] = (ImageView) activity.findViewById(R.id.iv5);
        IMGS[5] = (ImageView) activity.findViewById(R.id.iv6);
        IMGS[6] = (ImageView) activity.findViewById(R.id.iv7);
        IMGS[7] = (ImageView) activity.findViewById(R.id.iv8);
        IMGS[8] = (ImageView) activity.findViewById(R.id.iv9);
        IMGS[9] = (ImageView) activity.findViewById(R.id.iv10);
        IMGS[10] = (ImageView) activity.findViewById(R.id.iv11);
        IMGS[11] = (ImageView) activity.findViewById(R.id.iv12);
        NextBT = (ImageView) activity.findViewById(R.id.NextBT);
        LastBT = (ImageView) activity.findViewById(R.id.lastBT);
        BackMain = (ImageView) activity.findViewById(R.id.BackMainPage);
    }

    private ImageView.OnClickListener tuch = new ImageView.OnClickListener(){
        public void onClick(View v) {
            switch (v.getId()) {
                case 0:
                    order = (page - 1) * 12 + 1;
                    backorder(order);
                    break;
                case 1:
                    order = (page - 1) * 12 + 2;
                    backorder(order);
                    break;
                case 2:
                    order = (page - 1) * 12 + 3;
                    backorder(order);
                    break;
                case 3:
                    order = (page - 1) * 12 + 4;
                    backorder(order);
                    break;
                case 4:
                    order = (page - 1) * 12 + 5;
                    backorder(order);
                    break;
                case 5:
                    order = (page - 1) * 12 + 6;
                    backorder(order);
                    break;
                case 6:
                    order = (page - 1) * 12 + 7;
                    backorder(order);
                    break;
                case 7:
                    order = (page - 1) * 12 + 8;
                    backorder(order);
                    break;
                case 8:
                    order = (page - 1) * 12 + 9;
                    backorder(order);
                    break;
                case 9:
                    order = (page - 1) * 12 + 10;
                    backorder(order);
                    break;
                case 10:
                    order = (page - 1) * 12 + 11;
                    backorder(order);
                    break;
                case 11:
                    order = (page - 1) * 12 + 12;
                    backorder(order);
                    break;
            }
        }
    };

    private int backorder(int order){
        this.order = order;
        return order;
    }

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
            activity.finish();
        }
    };
}
