package com.sundroid.traininfo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.ToastClass;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.database.DBHelper;
import com.sundroid.traininfo.database.GetData;
import com.sundroid.traininfo.pojo.autocompletestation.AutoCompleteStationPOJO;
import com.sundroid.traininfo.pojo.autocompletestation.StationAutocompletePOJO;
import com.sundroid.traininfo.pojo.trainarrivalatstation.TrainArrivalPOJO;
import com.sundroid.traininfo.pojo.trainarrivalatstation.TrainPOJO;
import com.sundroid.traininfo.pojo.trainautocomplete.TrainAutoResultPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sundroid.traininfo.R.id.tv_date;

public class TrainArrivalAtStationActivity extends AppCompatActivity implements View.OnClickListener, WebServicesCallBack, TimePickerDialog.OnTimeSetListener{

    private final static String TRAIN_ARRIVAL_API="train_arrival_api";
    private final static String STATION_AUTO_COMPLETE="station_autocomplete";
    private final String TAG=getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_station_code)
    AutoCompleteTextView et_station_code;
    @BindView(R.id.et_hour)
    EditText et_hour;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    DBHelper dbHelper;
    GetData getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_arrival_at_station);
        ButterKnife.bind(this);

        dbHelper=new DBHelper(this);
        getData=new GetData(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btn_search.setOnClickListener(this);
//        copydatabase();
        addTextWatcher();
    }

    public void callAutoStationCompleteAPI(){

        List<TrainAutoResultPOJO> list_trains=getData.getTrainList(et_station_code.getText().toString());
        List<String> lis_string=new ArrayList<>();
        for(TrainAutoResultPOJO trainAutoResultPOJO:list_trains){
            lis_string.add(trainAutoResultPOJO.getFullName());
        }
        Log.d(TAG,lis_string.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lis_string);
        et_station_code.setAdapter(adapter);


    }

    public void addTextWatcher(){
        et_station_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_station_code.getText().toString().length()>0){
                    getSourceStationList();
                }
            }
        });
    }

    List<String> list_source_string=new ArrayList<>();
    List<StationAutocompletePOJO> list_source_stations;
    public void getSourceStationList(){
        list_source_stations=getData.getStationList(et_station_code.getText().toString());
        list_source_string.clear();
        for(StationAutocompletePOJO stationAutocompletePOJO:list_source_stations){
            list_source_string.add(stationAutocompletePOJO.getStation());
        }
        Log.d(TAG,list_source_string.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_source_string);
        et_station_code.setAdapter(adapter);
    }
    public String getSourceStationCode(){
        String stn_code=et_station_code.getText().toString();
        try {
            if (list_source_string.contains(et_station_code.getText().toString())) {
                int index = list_source_string.indexOf(et_station_code.getText().toString());
                stn_code=list_source_stations.get(index).getCode();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stn_code;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_search:
                callTrainAPI();
                break;
            case tv_date:
                callDateDialog();
                break;
        }
    }

    public void callTrainAPI() {
        if (et_station_code.getText().toString().length() > 0 && et_hour.getText().toString().length() > 0) {
            Log.d(TAG,"tex:-"+list_of_stations_string.indexOf(et_station_code.getText().toString()));
            String url = WebUrls.getTrainArrivalsURL(getSourceStationCode(),
                    et_hour.getText().toString() );
            Log.d(TAG, "url:-" + url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, TRAIN_ARRIVAL_API).execute(url);
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Information Correctly", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case TRAIN_ARRIVAL_API:
                parseTrainFairResponse(response);
                break;
            case STATION_AUTO_COMPLETE:
                parseStationAutocomplete(response);
                break;
        }
    }
    List<String> list_of_stations_string=new ArrayList<>();
    List<StationAutocompletePOJO> list_of_stations=new ArrayList<>();
    public void parseStationAutocomplete(String response){
        Log.d(TAG,"station autocomplete:-"+response);
        try{

            Gson gson=new Gson();
            AutoCompleteStationPOJO autoCompleteStationPOJO=gson.fromJson(response,AutoCompleteStationPOJO.class);
            if(autoCompleteStationPOJO.getResponse_code()==200){
                list_of_stations_string.clear();
                list_of_stations.clear();
                List<StationAutocompletePOJO> stationAutocompletePOJOList=autoCompleteStationPOJO.getStationAutocompletePOJOList();
                list_of_stations.addAll(stationAutocompletePOJOList);
                for(StationAutocompletePOJO stationAutocompletePOJO:stationAutocompletePOJOList){
                    list_of_stations_string.add(stationAutocompletePOJO.getStation());
                    StationAutocompletePOJO stationAutocompletePOJO1=dbHelper.getStationbyStationName(stationAutocompletePOJO.getFullname(),stationAutocompletePOJO.getCode());
                    if(stationAutocompletePOJO1!=null&&stationAutocompletePOJO1.getFullname().equals(stationAutocompletePOJO.getFullname())){

                    }else{
                        dbHelper.insertstation(stationAutocompletePOJO);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_of_stations_string);
                et_station_code.setAdapter(adapter);
            }
            else{
                ToastClass.showLongToast(getApplicationContext(),"Server is temporary down");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();

    }
    public void parseTrainFairResponse(String response){
        Log.d(TAG,"response:-"+response);
        Gson gson=new Gson();
        try{
            TrainArrivalPOJO trainArrivalPOJO=gson.fromJson(response,TrainArrivalPOJO.class);
            if(trainArrivalPOJO.getResponse_code().equals("200")){
                inflateTrainArrival(trainArrivalPOJO.getList_trains());
            }
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }



    public void inflateTrainArrival(List<TrainPOJO> list_trains) {
        for (int i = 0; i < list_trains.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_cancelled_trains, null);
            TextView tv_train_name = (TextView) view.findViewById(R.id.tv_train_name);
            TextView tv_source_stn = (TextView) view.findViewById(R.id.tv_source_stn);
            TextView tv_dest_stn = (TextView) view.findViewById(R.id.tv_dest_stn);

            tv_train_name.setText(list_trains.get(i).getName()+"("+
                    list_trains.get(i).getNumber()+")");

            tv_source_stn.setText(list_trains.get(i).getSchdep());

            tv_dest_stn.setText(list_trains.get(i).getScharr());

            ll_scroll.addView(view);
        }
    }


    public void callDateDialog() {
//        Calendar now = Calendar.getInstance();
//        TimePickerDialog dpd = TimePickerDialog.newInstance(
//                TrainArrivalAtStationActivity.this,
//                now.get(Calendar.HOUR),
//                now.get(Calendar.MINUTE)
//        );
//        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//        tv_hour.setText(hourOfDay+":"+minute+":"+second);
    }

    public void copydatabase(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.sundroid.traininfo" +"/databases/"+"traindb";
        String backupDBPath = "traindb";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
