package nfu.csie.newdrflower.controller;
import android.util.Log;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by barry on 2017/5/10.
 */

public class DatabasesConnect {

    public DatabasesConnect(){

    }


    public ArrayList<HashMap<String, Object>> flowerInfo(){


        return
    }

    public ArrayList<HashMap<String, Object>> flowerdata(){

        return
    }

    private String PostflowerInfo(String query)
    {
        String result = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://172.20.10.2/flowerInfo.php");
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

    public final void flowerInfoListView(String input)
    {
	/*
	 * SQL 結果有多筆資料時使用JSONArray
	 * 只有一筆資料時直接建立JSONObject物件
	 * JSONObject jsonData = new JSONObject(result);
	 */
        try
        {
            JSONArray jsonArray = new JSONArray(input);
            ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                HashMap<String, Object> h2 = new HashMap<String, Object>();
                h2.put("id", jsonData.getString("_id"));
                h2.put("name", jsonData.getString("_Name"));
                h2.put("scientificName",jsonData.getString("_ScientificName"));
                h2.put("englishName",jsonData.getString("_EnglishName"));
                h2.put("otherName",jsonData.getString("_OtherName"));
                h2.put("kind",jsonData.getString("_Kind"));
                h2.put("feature",jsonData.getString("_Feature"));
                users.add(h2);

            }
        }
        catch (JSONException e)
        {
            // TODO 自動產生的 catch 區塊
            e.printStackTrace();
        }
    }

    private String PostflowerData(String query)
    {
        String result = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://172.20.10.2/flowerdata.php");
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

    public final void flowerDataListView(String input)
    {
	/*
	 * SQL 結果有多筆資料時使用JSONArray
	 * 只有一筆資料時直接建立JSONObject物件
	 * JSONObject jsonData = new JSONObject(result);
	 */
        try
        {
            JSONArray jsonArray = new JSONArray(input);
            ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                HashMap<String, Object> h2 = new HashMap<String, Object>();
                h2.put("order",jsonData.getString("_Order"));
                h2.put("id",jsonData.getString("_id"));
                h2.put("part",jsonData.getString(""));
                h2.put("picture",jsonData.getString(""));
                h2.put("prt",jsonData.getString(""));
                h2.put("pgt",jsonData.getString(""));
                h2.put("pbt",jsonData.getString(""));
                h2.put("prr",jsonData.getString(""));
                h2.put("pgr",jsonData.getString(""));
                h2.put("pbr",jsonData.getString(""));
                h2.put("prc",jsonData.getString(""));
                h2.put("pgc",jsonData.getString(""));
                h2.put("pbc",jsonData.getString(""));
                h2.put("prl",jsonData.getString(""));
                h2.put("pgl",jsonData.getString(""));
                h2.put("pbl",jsonData.getString(""));
                h2.put("prb",jsonData.getString(""));
                h2.put("pgb",jsonData.getString(""));
                h2.put("pbb",jsonData.getString(""));
                h2.put("prdgt",jsonData.getString(""));
                h2.put("prdbt",jsonData.getString(""));
                h2.put("pgdbt",jsonData.getString(""));
                h2.put("prdgr",jsonData.getString(""));
                h2.put("prdbr",jsonData.getString(""));
                h2.put("pgdbr",jsonData.getString(""));
                h2.put("prdgc",jsonData.getString(""));
                h2.put("prdbc",jsonData.getString(""));
                h2.put("prdgl",jsonData.getString(""));
                h2.put("prdbl",jsonData.getString(""));
                h2.put("pgdbl",jsonData.getString(""));
                h2.put("prdgb",jsonData.getString(""));
                h2.put("prdbb",jsonData.getString(""));
                h2.put("pgdbb",jsonData.getString(""));
                users.add(h2);

            }
        }
        catch (JSONException e)
        {
            // TODO 自動產生的 catch 區塊
            e.printStackTrace();
        }
    }

}
