package com.sundroid.traininfo.pojo.livetrainstatus;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 26-03-2017.
 */

public class LiveDateTrainPOJO implements Serializable {
    @SerializedName("date")
    String date;
    @SerializedName("result")
    List<Route> routeList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    @Override
    public String toString() {
        return "LiveDateTrainPOJO{" +
                "date='" + date + '\'' +
                ", routeList=" + routeList +
                '}';
    }
}
