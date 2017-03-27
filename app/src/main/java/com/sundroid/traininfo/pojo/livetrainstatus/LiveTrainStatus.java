package com.sundroid.traininfo.pojo.livetrainstatus;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 26-03-2017.
 */

public class LiveTrainStatus implements Serializable {
    @SerializedName("position")
    String position;
    @SerializedName("current_station")
    CurrentStation currentStation;
    @SerializedName("start_date")
    String start_date;
    @SerializedName("train_number")
    String train_number;
    @SerializedName("error")
    String error;
    @SerializedName("response_code")
    int response_code;
    @SerializedName("route")
    List<Route> routeList;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public CurrentStation getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(CurrentStation currentStation) {
        this.currentStation = currentStation;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getTrain_number() {
        return train_number;
    }

    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    @Override
    public String toString() {
        return "LiveTrainStatus{" +
                "position='" + position + '\'' +
                ", currentStation=" + currentStation +
                ", start_date='" + start_date + '\'' +
                ", train_number='" + train_number + '\'' +
                ", error='" + error + '\'' +
                ", response_code=" + response_code +
                ", routeList=" + routeList +
                '}';
    }
}
