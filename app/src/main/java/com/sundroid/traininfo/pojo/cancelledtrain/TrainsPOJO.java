package com.sundroid.traininfo.pojo.cancelledtrain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 05-03-2017.
 */

public class TrainsPOJO {
    @SerializedName("train")
    TrainPOJO train;
    @SerializedName("dest")
    DestPOJO destPOJO;
    @SerializedName("source")
    SourcePOJO sourcePOJO;

    public TrainPOJO getTrain() {
        return train;
    }

    public void setTrain(TrainPOJO train) {
        this.train = train;
    }

    public DestPOJO getDestPOJO() {
        return destPOJO;
    }

    public void setDestPOJO(DestPOJO destPOJO) {
        this.destPOJO = destPOJO;
    }

    public SourcePOJO getSourcePOJO() {
        return sourcePOJO;
    }

    public void setSourcePOJO(SourcePOJO sourcePOJO) {
        this.sourcePOJO = sourcePOJO;
    }

    @Override
    public String toString() {
        return "TrainsPOJO{" +
                "train=" + train +
                ", destPOJO=" + destPOJO +
                ", sourcePOJO=" + sourcePOJO +
                '}';
    }
}
