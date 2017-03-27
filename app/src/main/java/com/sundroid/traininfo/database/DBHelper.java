package com.sundroid.traininfo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sundroid.traininfo.pojo.autocompletestation.StationAutocompletePOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 27-03-2017.
 */

public class DBHelper {
    DataBaseHelper helper;
    public DBHelper(Context context){
        helper=new DataBaseHelper(context);
    }



    public long insertstation(StationAutocompletePOJO pojo){
//        //Log.d("sunil","insert:-"+dep.toString());
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(DataBaseHelper.full_name, pojo.getFullname()+"");
        contentValues.put(DataBaseHelper.code, pojo.getCode()+"");
        contentValues.put(DataBaseHelper.stationname, pojo.getStation()+"");


        long id=db.insert(DataBaseHelper.STATION_TABLE, null, contentValues);
        return id;
    }

    public List<StationAutocompletePOJO> getAllStation(){
        SQLiteDatabase db=helper.getWritableDatabase();
        List<StationAutocompletePOJO> lst=new ArrayList<>();
        String[] columns={DataBaseHelper.ID,
                DataBaseHelper.full_name,
                DataBaseHelper.code,
                DataBaseHelper.stationname
        };
        Cursor cursor=db.query(DataBaseHelper.STATION_TABLE, columns, null, null, null, null, null);

        while(cursor.moveToNext()){
            String full_name=cursor.getString(1);
            String code=cursor.getString(2);
            String stationname=cursor.getString(3);

            StationAutocompletePOJO station=new StationAutocompletePOJO();
            station.setCode(code);
            station.setFullname(full_name);



            lst.add(station);
        }
        return lst;

    }
    public StationAutocompletePOJO getStationbyStationName(String full_name,String code){
        SQLiteDatabase db=helper.getWritableDatabase();
        StationAutocompletePOJO station = null;
        String[] columns={DataBaseHelper.ID,
                DataBaseHelper.full_name,
                DataBaseHelper.code,
                DataBaseHelper.stationname
        };
//        Cursor cursor=db.query(DataBaseHelper.OBSTACLE_TABLE, columns, null, null, null, null, null);
        Cursor cursor=db.query(DataBaseHelper.STATION_TABLE, columns, DataBaseHelper.full_name+" = '"+full_name+"'", null, null, null, null);
        if(cursor.moveToNext()){
            String full_name1=cursor.getString(1);
            String code1=cursor.getString(2);
            String stationname1=cursor.getString(3);

            station=new StationAutocompletePOJO();
            station.setCode(code1);
            station.setFullname(full_name1);
        }
        cursor.close();
        return station;
    }
    public void deleteAllStations(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("delete from "+ DataBaseHelper.STATION_TABLE);
    }


//    public int UpdateStoreImagePath(String store_id,String store_image_path){
//        //UPDATE TABLE SET Name='vav' where Name=?
//        SQLiteDatabase db=helper.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(DataBaseHelper.STORE_IMAGE_PATH,store_image_path);
//        String[] whereArgs={store_id};
//        int count=db.update(DataBaseHelper.STORE_TABLE,contentValues,DataBaseHelper.STORE_ID+" =? ",whereArgs);
//        return count;
//    }

//    public int deleteStoreChatByPatientID(String p_id){
//        SQLiteDatabase db=helper.getWritableDatabase();
//        String[] whereArgs={p_id};
//        int count=db.delete(DataBaseHelper.STORED_URGENT_CHAT_TABLE, DataBaseHelper.u_chat_p_id+"=?", whereArgs);
//        return count;
//    }

    static class DataBaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME="traindb";

        //table names
        private static final String STATION_TABLE="stationtb";


        private static final int DATABASE_VERSION=2;

        //columns for the ItemData
        private static final String ID="_id";
        private static final String full_name="full_name";
        private static final String code = "code";
        private static final String stationname = "stationname";



        private static final String CREATE_STATION_TABLE="CREATE TABLE "+STATION_TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +full_name+" VARCHAR(255), "
                +code +" VARCHAR(255), "
                + stationname +" VARCHAR(255) "
                +");";





        private static final String DROP_STATION_TABLE="DROP TABLE IF EXISTS "+STATION_TABLE;


        private Context context;



        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try{

                db.execSQL(CREATE_STATION_TABLE);

//                Toast.makeText(context, "database called", Toast.LENGTH_SHORT).show();
                Log.d("sunil","database called");
            }
            catch(Exception e){
//                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("sunil",e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            try{
                db.execSQL(DROP_STATION_TABLE);

//                Toast.makeText(context, "database droped", Toast.LENGTH_SHORT).show();
                Log.d("sunil","database droped");
                onCreate(db);
            }
            catch(Exception e){
//                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("sunil",e.toString());
            }
        }

    }

}
