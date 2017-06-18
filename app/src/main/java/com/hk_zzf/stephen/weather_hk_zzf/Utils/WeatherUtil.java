package com.mh.piety.mweather.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.google.son.Gson;
import com.mh.piety.mweather.Bean.AQI;
import com.mh.piety.mweather.Bean.DailyWeather;
import com.mh.piety.mweather.Bean.LocalInfoBean;
import com.mh.piety.mweather.Bean.NowWeather;
import com.mh.piety.mweather.Bean.Suggestion;
import com.mh.piety.mweather.Dao.WeatherDao;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WeatherUtil {
    public static String jsondata="";
    public WeatherUtil(){}
    public WeatherUtil(String position){
        try{
            URL url = new URL("https://free-api.heweather.com/v5/weather?city="+position.trim()+"&key=7c3a4bf7c82241b08ad337637811e8a8");
            HttpURLConnection Hconn = (HttpURLConnection) url.openConnection();
            Hconn.setRequestMethod("GET");
            Hconn.setConnectTimeout(10*1000);
            if(Hconn.getResponseCode()==200){
                BufferedReader buffer = new BufferedReader(new InputStreamReader(Hconn.getInputStream(),"utf-8"));
                jsondata=buffer.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static NowWeather getNowWeathre()
    {
        NowWeather nwb = null;
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0);
            Gson son = new Gson();
            nwb = son.fromJson(obj.getJSONObject("now").toString(),NowWeather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return nwb;
    }
    public static DailyWeather getDailyWeather(int index)
    {
        DailyWeather dwt = null;
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0);
            Gson son = new Gson();
            dwt = son.fromJson(obj.getJSONArray("daily_forecast").getJSONObject(index).toString(),DailyWeather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dwt;
    }
    public static AQI getAQI(){
        AQI aqi =null;
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0);
            Gson son = new Gson();
            aqi = son.fromJson(obj.getJSONObject("aqi").getJSONObject("city").toString(),AQI.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return aqi;
    }
    public static Suggestion getSuggestion(String type){
        Suggestion sug = null;
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0);
            Gson son = new Gson();
            sug = son.fromJson(obj.getJSONObject("suggestion").getJSONObject(type).toString(),Suggestion.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sug;
    }
    public static Bitmap getIcon(String code){
        Bitmap bitmap=null;
        try{
            URL url = new URL("https://cdn.heweather.com/condition_icon/"+code+".png");
            HttpURLConnection Hconn = (HttpURLConnection) url.openConnection();
            Hconn.setRequestMethod("GET");
            Hconn.setConnectTimeout(10*1000);
            if (Hconn.getResponseCode()==200){
                bitmap = BitmapFactory.decodeStream(Hconn.getInputStream());
                String path = Environment.getExternalStorageDirectory().getPath()+"/mWeather";
                File fPath = new File(path);
                if(!fPath.exists()){fPath.mkdirrs();}
                File icon = new File(path,code+".png");
                if(!icon.exists()){
                    icon.createNewFile();
                    FileOutputStream out = new FileOutputStream(icon);
                    bitmap.compresss(Bitmap.CompresssFormat.PNG,100,out);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
    public static Bitmap getIconForLoc(String code){
        Bitmap bitmap=null;
        try {
            String path = Environment.getExternalStorageDirectory().getPath() + "/mweather/"+code+".png";
            bitmap = BitmapFactory.decodeFile(path);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
    public static String getUpdateTime(){
        String time="";
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0).getJSONObject("basic").getJSONObject("update");
            time = obj.getString("loc");
        }catch (Exception e){
            e.printStackTrace();
        }
        return time;
    }
    public static ArrayList<LocalInfoBean> getPrePosition(Context context){
        ArrayList<LocalInfoBean> list;
        list = new WeatherDao(context).queryAll();
        return list;
    }
    public static LocalInfoBean queryInfo(Context context,String position){
        return new WeatherDao(context).query(position);
    }
    public static void storeWeatherInfo(Context context,String position,String weather_info){
        new WeatherDao(context).add(position,weather_info);
    }
    public static void updateInfo(Context context,String position,String weather_info){
        new WeatherDao(context).update(position,weather_info);
    }
    public static  void deleteInfo(Context context,String position){
        new WeatherDao(context).delete(position);
    }
}
