package com.sundroid.traininfo.pojo.trainbetween;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 16-02-2017.
 */

public class TrainPOJO implements Serializable {
    @SerializedName("classes")
    List<ClassesPOJO> list_classes;
    @SerializedName("days")
    List<DaysPOJO> list_days;
    @SerializedName("number")
    String number;
    @SerializedName("dest_arrival_time")
    String dest_arrival_time;
    @SerializedName("no")
    String no;
    @SerializedName("name")
    String name;
    @SerializedName("src_departure_time")
    String src_departure_time;
    @SerializedName("travel_time")
    String travel_time;
    @SerializedName("from")
    FromPOJO fromPOJO;
    @SerializedName("to")
    ToPOJO toPOJO;


    public List<ClassesPOJO> getList_classes() {
        return list_classes;
    }

    public void setList_classes(List<ClassesPOJO> list_classes) {
        this.list_classes = list_classes;
    }

    public List<DaysPOJO> getList_days() {
        return list_days;
    }

    public void setList_days(List<DaysPOJO> list_days) {
        this.list_days = list_days;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDest_arrival_time() {
        return dest_arrival_time;
    }

    public void setDest_arrival_time(String dest_arrival_time) {
        this.dest_arrival_time = dest_arrival_time;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc_departure_time() {
        return src_departure_time;
    }

    public void setSrc_departure_time(String src_departure_time) {
        this.src_departure_time = src_departure_time;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public FromPOJO getFromPOJO() {
        return fromPOJO;
    }

    public void setFromPOJO(FromPOJO fromPOJO) {
        this.fromPOJO = fromPOJO;
    }

    public ToPOJO getToPOJO() {
        return toPOJO;
    }

    public void setToPOJO(ToPOJO toPOJO) {
        this.toPOJO = toPOJO;
    }

    @Override
    public String toString() {
        return "TrainPOJO{" +
                "list_classes=" + list_classes +
                ", list_days=" + list_days +
                ", number='" + number + '\'' +
                ", dest_arrival_time='" + dest_arrival_time + '\'' +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", src_departure_time='" + src_departure_time + '\'' +
                ", travel_time='" + travel_time + '\'' +
                ", fromPOJO=" + fromPOJO +
                ", toPOJO=" + toPOJO +
                '}';
    }
}
