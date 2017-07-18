package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextPaint;
import android.util.TypedValue;
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
    private ImageView pic;
    private TextView Name,ScientificName,EnglishName,OtherName,Kind,Feature;
    private Button back;

    public DataInfoView (Activity activity){
        this.activity = activity;

        initview();
        setlisten();
    }

    public void SetInfoView(ArrayList<HashMap<String, Object>> PicInfo,byte[] picdata){
        pic.setImageBitmap(set(picdata));
        Name.setText(PicInfo.get(0).get("name").toString());
        ScientificName.setText(PicInfo.get(0).get("scientificname").toString());
        EnglishName.setText(PicInfo.get(0).get("englishname").toString());
        OtherName.setText(PicInfo.get(0).get("othername").toString());
        Kind.setText(PicInfo.get(0).get("kind").toString());
        Feature.setText(PicInfo.get(0).get("feature").toString());

        adjustTvTextSize(Name,String.valueOf(Name.getText()));
        adjustTvTextSize(ScientificName,String.valueOf(ScientificName.getText()));
        adjustTvTextSize(EnglishName,String.valueOf(EnglishName.getText()));
        adjustTvTextSize(OtherName,String.valueOf(OtherName.getText()));
        adjustTvTextSize(Kind,String.valueOf(Kind.getText()));

    }

    private void setlisten() {
        back.setOnClickListener(Goback);
    }

    private void adjustTvTextSize(TextView tv, String text) {
        int maxWidth = tv.getWidth();
        int avaiWidth = maxWidth - tv.getPaddingLeft() - tv.getPaddingRight() - 10;
        if (avaiWidth <= 0) {
            return;
        }
        TextPaint textPaintClone = new TextPaint(tv.getPaint());
        float trySize = textPaintClone.getTextSize();
        while (textPaintClone.measureText(text) > avaiWidth) {
            trySize--;
            textPaintClone.setTextSize(trySize);
        }
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
    }

    private void initview() {
        pic = (ImageView) activity.findViewById(R.id.pic);
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
