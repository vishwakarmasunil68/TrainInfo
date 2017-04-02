package com.sundroid.traininfo.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.StringUtils;
import com.sundroid.traininfo.Utils.ToastClass;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.database.DBHelper;
import com.sundroid.traininfo.database.GetData;
import com.sundroid.traininfo.pojo.autocompletestation.StationAutocompletePOJO;
import com.sundroid.traininfo.pojo.trainautocomplete.TrainAutoPOJO;
import com.sundroid.traininfo.pojo.trainautocomplete.TrainAutoResultPOJO;
import com.sundroid.traininfo.pojo.trainfareenquiry.FarePOJO;
import com.sundroid.traininfo.pojo.trainfareenquiry.TrainFareEnquiryPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainFareEnquiryActivity extends AppCompatActivity implements View.OnClickListener, WebServicesCallBack, DatePickerDialog.OnDateSetListener {
    private final String TAG = getClass().getSimpleName();
    private final static String TRAIN_FAIR_API="train_fair_api";
    private final static String TRAIN_AUTO_COMPLETE="train_auto_complete";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_train_number)
    AutoCompleteTextView et_train_number;
    @BindView(R.id.et_source_station)
    AutoCompleteTextView et_source_station;
    @BindView(R.id.et_destination_station)
    AutoCompleteTextView et_destination_station;
    @BindView(R.id.et_age)
    EditText et_age;
    @BindView(R.id.spinner_quota)
    Spinner spinner_quota;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_train_name)
    TextView tv_train_name;
    @BindView(R.id.tv_from_to)
    TextView tv_from_to;
    @BindView(R.id.tv_quota)
    TextView tv_quota;
    @BindView(R.id.ll_fare_scroll)
    LinearLayout ll_fare_scroll;

    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.btn_live_train_status)
    Button btn_live_train_status;
    @BindView(R.id.btn_train_route)
    Button btn_train_route;


    DBHelper dbHelper;
    GetData getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_fare_enquiry);
        ButterKnife.bind(this);
        getData=new GetData(this);
        dbHelper=new DBHelper(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tv_date.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_train_route.setOnClickListener(this);
        btn_live_train_status.setOnClickListener(this);

        addTextWatchers();
    }


    public String getTrainNumber(){
        String train_no=et_train_number.getText().toString();
        try {
            if (list_trains_string.contains(et_train_number.getText().toString())) {
                int index = list_trains_string.indexOf(et_train_number.getText().toString());
                train_no=list_trains.get(index).getNumber();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return train_no;
    }

    public String getSourceStationCode(){
        String stn_code=et_source_station.getText().toString();
        try {
            if (list_source_string.contains(et_source_station.getText().toString())) {
                int index = list_source_string.indexOf(et_source_station.getText().toString());
                stn_code=list_source_stations.get(index).getCode();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stn_code;
    }

    public String getDestinationStationCode(){
        String stn_code=et_destination_station.getText().toString();
        try {
            if (list_destination_string.contains(et_destination_station.getText().toString())) {
                int index = list_destination_string.indexOf(et_destination_station.getText().toString());
                stn_code=list_dest_stations.get(index).getCode();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stn_code;
    }

    public void callTrainNameAPI(){
        String url = WebUrls.getTrainAutoCompleteSuggestURL(et_train_number.getText().toString());
        Log.d(TAG, "url:-" + url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        new WebServiceBase(nameValuePairs, this, TRAIN_AUTO_COMPLETE).execute(url);
    }
    public void addTextWatchers(){
        et_train_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_train_number.getText().toString().length()>0){
                    getTrainList();
                }
            }
        });

        et_source_station.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_source_station.getText().toString().length()>0){
                    getSourceStationList();
                }
            }
        });

        et_destination_station.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_destination_station.getText().toString().length()>0){
                    getDestinationStationList();
                }
            }
        });
    }
    List<String> list_trains_string=new ArrayList<>();
    List<TrainAutoResultPOJO> list_trains;
    public void getTrainList(){
        list_trains=getData.getTrainList(et_train_number.getText().toString());
        list_trains_string.clear();
        for(TrainAutoResultPOJO trainAutoResultPOJO:list_trains){
            list_trains_string.add(trainAutoResultPOJO.getFullName());
        }
        Log.d(TAG,list_trains_string.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_trains_string);
        et_train_number.setAdapter(adapter);
    }

    List<String> list_source_string=new ArrayList<>();
    List<StationAutocompletePOJO> list_source_stations;
    public void getSourceStationList(){
        list_source_stations=getData.getStationList(et_source_station.getText().toString());
        list_source_string.clear();
        for(StationAutocompletePOJO stationAutocompletePOJO:list_source_stations){
            list_source_string.add(stationAutocompletePOJO.getStation());
        }
        Log.d(TAG,list_source_string.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_source_string);
        et_source_station.setAdapter(adapter);
    }

    List<String> list_destination_string=new ArrayList<>();
    List<StationAutocompletePOJO> list_dest_stations;
    public void getDestinationStationList(){
        list_dest_stations=getData.getStationList(et_destination_station.getText().toString());
        list_destination_string.clear();
        for(StationAutocompletePOJO stationAutocompletePOJO:list_dest_stations){
            list_destination_string.add(stationAutocompletePOJO.getStation());
        }
        Log.d(TAG,list_destination_string.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_destination_string);
        et_destination_station.setAdapter(adapter);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_search:
                train_number="";
                callTrainAPI();
                break;
            case R.id.tv_date:
                callDateDialog();
                break;
            case R.id.btn_live_train_status:
                startLiveTrainActivity();
                break;
            case R.id.btn_train_route:
                startTrainRouteActivity();
                break;

        }
    }

    public void startLiveTrainActivity(){
        if(train_number.length()>0) {
            Intent intent = new Intent(TrainFareEnquiryActivity.this, LiveTrainStatusActivity.class);
            intent.putExtra("train", train_number);
            startActivity(intent);
        }
        else{
            ToastClass.showLongToast(getApplicationContext(),"First Search Train");
        }
    }

    public void startTrainRouteActivity(){
        if(train_number.length()>0) {
            Intent intent = new Intent(TrainFareEnquiryActivity.this, TrainRouteActivity.class);
            intent.putExtra("trainno", train_number);
            startActivity(intent);
        }
        else{
            ToastClass.showLongToast(getApplicationContext(),"First Search Train");
        }
    }
    String train_number="";
    public void callTrainAPI() {
        if (et_source_station.getText().toString().length() > 0 && et_destination_station.getText().toString().length() > 0
                && et_age.getText().toString().length() > 0 && spinner_quota.getSelectedItem().toString().length() > 0 &&
                tv_date.getText().toString().length() > 0 && et_train_number.getText().toString().length() > 0) {
            String train_number=getTrainNumber();
            String source_station_code=getSourceStationCode();
            String dest_station_code=getDestinationStationCode();
            String url = WebUrls.getTrainFairURL(train_number,
                    source_station_code,
                    dest_station_code,
                    et_age.getText().toString(),
                    StringUtils.getQuotaCODE(spinner_quota.getSelectedItem().toString()),
                    tv_date.getText().toString());
            Log.d(TAG, "url:-" + url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, TRAIN_FAIR_API).execute(url);
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
            case TRAIN_FAIR_API:
                parseTrainFairResponse(response);
                break;
            case  TRAIN_AUTO_COMPLETE:
                parseTrainAutoCompleteResponse(response);
                break;
        }
    }

    public void parseTrainAutoCompleteResponse(String response){
        Log.d(TAG,"response:-"+response);
        try{
            Gson gson=new Gson();
            TrainAutoPOJO trainAutoPOJO=gson.fromJson(response,TrainAutoPOJO.class);
            if(trainAutoPOJO.getResponse_code()==200){
//                list_of_stations_string.clear();
//                list_of_stations.clear();
                List<TrainAutoResultPOJO> trainAutoResultPOJOs=trainAutoPOJO.getTrainAutoResultPOJOList();
//                list_of_stations.addAll(stationAutocompletePOJOList);
                for(TrainAutoResultPOJO trainAutoResultPOJO:trainAutoResultPOJOs){
//                    list_of_stations_string.add(stationAutocompletePOJO.getStation());
                    TrainAutoResultPOJO trainAutoResultPOJO1=dbHelper.getTrainbyTrainName(trainAutoResultPOJO.getName());
                    if(trainAutoResultPOJO1!=null&&trainAutoResultPOJO1.getFullName().equals(trainAutoResultPOJO.getFullName())){

                    }else{
                        dbHelper.insertTrain(trainAutoResultPOJO);
                    }
                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_of_stations_string);
//                et_station_code.setAdapter(adapter);
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
//        Log.d(TAG,"response:-"+response);
        Gson gson=new Gson();
        TrainFareEnquiryPOJO pojo=gson.fromJson(response,TrainFareEnquiryPOJO.class);
        if(pojo!=null){
            try{
                if(pojo.getResponse_code().equals("200")){
                    Log.d(TAG,"TrainPOJO route response:-"+pojo.toString());

                    tv_train_name.setText(pojo.getTrain_fare_pojo().getName()+"("
                    +pojo.getTrain_fare_pojo().getNumber()+")");
                    train_number=pojo.getTrain_fare_pojo().getNumber();
                    tv_from_to.setText(pojo.getFromPOJO().getName()+"("+
                    pojo.getFromPOJO().getCode()+") to "+pojo.getToPOJO().getName()+"("+
                    pojo.getToPOJO().getCode()+")");

                    tv_quota.setText("Quota:-"+pojo.getQuota_pojo().getName()+"("+
                    pojo.getQuota_pojo().getCode()+")");

                    inflateLinearList(pojo.getList_fare());
                }else{
                    Toast.makeText(getApplicationContext(),"No Response",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Log.d(TAG,"error:-"+e.toString());
            }
        }
    }

    public void inflateLinearList(List<FarePOJO> list_fare){
        for (int i = 0; i < list_fare.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_list_fares, null);
            TextView tv_class = (TextView) view.findViewById(R.id.tv_class);
            TextView tv_fare = (TextView) view.findViewById(R.id.tv_fare);

            tv_class.setText(list_fare.get(i).getName()+"("+list_fare.get(i).getCode()+")");
            tv_fare.setText(list_fare.get(i).getFare());

            ll_fare_scroll.addView(view);
        }
    }

    public void callDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                TrainFareEnquiryActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
