package com.mh.piety.mweather.Dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mh.piety.mweather.Bean.LocalInfoBean;

import java.util.ArrayList;

public class WeatherDao {
    private SQLiteDatabase db;
    public WeatherDao(Context context){
        MySqliteOpenHelper openHelp = new MySqliteOpenHelper(context);
        db=openHelp.getReadableDatabase();
    }
    public ArrayList<LocalInfoBean> queryAll(){
        ArrayList<LocalInfoBean> list = new ArrayList<>();
        LocalInfoBean loca;
        Cursor cursor =db.rawQuery("select * from weather",null);
        while (cursor.moveToNext()){
            loca = new LocalInfoBean();
            loca.position=cursor.getString(1);
            loca.weather_info=cursor.getString(2);
            list.add(loca);
        }
        cursor.close();
        db.close();
        return list;
    }
    public LocalInfoBean query(String position){
        Cursor cursor=db.rawQuery("select * from weather where position=?",new String[]{position});
        if(cursor.moveToNext()){
            LocalInfoBean lbi = new LocalInfoBean();
            lbi.position=cursor.getString(1);
            lbi.weather_info=cursor.getString(2);
            return lbi;
        }
        cursor.close();
        db.close();
        return null;
    }
    public void add(String position,String weather_info){
        ContentValues value = new ContentValues();
        value.put("position",position);
        value.put("weather_info",weather_info);
        db.insert("weather",null,value);
        db.close();
    }
    public void update(String position,String weather_info){
        ContentValues value = new ContentValues();
        value.put("weather_info",weather_info);
        db.update("weather",value,"position=?",new String[]{position});
        db.close();
    }
    public void delete(String position){
        db.delete("weather","position=?",new String[]{position});
        db.close();
    }
}
