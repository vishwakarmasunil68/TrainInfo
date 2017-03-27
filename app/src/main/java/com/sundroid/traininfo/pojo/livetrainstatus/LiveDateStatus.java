package com.sundroid.traininfo.pojo.livetrainstatus;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 26-03-2017.
 */

public class LiveDateStatus  implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<LiveDateTrainPOJO> liveDateTrainPOJOs;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<LiveDateTrainPOJO> getLiveDateTrainPOJOs() {
        return liveDateTrainPOJOs;
    }

    public void setLiveDateTrainPOJOs(List<LiveDateTrainPOJO> liveDateTrainPOJOs) {
        this.liveDateTrainPOJOs = liveDateTrainPOJOs;
    }

    @Override
    public String toString() {
        return "LiveDateStatus{" +
                "success='" + success + '\'' +
                ", liveDateTrainPOJOs=" + liveDateTrainPOJOs +
                '}';
    }
}
