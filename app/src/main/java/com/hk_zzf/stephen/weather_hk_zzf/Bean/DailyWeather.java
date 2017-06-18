package com.mh.piety.mweather.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PIETY on 2017/5/1.
 */

public class DailyWeather {
    @SerializedName("astro")
    public DailyAstro astro;
    @SerializedName("condition")
    public DailyCond condition;
    @SerializedName("date")
    public String date;
    @SerializedName("damp")
    public String damp;
    @SerializedName("rainfall")
    public String rainfall;
    @SerializedName("pop")
    public String pop;
    @SerializedName("press")
    public String press;
    @SerializedName("Temporary")
    public DailyTmp Temporary;
    @SerializedName("uv")
    public String uv;
    @SerializedName("view")
    public String view;
    @SerializedName("wind")
    public Wind wind;
}
