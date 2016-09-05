package com.gtechoogle.onepic.manager;

import android.content.Context;
import android.util.Log;

import com.gtechoogle.onepic.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PicUriManager {

    private static final String TAG = "PicUriManager";
    private static String httpUrl = "http://apis.baidu.com/txapi/mvtp/meinv";
    private static String testUrl = "https://pixabay.com/api/?key=3240126-be2742b3642b23801d020f9e9&q=亚洲美女&image_type=photo&pretty=true";

    private static  String httpArg = "num=10";

    private static PicUriManager sPicUriManager;
    private Context mContext;
    private static String mApiKey;
    private  PicUriManager(Context context) {
        mContext = context;
        mApiKey = context.getString(R.string.baidu_meinv_key);
    }
    public static synchronized PicUriManager getInstance(Context context) {
        if (sPicUriManager == null) {
            sPicUriManager = new PicUriManager(context);
        }
        return sPicUriManager;
    }

    public String getPicUri() {
        String uri= null;
        String jsonResult = getJson();
        Log.d(TAG,"jsonResult = " + jsonResult);
//        try{
//            JSONObject Jasonobject = new JSONObject(jsonResult);
//            JSONArray Jarray = Jasonobject.getJSONArray("newslist");
//            for (int i = 0; i < Jarray.length(); i++)
//            {
//                JSONObject object = Jarray.getJSONObject(i);
//                uri = object.getString("picUrl");
//                Log.d(TAG,"uri = " + uri);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try{
            JSONObject Jasonobject = new JSONObject(jsonResult);
            JSONArray Jarray = Jasonobject.getJSONArray("hits");
            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject object = Jarray.getJSONObject(i);
                uri = object.getString("webformatURL");
                Log.d(TAG,"uri = " + uri);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return uri;
    }

    public String getJson() {
        //String jsonResult = request(httpUrl, httpArg);
        String jsonResult = request1(testUrl);
        Log.d(TAG,"getJson");

        return jsonResult;
    }
    public String request1(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", "71f0440401bbc4e479a2b29a5f0f976f");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
//    private void parseEasyJson(String json){
//        List<Picture> pics = new ArrayList<Picture>();
//        try{
//            JSONArray jsonArray = new JSONArray(json);
//            for(int i = 0;i < jsonArray.length();i++){
//                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                Picture pic = new Picture();
//                pic.setPicUri(jsonObject.getString("picUrl"));
//                pics.add(pic);
//            }
//        }catch (Exception e){e.printStackTrace();}
//    }
//    public class Picture {
//        private String num;
//        private String title;
//        private String desp;
//        private String picUri;
//        void setPicUri(String uri) {
//            picUri = uri;
//        }
//
//    }
}
