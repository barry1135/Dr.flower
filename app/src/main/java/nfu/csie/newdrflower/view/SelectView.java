package nfu.csie.newdrflower.view;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.CameraActivity;
import nfu.csie.newdrflower.controller.DataInfoActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by barry on 2017/5/31.
 */

public class SelectView {
    private Activity activity;
    private ImageView picshow1,picshow2,picshow3;
    private Button retake;
    private int[] pic_id;
    private Bitmap[] pic_data;

    public SelectView(Activity activity){
        this.activity = activity;

        init();
        setlistener();
    }

    private void init(){
        picshow1 = (ImageView) activity.findViewById(R.id.imageView1);
        picshow2 = (ImageView) activity.findViewById(R.id.imageView2);
        picshow3 = (ImageView) activity.findViewById(R.id.imageView3);
        retake = (Button) activity.findViewById(R.id.button1);

        picshow1.setVisibility(View.GONE);
        picshow2.setVisibility(View.GONE);
        picshow3.setVisibility(View.GONE);
    }

    private void setlistener(){
        picshow1.setOnClickListener(pic1_listen);
        picshow2.setOnClickListener(pic2_listen);
        picshow3.setOnClickListener(pic3_listen);
        retake.setOnClickListener(back);
    }

    private Button.OnClickListener back = new Button.OnClickListener(){
        public void onClick(View v){
            Intent it = new Intent(activity, CameraActivity.class);
            activity.startActivity(it);
            activity.finish();
        }
    };

    private ImageView.OnClickListener pic1_listen = new ImageView.OnClickListener(){
        public void onClick(View v){
            Intent it = new Intent(activity, DataInfoActivity.class);
            it.putExtra("order",pic_id[0]-1);
            it.putExtra("Pic",Bitmap2Bytes(pic_data[0]));
            activity.startActivity(it);
            activity.finish();
        }
    };

    private  ImageView.OnClickListener pic2_listen = new ImageView.OnClickListener(){
        public void onClick(View v){
            Intent it = new Intent(activity, DataInfoActivity.class);
            it.putExtra("order",pic_id[1]-1);
            it.putExtra("Pic",Bitmap2Bytes(pic_data[1]));
            activity.startActivity(it);
            activity.finish();
        }
    };

    private ImageView.OnClickListener pic3_listen = new ImageView.OnClickListener(){
        public void onClick(View v){
            Intent it = new Intent(activity, DataInfoActivity.class);
            it.putExtra("order",pic_id[2]-1);
            it.putExtra("Pic",Bitmap2Bytes(pic_data[2]));
            activity.startActivity(it);
            activity.finish();
        }
    };

    public void setimg(ArrayList<HashMap<String, Object>> similar_pic){
        switch (similar_pic.size()){
            case 1:
                pic_id = new int[1];
                pic_data = new Bitmap[1];
                picshow1.setImageBitmap((Bitmap)similar_pic.get(0).get("pic"));
                picshow1.setVisibility(View.VISIBLE);
                pic_id [0]= Integer.parseInt(similar_pic.get(0).get("id").toString());
                pic_data[0] = (Bitmap)similar_pic.get(0).get("pic");
                break;
            case 2:
                pic_id = new int[2];
                pic_data = new Bitmap[2];
                picshow1.setImageBitmap((Bitmap)similar_pic.get(0).get("pic"));
                picshow1.setVisibility(View.VISIBLE);
                pic_id [0]= Integer.parseInt(similar_pic.get(0).get("id").toString());
                pic_data[0] = (Bitmap)similar_pic.get(0).get("pic");
                picshow2.setImageBitmap((Bitmap)similar_pic.get(1).get("pic"));
                picshow2.setVisibility(View.VISIBLE);
                pic_id [1]= Integer.parseInt(similar_pic.get(1).get("id").toString());
                pic_data[1] = (Bitmap)similar_pic.get(1).get("pic");
                break;
            case 3:
                pic_id = new int[3];
                pic_data = new Bitmap[3];
                picshow1.setImageBitmap((Bitmap)similar_pic.get(0).get("pic"));
                picshow1.setVisibility(View.VISIBLE);
                pic_id [0]= Integer.parseInt(similar_pic.get(0).get("id").toString());
                pic_data[0] = (Bitmap)similar_pic.get(0).get("pic");
                picshow2.setImageBitmap((Bitmap)similar_pic.get(1).get("pic"));
                picshow2.setVisibility(View.VISIBLE);
                pic_id [1]= Integer.parseInt(similar_pic.get(1).get("id").toString());
                pic_data[1] = (Bitmap)similar_pic.get(1).get("pic");
                picshow3.setImageBitmap((Bitmap)similar_pic.get(2).get("pic"));
                picshow3.setVisibility(View.VISIBLE);
                pic_id [2]= Integer.parseInt(similar_pic.get(2).get("id").toString());
                pic_data[2] = (Bitmap)similar_pic.get(2).get("pic");
                break;
        }
    }

    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
