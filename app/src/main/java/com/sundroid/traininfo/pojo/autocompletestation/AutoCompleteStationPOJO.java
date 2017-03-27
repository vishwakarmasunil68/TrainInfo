package com.sundroid.traininfo.pojo.autocompletestation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 26-03-2017.
 */

public class AutoCompleteStationPOJO {
    @SerializedName("response_code")
    int response_code;
    @SerializedName("total")
    int total;
    @SerializedName("station")
    List<StationAutocompletePOJO> stationAutocompletePOJOList;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<StationAutocompletePOJO> getStationAutocompletePOJOList() {
        return stationAutocompletePOJOList;
    }

    public void setStationAutocompletePOJOList(List<StationAutocompletePOJO> stationAutocompletePOJOList) {
        this.stationAutocompletePOJOList = stationAutocompletePOJOList;
    }

    @Override
    public String toString() {
        return "AutoCompleteStationPOJO{" +
                "response_code=" + response_code +
                ", total=" + total +
                ", stationAutocompletePOJOList=" + stationAutocompletePOJOList +
                '}';
    }
}
