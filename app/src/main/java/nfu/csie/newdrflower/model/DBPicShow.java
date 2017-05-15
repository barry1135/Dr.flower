package nfu.csie.newdrflower.model;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by barry on 2017/5/12.
 */

public class DBPicShow  {

    private DatabasesConnect DBConnect;
    private ArrayList<HashMap<String, Object>> user = new ArrayList<HashMap<String, Object>>();
    private ArrayList<Bitmap> picdata;
    int page = 1,max,res,now=0,id,order,t;

    public DBPicShow(){
        DBConnect = new DatabasesConnect();
        Log.d("text2","dbpicshow");
    }

    public ArrayList<HashMap<String, Object>> Picget(){
        user = DBConnect.DBConnectPicReturn();
        max = user.size() / 12;
        res = user.size() % 12;
        return user;
    }
}
