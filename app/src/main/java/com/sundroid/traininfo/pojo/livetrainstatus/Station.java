package com.sundroid.traininfo.pojo.livetrainstatus;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 26-03-2017.
 */

public class Station implements Serializable {
    @SerializedName("name")
    String name;
    @SerializedName("code")
    String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
