package com.sundroid.traininfo.pojo.trainautocomplete;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 28-03-2017.
 */

public class TrainAutoResultPOJO {
    @SerializedName("name")
    String name;
    @SerializedName("number")
    String number;

    public String getName() {
        return name;
    }

    public String getFullName(){return name+"("+number+")";}

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "TrainAutoResultPOJO{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
