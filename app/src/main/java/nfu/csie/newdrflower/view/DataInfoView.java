package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nfu.csie.newdrflower.R;

/**
 * Created by barry on 2017/5/18.
 */

public class DataInfoView {
    private Activity activity;
    private ImageView iv;
    private TextView Name,ScientificName,EnglishName,OtherName,Kind,Feature;
    private Button back;

    public DataInfoView (Activity activity){
        this.activity = activity;

        initview();
        setlisten();
    }

    public void SetInfoView(ArrayList<HashMap<String, Object>> PicInfo,byte[] picdata){
        iv.setImageBitmap(set(picdata));
        Name.setText(PicInfo.get(0).get("name").toString());
        ScientificName.setText(PicInfo.get(0).get("scientificname").toString());
        EnglishName.setText(PicInfo.get(0).get("englishname").toString());
        OtherName.setText(PicInfo.get(0).get("othername").toString());
        Kind.setText(PicInfo.get(0).get("kind").toString());
        Feature.setText(PicInfo.get(0).get("feature").toString());

    }

    private void setlisten() {
        back.setOnClickListener(Goback);
    }

    private void initview() {
        iv = (ImageView) activity.findViewById(R.id.pic);
        Name = (TextView) activity.findViewById(R.id.name);
        ScientificName = (TextView) activity.findViewById(R.id.scientificName);
        EnglishName = (TextView) activity.findViewById(R.id.englishname);
        OtherName = (TextView) activity.findViewById(R.id.othername);
        Kind = (TextView) activity.findViewById(R.id.kind);
        Feature = (TextView) activity.findViewById(R.id.feature);
        back = (Button) activity.findViewById(R.id.back);

    }

    private Button.OnClickListener Goback = new Button.OnClickListener(){
        public void onClick(View v){
            activity.finish();
        }
    };

    private Bitmap set(byte[] bytes){

        Bitmap a = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        int width = a.getWidth();
        int height = a.getHeight();
        int newwidth = 100;
        int newheight = 100;
        float scaleWidth = ((float) newwidth) / width;
        float scaleHeight = ((float) newheight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap a1 = Bitmap.createBitmap(a, 0, 0, width, height, matrix,true);
        return a1;
    }
}
