package com.sundroid.traininfo.pojo.seatavailability;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 25-02-2017.
 */

public class AvailabilityPOJO implements Serializable {
    @SerializedName("status")
    String status;
    @SerializedName("date")
    String date;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AvailabilityPOJO{" +
                "status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
