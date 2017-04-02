package com.sundroid.traininfo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.StringUtils;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.database.GetData;
import com.sundroid.traininfo.pojo.autocompletestation.StationAutocompletePOJO;
import com.sundroid.traininfo.pojo.seatavailability.AvailabilityPOJO;
import com.sundroid.traininfo.pojo.seatavailability.SeatAvailabilityPOJO;
import com.sundroid.traininfo.pojo.trainautocomplete.TrainAutoResultPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatAvailability extends AppCompatActivity implements View.OnClickListener
        ,WebServicesCallBack,DatePickerDialog.OnDateSetListener{

    private final String TAG=getClass().getSimpleName();
    private final static String SEAT_AVAILABILITY_API="seat_availability_api";

    @BindView(R.id.et_train_no)
    AutoCompleteTextView et_train_no;
    @BindView(R.id.et_source_code)
    AutoCompleteTextView et_source_code;
    @BindView(R.id.et_destination)
    AutoCompleteTextView et_destination;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.spinner_quota)
    Spinner spinner_quota;
    @BindView(R.id.spinner_seat)
    Spinner spinner_seat;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.tv_train_name)
    TextView tv_train_name;
    @BindView(R.id.tv_from_to)
    TextView tv_from_to;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.tv_date_train)
    TextView tv_date_train;
    @BindView(R.id.ll_availability_scroll)
    LinearLayout ll_availability_scroll;
    @BindView(R.id.ll_result)
    LinearLayout ll_result;
    GetData getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_availability);
        ButterKnife.bind(this);

        getData=new GetData(this);

        tv_date.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        tv_date.setText(sdf.format(d));

        addTextWatchers();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_search:
                callseatAvailabilityAPI();
//                parseSeatAvailabilityAPI(apiresponse);
                break;
            case R.id.tv_date:
                OpenCalendar();
                break;
        }
    }
    public void OpenCalendar(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SeatAvailability.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    public void callseatAvailabilityAPI(){
        String source_station=et_source_code.getText().toString();
        String destination_station=et_destination.getText().toString();
        String date=tv_date.getText().toString();
        String quota=spinner_quota.getSelectedItem().toString();
        String seat_class=spinner_seat.getSelectedItem().toString();
        String quota_code= StringUtils.getQuotaCODE(quota);
        String seat_class_code=StringUtils.getSeatClassCODE(seat_class);
        if (et_source_code.getText().toString().length() > 0 && et_destination.getText().toString().length() > 0
                && et_train_no.getText().toString().length() > 0 && tv_date.getText().toString().length() > 0 &&
                spinner_quota.getSelectedItem().toString().length() > 0 && spinner_seat.getSelectedItem().toString().length() > 0) {

            String train_no=getTrainNumber();
            String source_code=getSourceStationCode();
            String dest_code=getDestinationStationCode();
            String url = WebUrls.getSeatAvailabilityURL(
                    train_no,
                    source_code,
                    dest_code,
                    date,
                    seat_class_code,
                    quota_code);
            Log.d(TAG, "url:-" + url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, SEAT_AVAILABILITY_API).execute(url);
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Information Correctly", Toast.LENGTH_LONG).show();
        }
    }
    public String getTrainNumber(){
        String train_no=et_train_no.getText().toString();
        try {
            if (list_trains_string.contains(et_train_no.getText().toString())) {
                int index = list_trains_string.indexOf(et_train_no.getText().toString());
                train_no=list_trains.get(index).getNumber();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return train_no;
    }

    public String getSourceStationCode(){
        String stn_code=et_source_code.getText().toString();
        try {
            if (list_source_string.contains(et_source_code.getText().toString())) {
                int index = list_source_string.indexOf(et_source_code.getText().toString());
                stn_code=list_source_stations.get(index).getCode();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stn_code;
    }

    public String getDestinationStationCode(){
        String stn_code=et_destination.getText().toString();
        try {
            if (list_destination_string.contains(et_destination.getText().toString())) {
                int index = list_destination_string.indexOf(et_destination.getText().toString());
                stn_code=list_dest_stations.get(index).getCode();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stn_code;
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case SEAT_AVAILABILITY_API:
                parseSeatAvailabilityAPI(response);
                break;
        }
    }
    public void parseSeatAvailabilityAPI(String response){
        Log.d(TAG,"seat available:-"+response);
        Gson gson=new Gson();
        ll_result.setVisibility(View.GONE);
        SeatAvailabilityPOJO seatAvailabilityPOJO=gson.fromJson(response,SeatAvailabilityPOJO.class);
        try{
            if(seatAvailabilityPOJO!=null){
                if(seatAvailabilityPOJO.getResponse_code().equals("200")){
                    Log.d(TAG,seatAvailabilityPOJO.toString());
                    ll_result.setVisibility(View.VISIBLE);
//                    Intent intent=new Intent(SeatAvailability.this,SeatAvailabilityResultAcitivity.class);
//                    intent.putExtra("seatavailabilitypojo",seatAvailabilityPOJO);
//                    startActivity(intent);
                    if(seatAvailabilityPOJO!=null){
                        tv_train_name.setText(seatAvailabilityPOJO.getTrain_name());

                        tv_from_to.setText(seatAvailabilityPOJO.getFromPOJO().getName()+"("+
                                seatAvailabilityPOJO.getFromPOJO().getCode()+") to "+
                                seatAvailabilityPOJO.getToPOJO().getName()+"("+seatAvailabilityPOJO.getToPOJO().getCode()+")");

                        tv_date_train.setText(seatAvailabilityPOJO.getLastUpdatedPOJO().getDate()+" "+
                                seatAvailabilityPOJO.getLastUpdatedPOJO().getTime());

                        tv_class.setText(seatAvailabilityPOJO.getClassPOJO().getClass_name()+"("+
                                seatAvailabilityPOJO.getClassPOJO().getClass_code()+")");
                        List<AvailabilityPOJO> list_availability=seatAvailabilityPOJO.getList_availability();
                        inflateLinearList(list_availability);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
            Log.d(TAG,e.toString());
        }
    }

    public void inflateLinearList(List<AvailabilityPOJO> list_availability){
        for (int i = 0; i < list_availability.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_seat_availability_result, null);
            TextView tv_availability = (TextView) view.findViewById(R.id.tv_availability);
            TextView tv_inflate_date = (TextView) view.findViewById(R.id.tv_inflate_date);
            LinearLayout ll_available = (LinearLayout) view.findViewById(R.id.ll_available);

            tv_availability.setText(list_availability.get(i).getStatus());
            tv_inflate_date.setText(list_availability.get(i).getDate());

            ll_availability_scroll.addView(view);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date=dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        tv_date.setText(date);
    }

    public void addTextWatchers(){
        et_train_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_train_no.getText().toString().length()>0){
                    getTrainList();
                }
            }
        });

        et_source_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_source_code.getText().toString().length()>0){
                    getSourceStationList();
                }
            }
        });

        et_destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_destination.getText().toString().length()>0){
                    getDestinationStationList();
                }
            }
        });
    }
    List<String> list_trains_string=new ArrayList<>();
    List<TrainAutoResultPOJO> list_trains;
    public void getTrainList(){
        list_trains=getData.getTrainList(et_train_no.getText().toString());
        list_trains_string.clear();
        for(TrainAutoResultPOJO trainAutoResultPOJO:list_trains){
            list_trains_string.add(trainAutoResultPOJO.getFullName());
        }
        Log.d(TAG,list_trains_string.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_trains_string);
        et_train_no.setAdapter(adapter);
    }

    List<String> list_source_string=new ArrayList<>();
    List<StationAutocompletePOJO> list_source_stations;
    public void getSourceStationList(){
        list_source_stations=getData.getStationList(et_source_code.getText().toString());
        list_source_string.clear();
        for(StationAutocompletePOJO stationAutocompletePOJO:list_source_stations){
            list_source_string.add(stationAutocompletePOJO.getStation());
        }
        Log.d(TAG,list_source_string.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_source_string);
        et_source_code.setAdapter(adapter);
    }

    List<String> list_destination_string=new ArrayList<>();
    List<StationAutocompletePOJO> list_dest_stations;
    public void getDestinationStationList(){
        list_dest_stations=getData.getStationList(et_destination.getText().toString());
        list_destination_string.clear();
        for(StationAutocompletePOJO stationAutocompletePOJO:list_dest_stations){
            list_destination_string.add(stationAutocompletePOJO.getStation());
        }
        Log.d(TAG,list_destination_string.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_destination_string);
        et_destination.setAdapter(adapter);
    }


    String apiresponse="{\n" +
            "from: {\n" +
            "name: \"DELHI\",\n" +
            "lat: 28.6609677,\n" +
            "lng: 77.2276704,\n" +
            "code: \"DLI\"\n" +
            "},\n" +
            "quota: {\n" +
            "quota_code: \"GN\",\n" +
            "quota_name: \"GENERAL QUOTA\"\n" +
            "},\n" +
            "response_code: 200,\n" +
            "train_name: \"KALKA MAIL\",\n" +
            "availability: [\n" +
            "{\n" +
            "status: \"RLWL145/WL86\",\n" +
            "date: \"28-2-2017\"\n" +
            "},\n" +
            "{\n" +
            "status: \"RLWL156/WL108\",\n" +
            "date: \"1-3-2017\"\n" +
            "},\n" +
            "{\n" +
            "status: \"RLWL158/WL112\",\n" +
            "date: \"2-3-2017\"\n" +
            "},\n" +
            "{\n" +
            "status: \"RLWL149/WL96\",\n" +
            "date: \"3-3-2017\"\n" +
            "},\n" +
            "{\n" +
            "status: \"RLWL132/WL115\",\n" +
            "date: \"4-3-2017\"\n" +
            "},\n" +
            "{\n" +
            "status: \"RLWL157/WL115\",\n" +
            "date: \"5-3-2017\"\n" +
            "}\n" +
            "],\n" +
            "to: {\n" +
            "name: \"KANPUR CENTRAL\",\n" +
            "lat: 28.8365997,\n" +
            "lng: 78.24845739999999,\n" +
            "code: \"CNB\"\n" +
            "},\n" +
            "class: {\n" +
            "class_name: \"SLEEPER CLASS\",\n" +
            "class_code: \"SL\"\n" +
            "},\n" +
            "train_number: \"12312\",\n" +
            "error: \"\",\n" +
            "failure_rate: 81.28342245989305,\n" +
            "last_updated: {\n" +
            "time: \"17:21\",\n" +
            "date: \"2017-02-25\"\n" +
            "}\n" +
            "}";
}
