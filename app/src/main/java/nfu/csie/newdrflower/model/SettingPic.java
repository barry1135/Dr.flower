package nfu.csie.newdrflower.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;

/**
 * Created by barry on 2017/5/26.
 */

public class SettingPic {

    private byte[] picdat;
    private DisplayMetrics metrics;

    public Bitmap changepic(byte[] pic,int maxwidth,int maxheight){
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeByteArray(pic, 0, pic.length,option);
        int iw = option.outWidth;
        int ih = option.outHeight;
        int scaleFactor = Math.min(iw/maxwidth, ih/maxheight);
        option.inJustDecodeBounds = false;
        option.inSampleSize = scaleFactor;
        option.inPurgeable = true;
        bm = BitmapFactory.decodeByteArray(pic, 0, pic.length,option);
        Matrix matrix = new Matrix();
        matrix.setRotate(0);
        bm = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) maxwidth) / width;
        float scaleHeight = ((float) maxheight) / height;
        matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);

        int sx,sy,ex,ey;
        sy = (int) (bm.getWidth()*0.145);
        sx = (int) (bm.getHeight()*0.15);
        ey = (int) (bm.getWidth()*0.755);
        ex = (int) (bm.getHeight()*0.55);
        bm.getHeight();
        bm.getWidth();
        matrix = new Matrix();
        Bitmap m = Bitmap.createBitmap(bm,sy,sx,ey,ex,matrix,true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        m.compress(Bitmap.CompressFormat.JPEG, 80, stream );
        picdat = stream.toByteArray();

        width = m.getWidth();
        height = m.getHeight();
        int newwidth = 300;
        int newheight = 300;
        scaleWidth = ((float) newwidth) / width;
        scaleHeight = ((float) newheight) / height;
        matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap a1 = Bitmap.createBitmap(m, 0, 0, width, height, matrix,true);

        return a1;

    }

    public byte[] Bitmap2Byte(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();

    }
}
