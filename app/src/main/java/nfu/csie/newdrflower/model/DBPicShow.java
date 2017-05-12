package nfu.csie.newdrflower.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by barry on 2017/5/12.
 */

public class DBPicShow {

    private DatabasesConnect DBConnect;
    private ArrayList<HashMap<String, Object>> user = new ArrayList<HashMap<String, Object>>();
    private ArrayList<Bitmap> picdata;
    int page = 1,max,res,now=0,id,order,t;

    public void DBPicShow(){
        DBConnect = new DatabasesConnect();
        user = DBConnect.DBConnectPicReturn();
        max = user.size() / 12;
        res = user.size() % 12;
        Log.d("text","past");
    }

    public ArrayList<Bitmap> Picget(){
        for(int i=0; now<12; now++,i++)
        {

            picdata.add((Bitmap)(user.get(i).get("_Picture")));
        }
        return picdata;
    }
}
