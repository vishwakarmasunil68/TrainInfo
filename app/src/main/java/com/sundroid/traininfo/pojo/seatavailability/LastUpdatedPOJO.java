package com.sundroid.traininfo.pojo.seatavailability;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 25-02-2017.
 */

public class LastUpdatedPOJO implements Serializable {
    @SerializedName("time")
    String time;
    @SerializedName("date")
    String date;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LastUpdatedPOJO{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
