package nfu.csie.newdrflower.model;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by barry on 2017/5/31.
 */

public class PicComparison {
    private ArrayList<HashMap<String, Object>> similar_pic = new ArrayList<HashMap<String, Object>>();
    private ArrayList<HashMap<String, Object>> db_data = new ArrayList<HashMap<String, Object>>();
    private byte[] dat=null;
    private Bitmap bitpic;
    int mBitmapH = 0;
    int mBitmapW = 0;
    int mArrayColor[] = null;
    int mArrayColorLengh = 0;
    int mArrayR1[] = null;
    int mArrayG1[] = null;
    int mArrayB1[] = null;
    int mArrayA1[] = null;
    int CColor[] = null;
    int pic_PRdGT;
    int pic_PRdBT;
    int pic_PGdBT;
    int pic_PRdGR;
    int pic_PRdBR;
    int pic_PGdBR;
    int pic_PRdGC;
    int pic_PRdBC;
    int pic_PGdBC;
    int pic_PRdGL;
    int pic_PRdBL;
    int pic_PGdBL;
    int pic_PRdGB;
    int pic_PRdBB;
    int pic_PGdBB;
    String lnid;
    String lnorder;
    String pic_color;
    ArrayList<Integer> ratio = new ArrayList<Integer>();
    ArrayList<Integer> id = new ArrayList<Integer>();
    ArrayList<Integer> Order = new ArrayList<Integer>();
    ArrayList<Integer> temp = new ArrayList<Integer>();



    public ArrayList<HashMap<String,Object>> PicComparison(byte[] pic){

        String flowerdatajsonString = PostflowerData("society");
        flowerDataListView(flowerdatajsonString);
        bitpic = BitmapFactory.decodeByteArray(pic, 0, pic.length);
        bitpic = set(bitpic);
        pic_analysis(bitpic);
        search(pic_color);


        return similar_pic;
    }
    //ArrayList<HashMap<String, Object>>

    public String PostflowerData(String query)
    {
        String result = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://172.20.10.2/flowerData.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("category", query));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse = httpClient.execute(post);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = bufReader.readLine()) != null)
            {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
        }
        catch (Exception e)
        {
            Log.e("log_tag", e.toString());
        }
        return result;
    }

    private void flowerDataListView(String input)
    {
	/*
	 * SQL 結果有多筆資料時使用JSONArray
	 * 只有一筆資料時直接建立JSONObject物件
	 * JSONObject jsonData = new JSONObject(result);
	 */
        try
        {
            JSONArray jsonArray = new JSONArray(input);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                HashMap<String, Object> h2 = new HashMap<String, Object>();
                h2.put("id", jsonData.getString("_id"));
                h2.put("order", jsonData.getString("_Order"));
                h2.put("picture",(jsonData.getString("_Picture")));
                h2.put("prdgt", jsonData.getString("_PRdGT"));
                h2.put("prdbt", jsonData.getString("_PRdBT"));
                h2.put("pgdbt", jsonData.getString("_PGdBT"));
                h2.put("prdgr", jsonData.getString("_PRdGR"));
                h2.put("prdbr", jsonData.getString("_PRdBR"));
                h2.put("pgdbr", jsonData.getString("_PGdBR"));
                h2.put("prdgc", jsonData.getString("_PRdGC"));
                h2.put("prdbc", jsonData.getString("_PRdBC"));
                h2.put("pgdbc", jsonData.getString("_PGdBC"));
                h2.put("prdgl", jsonData.getString("_PRdGL"));
                h2.put("prdbl", jsonData.getString("_PRdBL"));
                h2.put("pgdbl", jsonData.getString("_PGdBL"));
                h2.put("prdgb", jsonData.getString("_PRdGB"));
                h2.put("prdbb", jsonData.getString("_PRdBB"));
                h2.put("pgdbb", jsonData.getString("_PGdBB"));
                h2.put("part", jsonData.getString("_Part"));

                db_data.add(h2);

            }

        }
        catch (JSONException e)
        {
            // TODO 自動產生的 catch 區塊
            e.printStackTrace();
        }

    }

    private Bitmap set(Bitmap a){
        int width = a.getWidth();
        int height = a.getHeight();
        int newwidth = 300;
        int newheight = 300;
        float scaleWidth = ((float) newwidth) / width;
        float scaleHeight = ((float) newheight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap a1 = Bitmap.createBitmap(a, 0, 0, width, height, matrix,true);
        return a1;
    }

    public void pic_analysis(Bitmap A1)
    {

        Bitmap NewImg=null;
        NewImg = A1.copy(Bitmap.Config.ARGB_8888, true);
        //���Ϥ����e
        mBitmapH = A1.getHeight();
        mBitmapW = A1.getWidth();
        mArrayColorLengh = mBitmapW * mBitmapH;
        mArrayColor = new int[mArrayColorLengh];
        mArrayR1 = new int[mArrayColorLengh];
        mArrayG1 = new int[mArrayColorLengh];
        mArrayB1 = new int[mArrayColorLengh];
        mArrayA1 = new int[mArrayColorLengh];
        CColor =new int[9];
        int[] CColorR = new int[9];
        int[] CColorG = new int[9];
        int[] CColorB = new int[9];
        int[] CColorA = new int[9];
        int count=0;
        for(int i = 0;i < mBitmapH;i++)
        {
            for(int j=0;j < mBitmapW;j++)
            {
                int color = A1.getPixel(j, i);
                //�N�Ϥ��C��s�JmArrayColor��
                mArrayColor[count] = color;
                //�N�����I���ά�RGB�T����
                mArrayA1[count] = Color.alpha(color);
                mArrayR1[count] = Color.red(color);
                mArrayG1[count] = Color.green(color);
                mArrayB1[count] = Color.blue(color);
                count++;
            }
        }
        int AvgColor=0;
        int AvgA=0;
        int AvgR=0;
        int AvgG=0;
        int AvgB=0;
        int x=0;
        int ConstantA=mBitmapH/3;
        int ConstantB=mBitmapH;
        for(int set = 0;set < 9;set++)
        {
            for(int j=set/3*ConstantA;j<set/3*ConstantA+ConstantA;j++)
            {
                x=set%3*ConstantA;
                for(int i=x+j*ConstantB;i<x+j*ConstantB+ConstantA;i++)
                {
                    AvgColor+=mArrayColor[i];
                    AvgA+=mArrayA1[i];
                    AvgR+=mArrayR1[i];
                    AvgG+=mArrayG1[i];
                    AvgB+=mArrayB1[i];
                }
            }
            CColor[set]=AvgColor/ConstantA/ConstantA;
            CColorA[set]=AvgA/ConstantA/ConstantA;
            CColorR[set]=AvgR/ConstantA/ConstantA;
            CColorG[set]=AvgG/ConstantA/ConstantA;
            CColorB[set]=AvgB/ConstantA/ConstantA;
            AvgColor=0;
            AvgA=0;
            AvgR=0;
            AvgG=0;
            AvgB=0;
        }
        int load=0;
        for(int set = 0;set <9;set++)
        {
            if(set==3)
                load+=ConstantA;
            if(set==6)
                load+=ConstantA;
            for(int i = set%3*ConstantA;i <set%3*ConstantA+ConstantA;i++)
            {
                for(int j = load;j <load+ConstantA;j++)
                {
                    if(set!=0 && set!=2 && set!=6 && set!=8)
                        NewImg.setPixel(i, j,Color.argb(CColorA[set], CColorR[set], CColorG[set], CColorB[set]));
                    else
                        NewImg.setPixel(i, j,Color.argb(255,255,255,255));
                }
            }
        }
        CColorR[0] = ( CColorR[1] + CColorR[3] + CColorR[4] + CColorR[5] + CColorR[7] ) / 5;
        CColorG[0] = ( CColorG[1] + CColorG[3] + CColorG[4] + CColorG[5] + CColorG[7] ) / 5;
        CColorB[0] = ( CColorB[1] + CColorB[3] + CColorB[4] + CColorB[5] + CColorB[7] ) / 5;

        pic_color=PART(CColorR[0],CColorG[0],CColorB[0]);

       /* CColorA[1] + " " + CColorR[1] + " " + CColorG[1] + " " + CColorB[1]+"\n"
        + CColorA[3] + " " + CColorR[3] + " " + CColorG[3] + " " + CColorB[3]+"\n"
        + CColorA[4] + " " + CColorR[4] + " " + CColorG[4] + " " + CColorB[4]+"\n"
        + CColorA[5] + " " + CColorR[5] + " " + CColorG[5] + " " + CColorB[5]+"\n"
        + CColorA[7] + " " + CColorR[7] + " " + CColorG[7] + " " + CColorB[7]+"\n");*/

        pic_PRdGT = Math.abs(CColorR[1] - CColorG[1]);
        pic_PRdBT = Math.abs(CColorR[1] - CColorB[1]);
        pic_PGdBT = Math.abs(CColorG[1] - CColorB[1]);
        pic_PRdGR = Math.abs(CColorR[5] - CColorG[5]);
        pic_PRdBR = Math.abs(CColorR[5] - CColorB[5]);
        pic_PGdBR = Math.abs(CColorG[5] - CColorB[5]);
        pic_PRdGC = Math.abs(CColorR[4] - CColorG[4]);
        pic_PRdBC = Math.abs(CColorR[4] - CColorB[4]);
        pic_PGdBC = Math.abs(CColorG[4] - CColorB[4]);
        pic_PRdGL = Math.abs(CColorR[3] - CColorG[3]);
        pic_PRdBL = Math.abs(CColorR[3] - CColorB[3]);
        pic_PGdBL = Math.abs(CColorG[3] - CColorB[3]);
        pic_PRdGB = Math.abs(CColorR[7] - CColorG[7]);
        pic_PRdBB = Math.abs(CColorR[7] - CColorB[7]);
        pic_PGdBB = Math.abs(CColorG[7] - CColorB[7]);

    }

    public String PART(int R, int G, int B)
    {
        int RDG = Math.abs(R-G);
        int RDB = Math.abs(R-B);
        int GDB = Math.abs(G-B);
        if(R>=120&&G>=120&&B>=120&&RDG<=40&&RDB<=40&&GDB<=40)
            return "white";
        if(R>150&&R*0.8>(G+B)/2&&R-G>30)
            return "red";
        if(R-B<50&&G<R+B*0.4)
            return "purple";
        if(R-G<30)
            return "yellow";
        return "null";
    }

    private void search(String color){
        int DPRdGT;
        int DPRdBT;
        int DPGdBT;
        int DPRdGR;
        int DPRdBR;
        int DPGdBR;
        int DPRdGC;
        int DPRdBC;
        int DPGdBC;
        int DPRdGL;
        int DPRdBL;
        int DPGdBL;
        int DPRdGB;
        int DPRdBB;
        int DPGdBB;
        int TdC;
        int LdC;
        int RdC;
        int BdC;
        int DTdC;
        int DLdC;
        int DRdC;
        int DBdC;
        int diff = 0;
        ratio.clear();
        id.clear();
        Order.clear();
        temp.clear();

        for(int i=0;i<db_data.size();i++){
            if(db_data.get(i).get("part").toString().contains(color)){

            }
        }


        /*for(int i =1;i< c.getCount();i++)
        {


            if(color.contains("yellow"))
            {
                TdC  = Math.abs((pic_PRdGT - pic_PRdGC));
                LdC  = Math.abs((pic_PRdGL - pic_PRdGC));
                RdC  = Math.abs((pic_PRdGR - pic_PRdGC));
                BdC  = Math.abs((pic_PRdGB - pic_PRdGC));
                DTdC = Math.abs((DPRdGT - DPRdGC));
                DLdC = Math.abs((DPRdGL - DPRdGC));
                DRdC = Math.abs((DPRdGR - DPRdGC));
                DBdC = Math.abs((DPRdGB - DPRdGC));

                diff = Math.abs(TdC - DTdC) + Math.abs(LdC - DLdC) + Math.abs(RdC - DRdC) + Math.abs(BdC - DBdC);

            }
            else if(color.contains("white"))
            {
                TdC  = Math.abs(((pic_PRdGT + pic_PRdBT + pic_PGdBT) - (pic_PRdGC + pic_PRdBC + pic_PGdBC)));
                LdC  = Math.abs(((pic_PRdGL + pic_PRdBL + pic_PGdBL) - (pic_PRdGC + pic_PRdBC + pic_PGdBC)));
                RdC  = Math.abs(((pic_PRdGR + pic_PRdBR + pic_PGdBR) - (pic_PRdGC + pic_PRdBC + pic_PGdBC)));
                BdC  = Math.abs(((pic_PRdGB + pic_PRdBB + pic_PGdBB) - (pic_PRdGC + pic_PRdBC + pic_PGdBC)));
                DTdC = Math.abs(((DPRdGT + DPRdBT + DPGdBT) - (DPRdGC + DPRdBC + DPGdBC)));
                DLdC = Math.abs(((DPRdGL + DPRdBL + DPGdBL) - (DPRdGC + DPRdBC + DPGdBC)));
                DRdC = Math.abs(((DPRdGR + DPRdBR + DPGdBR) - (DPRdGC + DPRdBC + DPGdBC)));
                DBdC = Math.abs(((DPRdGB + DPRdBB + DPGdBB) - (DPRdGC + DPRdBC + DPGdBC)));

                diff = Math.abs(TdC - DTdC) + Math.abs(LdC - DLdC) + Math.abs(RdC - DRdC) + Math.abs(BdC - DBdC);
            }
            else if(color.contains("red"))
            {
                TdC  = Math.abs(((pic_PRdBT + pic_PGdBT) - (pic_PRdBC + pic_PGdBC)));
                LdC  = Math.abs(((pic_PRdBL + pic_PGdBL) - (pic_PRdBC + pic_PGdBC)));
                RdC  = Math.abs(((pic_PRdBR + pic_PGdBR) - (pic_PRdBC + pic_PGdBC)));
                BdC  = Math.abs(((pic_PRdBB + pic_PGdBB) - (pic_PRdBC + pic_PGdBC)));
                DTdC = Math.abs(((DPRdBT + DPGdBT) - (DPRdBC + DPGdBC)));
                DLdC = Math.abs(((DPRdBL + DPGdBL) - (DPRdBC + DPGdBC)));
                DRdC = Math.abs(((DPRdBR + DPGdBR) - (DPRdBC + DPGdBC)));
                DBdC = Math.abs(((DPRdBB + DPGdBB) - (DPRdBC + DPGdBC)));

                diff = Math.abs(TdC - DTdC) + Math.abs(LdC - DLdC) + Math.abs(RdC - DRdC) + Math.abs(BdC - DBdC);

    			TdC  = Math.abs(PRdBT - PRdBC);
	    		LdC  = Math.abs(PRdBL - PRdBC);
	    		RdC  = Math.abs(PRdBR - PRdBC);
	    		BdC  = Math.abs(PRdBB - PRdBC);
	    		DTdC = Math.abs(DPRdBT - DPRdBC);
	    		DLdC = Math.abs(DPRdBL - DPRdBC);
	    		DRdC = Math.abs(DPRdBR - DPRdBC);
	    		DBdC = Math.abs(DPRdBB - DPRdBC);
	    		diff = Math.abs(TdC - DTdC) + Math.abs(LdC - DLdC) + Math.abs(RdC - DRdC) + Math.abs(BdC - DBdC);
            }
            else if(color.contains("purple"))
            {
                TdC  = Math.abs(((pic_PRdGT + pic_PGdBT) - (pic_PRdGC + pic_PGdBC)));
                LdC  = Math.abs(((pic_PRdGL + pic_PGdBL) - (pic_PRdGC + pic_PGdBC)));
                RdC  = Math.abs(((pic_PRdGR + pic_PGdBR) - (pic_PRdGC + pic_PGdBC)));
                BdC  = Math.abs(((pic_PRdGB + pic_PGdBB) - (pic_PRdGC + pic_PGdBC)));
                DTdC = Math.abs(((DPRdGT + DPGdBT) - (DPRdGC + DPGdBC)));
                DLdC = Math.abs(((DPRdGL + DPGdBL) - (DPRdGC + DPGdBC)));
                DRdC = Math.abs(((DPRdGR + DPGdBR) - (DPRdGC + DPGdBC)));
                DBdC = Math.abs(((DPRdGB + DPGdBB) - (DPRdGC + DPGdBC)));

                diff = Math.abs(TdC - DTdC) + Math.abs(LdC - DLdC) + Math.abs(RdC - DRdC) + Math.abs(BdC - DBdC);
            }

            ratio.add(diff);
            c.moveToNext();
        }*/
    }
}
