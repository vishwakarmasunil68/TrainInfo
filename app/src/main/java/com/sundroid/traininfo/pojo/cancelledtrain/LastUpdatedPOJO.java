package com.sundroid.traininfo.pojo.cancelledtrain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 05-03-2017.
 */

public class LastUpdatedPOJO {
    @SerializedName("date")
    String date;
    @SerializedName("time")
    String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LastUpdatedPOJO{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
