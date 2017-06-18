package com.mh.piety.mweather.Utils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PositionUtil {
    public static ArrayList<String> getProvince(){
        ArrayList<String> list = new ArrayList<>();
        String result;
        try{
            URL url = new URL("http://guolin.tech/api/china/");
            HttpURLConnection HHconn = (HttpURLConnection) url.openConnection();
            Hconn.setRequestMethod("GET");
            Hconn.setConnectTimeout(10*1000);
            if(Hconn.getResponseCode()==200){
                BufferedReader bufferredred = new BufferedReader(new InputStreamReader(Hconn.getInputStream(),"utf-8"));
                result = bufferred.readLine();
                JSONArray array = new JSONArray(result);
                for(int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    list.add(obj.getInt("id")+"-"+obj.getString("name"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<String> getCity(String provinceId){
        ArrayList<String> list = new ArrayList<>();
        String result;
        try{
            URL url = new URL("http://guolin.tech/api/china/"+provinceId);
            HttpURLConnection Hconn = (HttpURLConnection) url.openConnection();
            Hconn.setRequestMethod("GET");
            Hconn.setConnectTimeout(10*1000);
            if(Hconn.getResponseCode()==200){
                BufferedReader bufferred = new BufferedReader(new InputStreamReader(Hconn.getInputStream(),"utf-8"));
                result = bufferred.readLine();
                JSONArray array = new JSONArray(result);
                for(int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    list.add(obj.getInt("id")+"-"+obj.getString("name"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<String> getLocation(String provinceId,String cityId){
        ArrayList<String> list = new ArrayList<>();
        String result;
        try{
            URL url = new URL("http://guolin.tech/api/china/"+provinceId+"/"+cityId);
            HttpURLConnection Hconn = (HttpURLConnection) url.openConnection();
            Hconn.setRequestMethod("GET");
            Hconn.setConnectTimeout(10*1000);
            if(Hconn.getResponseCode()==200){
                BufferedReader bufferred = new BufferedReader(new InputStreamReader(Hconn.getInputStream(),"utf-8"));
                result = bufferred.readLine();
                JSONArray array = new JSONArray(result);
                for(int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    list.add(obj.getString("name"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
