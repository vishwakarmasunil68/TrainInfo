package com.sundroid.traininfo.database;

/**
 * Created by sunil on 29-03-2017.
 */

public class TableAttributes {
    public static final String DATABASE_NAME="traindb";

    //table names
    public static final String STATION_TABLE="stationtb";
    public static final String TRAIN_TABLE="traintb";


    public static final int DATABASE_VERSION=5;

    //columns for the ItemData
    public static final String ID="_id";
    public static final String full_name="full_name";
    public static final String code = "code";
    public static final String stationname = "stationname";


    public static final String train_number = "number";
    public static final String train_name = "name";
    public static final String train_full_name = "full_name";
}
