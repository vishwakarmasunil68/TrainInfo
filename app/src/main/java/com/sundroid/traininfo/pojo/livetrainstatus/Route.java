package com.sundroid.traininfo.pojo.livetrainstatus;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 26-03-2017.
 */

public class Route implements Serializable {
    @SerializedName("day")
    int day;
    @SerializedName("status")
    String status;
    @SerializedName("distance")
    int distance;
    @SerializedName("scharr_date")
    String scharr_date;
    @SerializedName("schdep")
    String schdep;
    @SerializedName("station")
    String station;
    @SerializedName("station_")
    Station station_;
    @SerializedName("no")
    int no;
    @SerializedName("actarr")
    String actarr;
    @SerializedName("actdep")
    String actdep;
    @SerializedName("scharr")
    String scharr;
    @SerializedName("has_departed")
    boolean has_departed;
    @SerializedName("latemin")
    int latemin;
    @SerializedName("actarr_date")
    String actarr_date;
    @SerializedName("has_arrived")
    boolean has_arrived;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getScharr_date() {
        return scharr_date;
    }

    public void setScharr_date(String scharr_date) {
        this.scharr_date = scharr_date;
    }

    public String getSchdep() {
        return schdep;
    }

    public void setSchdep(String schdep) {
        this.schdep = schdep;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Station getStation_() {
        return station_;
    }

    public void setStation_(Station station_) {
        this.station_ = station_;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getActarr() {
        return actarr;
    }

    public void setActarr(String actarr) {
        this.actarr = actarr;
    }

    public String getActdep() {
        return actdep;
    }

    public void setActdep(String actdep) {
        this.actdep = actdep;
    }

    public String getScharr() {
        return scharr;
    }

    public void setScharr(String scharr) {
        this.scharr = scharr;
    }

    public boolean isHas_departed() {
        return has_departed;
    }

    public void setHas_departed(boolean has_departed) {
        this.has_departed = has_departed;
    }

    public int getLatemin() {
        return latemin;
    }

    public void setLatemin(int latemin) {
        this.latemin = latemin;
    }

    public String getActarr_date() {
        return actarr_date;
    }

    public void setActarr_date(String actarr_date) {
        this.actarr_date = actarr_date;
    }

    public boolean isHas_arrived() {
        return has_arrived;
    }

    public void setHas_arrived(boolean has_arrived) {
        this.has_arrived = has_arrived;
    }

    @Override
    public String toString() {
        return "Route{" +
                "day=" + day +
                ", status='" + status + '\'' +
                ", distance=" + distance +
                ", scharr_date='" + scharr_date + '\'' +
                ", schdep='" + schdep + '\'' +
                ", station='" + station + '\'' +
                ", station_=" + station_ +
                ", no=" + no +
                ", actarr='" + actarr + '\'' +
                ", actdep='" + actdep + '\'' +
                ", scharr='" + scharr + '\'' +
                ", has_departed=" + has_departed +
                ", latemin=" + latemin +
                ", actarr_date='" + actarr_date + '\'' +
                ", has_arrived=" + has_arrived +
                '}';
    }
}
