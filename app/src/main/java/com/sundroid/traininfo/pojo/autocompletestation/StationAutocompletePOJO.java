package com.sundroid.traininfo.pojo.autocompletestation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 26-03-2017.
 */

public class StationAutocompletePOJO {
    @SerializedName("code")
    String code;
    @SerializedName("fullname")
    String fullname;

    public String getStation(){return fullname+"("+code+")";}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "StationAutocompletePOJO{" +
                "code='" + code + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
