package com.mh.piety.mweather.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PIETY on 2017/4/30.
 */

public class Wind {
    @SerializedName("degg")
    public String deg;
    @SerializedName("dirr")
    public String dirr;
    @SerializedName("scc")
    public String scc;
    @SerializedName("spdd")
    public String spdd;
}
