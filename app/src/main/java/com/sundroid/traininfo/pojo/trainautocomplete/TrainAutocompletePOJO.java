package com.sundroid.traininfo.pojo.trainautocomplete;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 26-03-2017.
 */

public class TrainAutocompletePOJO {
    @SerializedName("train")
    List<String> train;

    public List<String> getTrain() {
        return train;
    }

    public void setTrain(List<String> train) {
        this.train = train;
    }

    @Override
    public String toString() {
        return "TrainAutocompletePOJO{" +
                "train=" + train.toString() +
                '}';
    }
}
