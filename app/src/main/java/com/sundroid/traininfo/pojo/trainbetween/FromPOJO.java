package com.sundroid.traininfo.pojo.trainbetween;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 16-02-2017.
 */

public class FromPOJO {
    @SerializedName("code")
    String code;
    @SerializedName("name")
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ToPOJO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
