package com.sundroid.traininfo.pojo.seatavailability;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 25-02-2017.
 */

public class SeatAvailabilityPOJO implements Serializable{
    @SerializedName("from")
    FromPOJO fromPOJO;
    @SerializedName("quota")
    QuotaPOJO quotaPOJO;
    @SerializedName("response_code")
    String response_code;
    @SerializedName("train_name")
    String train_name;
    @SerializedName("availability")
    List<AvailabilityPOJO> list_availability;
    @SerializedName("to")
    ToPOJO toPOJO;
    @SerializedName("class")
    ClassPOJO classPOJO;
    @SerializedName("train_number")
    String train_number;
    @SerializedName("error")
    String error;
    @SerializedName("failure_rate")
    String failure_rate;
    @SerializedName("last_updated")
    LastUpdatedPOJO lastUpdatedPOJO;


    public FromPOJO getFromPOJO() {
        return fromPOJO;
    }

    public void setFromPOJO(FromPOJO fromPOJO) {
        this.fromPOJO = fromPOJO;
    }

    public QuotaPOJO getQuotaPOJO() {
        return quotaPOJO;
    }

    public void setQuotaPOJO(QuotaPOJO quotaPOJO) {
        this.quotaPOJO = quotaPOJO;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getTrain_name() {
        return train_name;
    }

    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public List<AvailabilityPOJO> getList_availability() {
        return list_availability;
    }

    public void setList_availability(List<AvailabilityPOJO> list_availability) {
        this.list_availability = list_availability;
    }

    public ToPOJO getToPOJO() {
        return toPOJO;
    }

    public void setToPOJO(ToPOJO toPOJO) {
        this.toPOJO = toPOJO;
    }

    public ClassPOJO getClassPOJO() {
        return classPOJO;
    }

    public void setClassPOJO(ClassPOJO classPOJO) {
        this.classPOJO = classPOJO;
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

    public String getFailure_rate() {
        return failure_rate;
    }

    public void setFailure_rate(String failure_rate) {
        this.failure_rate = failure_rate;
    }

    public LastUpdatedPOJO getLastUpdatedPOJO() {
        return lastUpdatedPOJO;
    }

    public void setLastUpdatedPOJO(LastUpdatedPOJO lastUpdatedPOJO) {
        this.lastUpdatedPOJO = lastUpdatedPOJO;
    }

    @Override
    public String toString() {
        return "SeatAvailabilityPOJO{" +
                "fromPOJO=" + fromPOJO +
                ", quotaPOJO=" + quotaPOJO +
                ", response_code='" + response_code + '\'' +
                ", train_name='" + train_name + '\'' +
                ", list_availability=" + list_availability +
                ", toPOJO=" + toPOJO +
                ", classPOJO=" + classPOJO +
                ", train_number='" + train_number + '\'' +
                ", error='" + error + '\'' +
                ", failure_rate='" + failure_rate + '\'' +
                ", lastUpdatedPOJO=" + lastUpdatedPOJO +
                '}';
    }
}
