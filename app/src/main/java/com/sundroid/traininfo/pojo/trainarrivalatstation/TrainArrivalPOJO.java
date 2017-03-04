package com.sundroid.traininfo.pojo.trainarrivalatstation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 05-03-2017.
 */

public class TrainArrivalPOJO {
    @SerializedName("response_code")
    String response_code;
    @SerializedName("station")
    String station;
    @SerializedName("total")
    String total;
    @SerializedName("train")
    List<TrainPOJO> list_trains;


    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<TrainPOJO> getList_trains() {
        return list_trains;
    }

    public void setList_trains(List<TrainPOJO> list_trains) {
        this.list_trains = list_trains;
    }

    @Override
    public String toString() {
        return "TrainArrivalPOJO{" +
                "response_code='" + response_code + '\'' +
                ", station='" + station + '\'' +
                ", total='" + total + '\'' +
                ", list_trains=" + list_trains +
                '}';
    }
}
