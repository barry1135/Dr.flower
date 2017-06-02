package nfu.csie.newdrflower.view;

import nfu.csie.newdrflower.R;
import nfu.csie.newdrflower.controller.CameraActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

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

        }
    };

    private  ImageView.OnClickListener pic2_listen = new ImageView.OnClickListener(){
        public void onClick(View v){

        }
    };

    private ImageView.OnClickListener pic3_listen = new ImageView.OnClickListener(){
        public void onClick(View v){

        }
    };

    public void setimg(ArrayList<HashMap<String, Object>> similar_pic){
        switch (similar_pic.size()){
            case 1:
                picshow1.setImageBitmap((Bitmap)similar_pic.get(0).get("pic"));
                picshow1.setVisibility(View.VISIBLE);
                break;
            case 2:
                picshow1.setImageBitmap((Bitmap)similar_pic.get(0).get("pic"));
                picshow1.setVisibility(View.VISIBLE);
                picshow2.setImageBitmap((Bitmap)similar_pic.get(1).get("pic"));
                picshow2.setVisibility(View.VISIBLE);
                break;
            case 3:
                picshow1.setImageBitmap((Bitmap)similar_pic.get(0).get("pic"));
                picshow1.setVisibility(View.VISIBLE);
                picshow2.setImageBitmap((Bitmap)similar_pic.get(1).get("pic"));
                picshow2.setVisibility(View.VISIBLE);
                picshow3.setImageBitmap((Bitmap)similar_pic.get(2).get("pic"));
                picshow3.setVisibility(View.VISIBLE);
                break;
        }
    }
}
