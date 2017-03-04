package com.sundroid.traininfo.pojo.trainarrivalatstation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 05-03-2017.
 */

public class TrainPOJO {
    @SerializedName("scharr")
    String scharr;
    @SerializedName("delayarr")
    String delayarr;
    @SerializedName("schdep")
    String schdep;
    @SerializedName("name")
    String name;
    @SerializedName("actdep")
    String actdep;
    @SerializedName("delaydep")
    String delaydep;
    @SerializedName("actarr")
    String actarr;
    @SerializedName("number")
    String number;

    public String getScharr() {
        return scharr;
    }

    public void setScharr(String scharr) {
        this.scharr = scharr;
    }

    public String getDelayarr() {
        return delayarr;
    }

    public void setDelayarr(String delayarr) {
        this.delayarr = delayarr;
    }

    public String getSchdep() {
        return schdep;
    }

    public void setSchdep(String schdep) {
        this.schdep = schdep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActdep() {
        return actdep;
    }

    public void setActdep(String actdep) {
        this.actdep = actdep;
    }

    public String getDelaydep() {
        return delaydep;
    }

    public void setDelaydep(String delaydep) {
        this.delaydep = delaydep;
    }

    public String getActarr() {
        return actarr;
    }

    public void setActarr(String actarr) {
        this.actarr = actarr;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "TrainPOJO{" +
                "scharr='" + scharr + '\'' +
                ", delayarr='" + delayarr + '\'' +
                ", schdep='" + schdep + '\'' +
                ", name='" + name + '\'' +
                ", actdep='" + actdep + '\'' +
                ", delaydep='" + delaydep + '\'' +
                ", actarr='" + actarr + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
