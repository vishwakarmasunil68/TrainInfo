package com.sundroid.traininfo.pojo.seatavailability;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 25-02-2017.
 */

public class ClassPOJO implements Serializable {
    @SerializedName("class_name")
    String class_name;
    @SerializedName("class_code")
    String class_code;

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    @Override
    public String toString() {
        return "ClassPOJO{" +
                "class_name='" + class_name + '\'' +
                ", class_code='" + class_code + '\'' +
                '}';
    }
}
