package com.sundroid.traininfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.StringUtils;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.pojo.seatavailability.SeatAvailabilityPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatAvailability extends AppCompatActivity implements View.OnClickListener
        ,WebServicesCallBack,DatePickerDialog.OnDateSetListener{

    private final String TAG=getClass().getSimpleName();
    private final static String SEAT_AVAILABILITY_API="seat_availability_api";

    @BindView(R.id.et_train_no)
    EditText et_train_no;
    @BindView(R.id.et_source_code)
    EditText et_source_code;
    @BindView(R.id.et_destination)
    EditText et_destination;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.spinner_quota)
    Spinner spinner_quota;
    @BindView(R.id.spinner_seat)
    Spinner spinner_seat;
    @BindView(R.id.btn_search)
    Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_availability);
        ButterKnife.bind(this);
        tv_date.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        tv_date.setText(sdf.format(d));

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_search:
//                callseatAvailabilityAPI();
                parseSeatAvailabilityAPI(apiresponse);
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
        String train_no=et_train_no.getText().toString();
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
            String url = WebUrls.getSeatAvailabilityURL(
                    train_no,
                    source_station,
                    destination_station,
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
        SeatAvailabilityPOJO seatAvailability=gson.fromJson(response,SeatAvailabilityPOJO.class);
        try{
            if(seatAvailability!=null){
                if(seatAvailability.getResponse_code().equals("200")){
                    Log.d(TAG,seatAvailability.toString());
                    Intent intent=new Intent(SeatAvailability.this,SeatAvailabilityResultAcitivity.class);
                    intent.putExtra("seatavailabilitypojo",seatAvailability);
                    startActivity(intent);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date=dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        tv_date.setText(date);
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
