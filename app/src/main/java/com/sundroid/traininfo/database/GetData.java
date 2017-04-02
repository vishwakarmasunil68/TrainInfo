package com.sundroid.traininfo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sundroid.traininfo.pojo.autocompletestation.StationAutocompletePOJO;
import com.sundroid.traininfo.pojo.trainautocomplete.TrainAutoResultPOJO;

import java.util.ArrayList;

/**
 * Created by sunil on 29-03-2017.
 */

public class GetData {

    SQLiteDatabase _database;

    DatabaseHelper databaseHelper;
    Context mContext;
    private final String TAG=getClass().getSimpleName();
    public GetData(Context context)
    {
        mContext = context;
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public ArrayList<TrainAutoResultPOJO> getTrainList(String trainkey)
    {
        ArrayList<TrainAutoResultPOJO> trainAutoResultPOJOArrayList = new ArrayList<TrainAutoResultPOJO>();
        String query = "Select * from "+ TableAttributes.TRAIN_TABLE + " where "+ TableAttributes.train_full_name+ " like '%"+trainkey+"%'" ;

        _database = databaseHelper.openDataBase();
        Cursor c = _database.rawQuery(query, null);
        try{
            if (c.moveToFirst()) {
                do {
                    TrainAutoResultPOJO trainAutoResultPOJO = new TrainAutoResultPOJO();
                    trainAutoResultPOJO.setNumber(c.getString(1));
                    trainAutoResultPOJO.setName(c.getString(2));

                    trainAutoResultPOJOArrayList.add(trainAutoResultPOJO);
                }
                while (c.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
        c.close();
        _database.close();

        return trainAutoResultPOJOArrayList;
    }

    public ArrayList<StationAutocompletePOJO> getStationList(String stationkey)
    {
        ArrayList<StationAutocompletePOJO> stationAutocompletePOJOArrayList = new ArrayList<StationAutocompletePOJO>();
        String query = "Select * from "+ TableAttributes.STATION_TABLE + " where "+ TableAttributes.stationname+ " like '%"+stationkey+"%'" ;

        _database = databaseHelper.openDataBase();
        Cursor c = _database.rawQuery(query, null);
        try{
            if (c.moveToFirst()) {
                do {
                    StationAutocompletePOJO stationAutocompletePOJO = new StationAutocompletePOJO();
                    stationAutocompletePOJO.setFullname(c.getString(1));
                    stationAutocompletePOJO.setCode(c.getString(2));

                    stationAutocompletePOJOArrayList.add(stationAutocompletePOJO);
                }
                while (c.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
        c.close();
        _database.close();

        return stationAutocompletePOJOArrayList;
    }
}
