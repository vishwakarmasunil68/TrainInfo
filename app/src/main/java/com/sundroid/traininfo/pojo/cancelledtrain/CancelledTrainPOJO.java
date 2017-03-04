package com.sundroid.traininfo.pojo.cancelledtrain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 05-03-2017.
 */

public class CancelledTrainPOJO {
    @SerializedName("response_code")
    String response_code;
    @SerializedName("last_updated")
    LastUpdatedPOJO last_updated;
    @SerializedName("trains")
    List<TrainsPOJO> list_trains;


    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public LastUpdatedPOJO getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(LastUpdatedPOJO last_updated) {
        this.last_updated = last_updated;
    }

    public List<TrainsPOJO> getList_trains() {
        return list_trains;
    }

    public void setList_trains(List<TrainsPOJO> list_trains) {
        this.list_trains = list_trains;
    }

    @Override
    public String toString() {
        return "CancelledTrainPOJO{" +
                "response_code='" + response_code + '\'' +
                ", last_updated=" + last_updated +
                ", list_trains=" + list_trains +
                '}';
    }
}
