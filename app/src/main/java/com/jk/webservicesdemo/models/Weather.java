package com.jk.webservicesdemo.models;

import com.google.gson.annotations.SerializedName;

public class Weather {
    private @SerializedName("current") Current currentWeather;
}

class Current{
    private float temp_c;
    private @SerializedName("wind_kph") float wind;
    private @SerializedName("condition") Condition currentCondition;

    //getter and toString()
}

class Condition{
    private @SerializedName("text") String currentCondition;
    private @SerializedName("icon") String image;

    //getter and toString()
}
