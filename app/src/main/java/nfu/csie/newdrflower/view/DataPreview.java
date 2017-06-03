package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.HashMap;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.DataInfoActivity;
import nfu.csie.newdrflower.controller.DataPreviewActivity;

/**
 * Created by barry on 2017/5/11.
 */

public class DataPreview{

    private Activity activity;
    private ImageView NextBT,LastBT,BackMain;
    private ImageView[] IMGS = new ImageView[12];
    int page = 1,max,res,now=0,id,order,t;
    ArrayList<HashMap<String, Object>> PicData = new ArrayList<HashMap<String, Object>>();




    public DataPreview(Activity activity){
        this.activity = activity;
        initView();
        setlisten();
        BackMain.setVisibility(View.INVISIBLE);
        LastBT.setVisibility(View.INVISIBLE);
        NextBT.setVisibility(View.INVISIBLE);
    }

    public void FirstsetImageView(ArrayList<HashMap<String, Object>> pic){
        for(int i = 0;i < 12 ;i++,now++){
            Bitmap PicSize = SetPic((Bitmap) pic.get(now).get("picture"));
            IMGS[i].setImageBitmap(PicSize);
            IMGS[i].setVisibility(View.VISIBLE);
            max = pic.size() / 12;
            res = pic.size() % 12;
        }
        PicData = pic;
        BackMain.setVisibility(View.VISIBLE);
        NextBT.setVisibility(View.VISIBLE);
    }

    private  void setImageView(){
        setviewVisible();
        if(page <= max)
        {
            for(int i=0; now<12*page; now++,i++)
            {
                Bitmap PicSize = SetPic((Bitmap) PicData.get(now).get("picture"));
                IMGS[i].setImageBitmap(PicSize);
                IMGS[i].setVisibility(View.VISIBLE);
            }
        }
        else
        {
            for(int i=0;now < ((page-1)*12+res);now++,i++)
            {
                Bitmap PicSize = SetPic((Bitmap) PicData.get(now).get("picture"));
                IMGS[i].setImageBitmap(PicSize);
                IMGS[i].setVisibility(View.VISIBLE);
            }
            now = page*12;
        }
    }

    private void setviewVisible(){
        for(int i =0 ; i<12;i++)
        {
            IMGS[i].setVisibility(View.INVISIBLE);

        }
    }

    private void setlisten() {
        for(int i = 0;i < IMGS.length;i++)
        {
            IMGS[i].setOnClickListener(tuch);
            IMGS[i].setId(i);
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
                    order = (page - 1) * 12;
                    break;
                case 1:
                    order = (page - 1) * 12 + 1;
                    break;
                case 2:
                    order = (page - 1) * 12 + 2;
                    break;
                case 3:
                    order = (page - 1) * 12 + 3;
                    break;
                case 4:
                    order = (page - 1) * 12 + 4;
                    break;
                case 5:
                    order = (page - 1) * 12 + 5;
                    break;
                case 6:
                    order = (page - 1) * 12 + 6;
                    break;
                case 7:
                    order = (page - 1) * 12 + 7;
                    break;
                case 8:
                    order = (page - 1) * 12 + 8;
                    break;
                case 9:
                    order = (page - 1) * 12 + 9;
                    break;
                case 10:
                    order = (page - 1) * 12 + 10;
                    break;
                case 11:
                    order = (page - 1) * 12 + 11;
                    break;
            }
            Intent it = new Intent(activity,DataInfoActivity.class);
            it.putExtra("order",order);
            it.putExtra("Pic",Bitmap2Bytes((Bitmap) PicData.get(order).get("picture")));
            activity.startActivity(it);
        }
    };


    private  ImageView.OnClickListener NextBTListener = new ImageView.OnClickListener(){
        public void onClick(View v){
            if(page <= max){
                if(page == max) {
                    NextBT.setVisibility(View.INVISIBLE);
                    LastBT.setVisibility(View.VISIBLE);
                }
                page++;
                setImageView();
            }
        }
    };

    private ImageView.OnClickListener LastBTListener = new ImageView.OnClickListener(){
        public void onClick(View v){
            if(page > 1){
                now = now - 24;
                page--;
                setImageView();
                if(page == 1) {
                    LastBT.setVisibility(View.INVISIBLE);
                    NextBT.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private ImageView.OnClickListener BackMainListener = new ImageView.OnClickListener(){
        public void onClick(View v){
            activity.finish();
        }
    };

    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private Bitmap SetPic(Bitmap a){
        int width = a.getWidth();
        int height = a.getHeight();
        int newwidth = IMGS[0].getWidth();
        int newheight = IMGS[0].getHeight();
        float scaleWidth = ((float) newwidth) / width;
        float scaleHeight = ((float) newheight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap a1 = Bitmap.createBitmap(a, 0, 0, width, height, matrix, true);
        return a1;
    }
}


