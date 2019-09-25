package com.itkee.webmagic;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Msgsend {
    public static String sendUrl = "http://smssh1.253.com/msg/send/json";
    public static String account = "N5721346";
    public static String password = "4rf6kvD3WRf358";
    public static String lastDate;
    public static List datalist = new ArrayList();
    public static void sendSmsByPost(Map map) {
        map.put("account",account);
        map.put("password",password);
        JSONObject js = (JSONObject) JSONObject.toJSON(map);
        URL url = null;
        try {
            url = new URL(sendUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os=httpURLConnection.getOutputStream();
            os.write(js.toString().getBytes("UTF-8"));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map getMap(String url){
        for(int i=0;i<datalist.size();i++){
            Map m = (Map) datalist.get(i);
            String url_ = m.get("url").toString();
            if(url.equals(url_)){
                return m;
            }
        }
        return null;
    }
}
