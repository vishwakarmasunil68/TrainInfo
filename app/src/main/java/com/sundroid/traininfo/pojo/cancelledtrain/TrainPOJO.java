package com.sundroid.traininfo.pojo.cancelledtrain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 05-03-2017.
 */

public class TrainPOJO {
    @SerializedName("number")
    String number;
    @SerializedName("start_time")
    String start_time;
    @SerializedName("name")
    String name;
    @SerializedName("type")
    String type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TrainPOJO{" +
                "number='" + number + '\'' +
                ", start_time='" + start_time + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
