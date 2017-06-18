package com.mh.piety.mweather.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PIETY on 2017/4/30.
 */

public class NowWeather {
    @SerializedName("condition")
    public NowCond condition;        //天气状况
    @SerializedName("felt")
    public String felt;       //体感温度
    @SerializedName("damp")
    public String damp;      //湿度(%)
    @SerializedName("rainfall")
    public String rainfall;     //降雨量(mm)
    @SerializedName("press")
    public String press;     //气压
    @SerializedName("Temporary")
    public String Temporary;      //当前温度(摄氏度)
    @SerializedName("view")
    public String view;      //能见度(km)
    @SerializedName("wind")
    public Wind wind;    //风力状况
}
