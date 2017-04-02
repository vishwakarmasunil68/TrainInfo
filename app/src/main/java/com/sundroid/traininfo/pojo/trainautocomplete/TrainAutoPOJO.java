package com.sundroid.traininfo.pojo.trainautocomplete;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 28-03-2017.
 */

public class TrainAutoPOJO {
    @SerializedName("total")
    int total;
    @SerializedName("response_code")
    int response_code;
    @SerializedName("trains")
    List<TrainAutoResultPOJO> trainAutoResultPOJOList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<TrainAutoResultPOJO> getTrainAutoResultPOJOList() {
        return trainAutoResultPOJOList;
    }

    public void setTrainAutoResultPOJOList(List<TrainAutoResultPOJO> trainAutoResultPOJOList) {
        this.trainAutoResultPOJOList = trainAutoResultPOJOList;
    }

    @Override
    public String toString() {
        return "TrainAutoPOJO{" +
                "total=" + total +
                ", response_code=" + response_code +
                ", trainAutoResultPOJOList=" + trainAutoResultPOJOList +
                '}';
    }
}
