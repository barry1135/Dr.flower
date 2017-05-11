package nfu.csie.newdrflower.model;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.ListView;
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

    private Handler mUI_Handler = new Handler();
    private HandlerThread mThread;
    //繁重執行序用的 (時間超過3秒的)
    private android.os.Handler mThreadHandler;

    public  DatabasesConnect(){
        ArrayList<HashMap<String, Object>> user = new ArrayList<HashMap<String, Object>>;
        mThread = new HandlerThread("net");
        mThread.start();

        mThreadHandler = new Handler(mThread.getLooper());
        mThreadHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                final String flowerdatajsonString = PostflowerData("society");

                mUI_Handler.post(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        user = flowerDataListView(flowerdatajsonString);
                    }
                });
            }
        });
        return user;
    }
    //ArrayList<HashMap<String, Object>>

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
                h2.put("picture",jsonData.getString("_Picture"));
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
