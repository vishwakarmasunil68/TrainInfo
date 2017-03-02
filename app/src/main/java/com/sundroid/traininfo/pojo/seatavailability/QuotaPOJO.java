package com.sundroid.traininfo.pojo.seatavailability;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 25-02-2017.
 */

public class QuotaPOJO implements Serializable {
    @SerializedName("quota_code")
    String quota_code;
    @SerializedName("quota_name")
    String quota_name;

    public String getQuota_code() {
        return quota_code;
    }

    public void setQuota_code(String quota_code) {
        this.quota_code = quota_code;
    }

    public String getQuota_name() {
        return quota_name;
    }

    public void setQuota_name(String quota_name) {
        this.quota_name = quota_name;
    }

    @Override
    public String toString() {
        return "QuotaPOJO{" +
                "quota_code='" + quota_code + '\'' +
                ", quota_name='" + quota_name + '\'' +
                '}';
    }
}
