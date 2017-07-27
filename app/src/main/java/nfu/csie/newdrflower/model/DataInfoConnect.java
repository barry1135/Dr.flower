package nfu.csie.newdrflower.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by barry on 2017/5/18.
 */

public class DataInfoConnect {

    private ArrayList<HashMap<String, Object>> user = new ArrayList<>();


    public ArrayList<HashMap<String,Object>> DBInforeturn(int order){
        String flowerdatajsonString = PostflowerData();
         flowerDataListView(flowerdatajsonString,order);

        return user;
    }
    //ArrayList<HashMap<String, Object>>

    private String PostflowerData()
    {
        String result = "";
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try
        {
            /*HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://172.20.10.2/flowerInfo.php");
            List<NameValuePair> nameValuePairs = new ArrayList<>();
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
            result = builder.toString();*/

            URL url = new URL("http://172.20.10.2/flowerInfo.php");
            //URL url = new URL("http://172.20.10.4/flowerData.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("");
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            inputStream = conn.getInputStream();

            BufferedReader bufferedReader=new BufferedReader(
                    new InputStreamReader(inputStream, "utf-8"));

            result=bufferedReader.readLine();
        }
        catch (Exception e)
        {
            Log.e("log_tag", e.toString());
        }
        return result;
    }

    private void flowerDataListView(String input,int order)
    {
	/*
	 * SQL 結果有多筆資料時使用JSONArray
	 * 只有一筆資料時直接建立JSONObject物件
	 * JSONObject jsonData = new JSONObject(result);
	 */
        try
        {
            JSONArray jsonArray = new JSONArray(input);
            ArrayList<HashMap<String, Object>> users = new ArrayList<>();
            JSONObject jsonData = jsonArray.getJSONObject(order);
            HashMap<String, Object> h2 = new HashMap<>();
            h2.put("name", jsonData.getString("_Name"));
            h2.put("scientificname", jsonData.getString("_ScientificName"));
            h2.put("englishname", jsonData.getString("_EnglishName"));
            h2.put("othername", jsonData.getString("_OtherName"));
            h2.put("kind", jsonData.getString("_Kind"));
            h2.put("feature", jsonData.getString("_Feature"));
            users.add(h2);
            user = users;
        }
        catch (JSONException e)
        {
            // TODO 自動產生的 catch 區塊
            e.printStackTrace();
        }

    }

}
