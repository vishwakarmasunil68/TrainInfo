package com.sundroid.traininfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.pojo.trainbetween.TrainBetweenStationPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrainBetweenStationActivity extends AppCompatActivity implements WebServicesCallBack,View.OnClickListener,DatePickerDialog.OnDateSetListener{
    private final static String TRAIN_BETWEEN_STATION="train_between_station";
    private final String TAG=getClass().getSimpleName();


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_source_station)
    EditText et_source_station;
    @BindView(R.id.et_dest_station)
    EditText et_dest_station;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.btn_search)
    Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_station);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_date.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_search:
//                callAPI();
                parseTrainBetweenStationResponse(trainapiresponse);
                break;
            case R.id.tv_date:
                callDateDialog();
                break;
        }
    }
    public void callDateDialog(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                TrainBetweenStationActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void callAPI(){
        if(et_source_station.getText().toString().length()>0&&et_dest_station.getText().toString().length()>0
                &&tv_date.getText().toString().length()>0) {
            String url = WebUrls.getTrainBetweenStationURL(et_source_station.getText().toString(),
                    et_dest_station.getText().toString(),tv_date.getText().toString());
            Log.d(TAG,"url:-"+url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, TRAIN_BETWEEN_STATION).execute(url);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please Enter Information Correctly",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case TRAIN_BETWEEN_STATION:
//                Log.d(TAG,"response:-"+response);
                parseTrainBetweenStationResponse(response);
                break;
        }
    }
    public void parseTrainBetweenStationResponse(String response){
        Gson gson=new Gson();
        TrainBetweenStationPOJO pojo=gson.fromJson(response,TrainBetweenStationPOJO.class);
        if(pojo!=null){
            try{
                if(pojo.getResponse_code().equals("200")){
                    Log.d(TAG,"TrainPOJO Between response:-"+pojo.toString());
                    Intent intent=new Intent(TrainBetweenStationActivity.this,TrainBetweenStationListActivity.class);
                    intent.putExtra("trainbetweenlist",pojo);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"No Response",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Log.d(TAG,"error:-"+e.toString());
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tv_date.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
    }

    String trainapiresponse="{\n" +
            "response_code: 200,\n" +
            "train: [\n" +
            "{\n" +
            "no: 1,\n" +
            "number: \"12312\",\n" +
            "name: \"KLK DLI HWH MAIL\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"14:20\",\n" +
            "from: {\n" +
            "code: \"ANDI\",\n" +
            "name: \"NEW ADRESHNAGARH\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"05:11\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"09:09\"\n" +
            "},\n" +
            "{\n" +
            "no: 2,\n" +
            "number: \"12004\",\n" +
            "name: \"NDLS-LKO SHATABDI EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"11:20\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"06:10\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"05:10\"\n" +
            "},\n" +
            "{\n" +
            "no: 3,\n" +
            "number: \"12816\",\n" +
            "name: \"NANDAN KANAN S/F (ADRA)\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"12:20\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"06:25\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"05:55\"\n" +
            "},\n" +
            "{\n" +
            "no: 4,\n" +
            "number: \"15484\",\n" +
            "name: \"SIKKIM MAHANANDA EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"15:12\",\n" +
            "from: {\n" +
            "code: \"DLI\",\n" +
            "name: \"DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"06:35\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"08:37\"\n" +
            "},\n" +
            "{\n" +
            "no: 5,\n" +
            "number: \"12506\",\n" +
            "name: \"NORTHEAST EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"12:50\",\n" +
            "from: {\n" +
            "code: \"ANVT\",\n" +
            "name: \"ANAND VIHAR TRM\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"06:45\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:05\"\n" +
            "},\n" +
            "{\n" +
            "no: 6,\n" +
            "number: \"12826\",\n" +
            "name: \"NDLS RNC SAMPARK KRANTI\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"13:15\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"06:55\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:20\"\n" +
            "},\n" +
            "{\n" +
            "no: 7,\n" +
            "number: \"13008\",\n" +
            "name: \"U A TOOFAN EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"18:45\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"07:00\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"11:45\"\n" +
            "},\n" +
            "{\n" +
            "no: 8,\n" +
            "number: \"12488\",\n" +
            "name: \"ANVT-JBNSUPERFAST EXPRES\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"13:45\",\n" +
            "from: {\n" +
            "code: \"ANVT\",\n" +
            "name: \"ANAND VIHAR TRM\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"07:30\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:15\"\n" +
            "},\n" +
            "{\n" +
            "no: 9,\n" +
            "number: \"12398\",\n" +
            "name: \"MAHABODHI EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"17:35\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"12:10\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"05:25\"\n" +
            "},\n" +
            "{\n" +
            "no: 10,\n" +
            "number: \"12420\",\n" +
            "name: \"GOMTI EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"19:50\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"12:25\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"07:25\"\n" +
            "},\n" +
            "{\n" +
            "no: 11,\n" +
            "number: \"12274\",\n" +
            "name: \"HWH DURONTO EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"17:48\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"12:55\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"04:53\"\n" +
            "},\n" +
            "{\n" +
            "no: 12,\n" +
            "number: \"12566\",\n" +
            "name: \"BIHAR SAMPARK KRANTI EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"20:15\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"14:15\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:00\"\n" +
            "},\n" +
            "{\n" +
            "no: 13,\n" +
            "number: \"12368\",\n" +
            "name: \"VIKRAMSHILA EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"20:25\",\n" +
            "from: {\n" +
            "code: \"ANVT\",\n" +
            "name: \"ANAND VIHAR TRM\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"14:40\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"05:45\"\n" +
            "},\n" +
            "{\n" +
            "no: 14,\n" +
            "number: \"15708\",\n" +
            "name: \"ASR-KIR EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"22:50\",\n" +
            "from: {\n" +
            "code: \"SZM\",\n" +
            "name: \"SUBZI MANDI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"14:42\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"08:08\"\n" +
            "},\n" +
            "{\n" +
            "no: 15,\n" +
            "number: \"14006\",\n" +
            "name: \"LICHCHIVI EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"23:25\",\n" +
            "from: {\n" +
            "code: \"ANVT\",\n" +
            "name: \"ANAND VIHAR TRM\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"15:10\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"08:15\"\n" +
            "},\n" +
            "{\n" +
            "no: 16,\n" +
            "number: \"12034\",\n" +
            "name: \"NDLS CNB SHATABDI\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"20:45\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"15:50\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"04:55\"\n" +
            "},\n" +
            "{\n" +
            "no: 17,\n" +
            "number: \"12454\",\n" +
            "name: \"NDLS-RNC\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"20:55\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"16:00\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"04:55\"\n" +
            "},\n" +
            "{\n" +
            "no: 18,\n" +
            "number: \"12424\",\n" +
            "name: \"NDLS-DBRT RAJDHANI\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"21:05\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"16:10\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"04:55\"\n" +
            "},\n" +
            "{\n" +
            "no: 19,\n" +
            "number: \"12314\",\n" +
            "name: \"SDAH RAJDHANI EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"21:15\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"16:25\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"04:50\"\n" +
            "},\n" +
            "{\n" +
            "no: 20,\n" +
            "number: \"12302\",\n" +
            "name: \"KOLKATA RAJDHANI EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"21:35\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"16:55\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"04:40\"\n" +
            "},\n" +
            "{\n" +
            "no: 21,\n" +
            "number: \"12570\",\n" +
            "name: \"ANVT-JYG GARIBRATH EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"22:12\",\n" +
            "from: {\n" +
            "code: \"ANVT\",\n" +
            "name: \"ANAND VIHAR TRM\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"16:55\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"05:17\"\n" +
            "},\n" +
            "{\n" +
            "no: 22,\n" +
            "number: \"22824\",\n" +
            "name: \"NDLS-BBS RAJDHANI\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"21:45\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"17:05\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"04:40\"\n" +
            "},\n" +
            "{\n" +
            "no: 23,\n" +
            "number: \"12310\",\n" +
            "name: \"PATNA RAJDHANI EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"21:55\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"17:15\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"04:40\"\n" +
            "},\n" +
            "{\n" +
            "no: 24,\n" +
            "number: \"12394\",\n" +
            "name: \"SAMPOORNKRANTI EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"22:25\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"17:25\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"05:00\"\n" +
            "},\n" +
            "{\n" +
            "no: 25,\n" +
            "number: \"12304\",\n" +
            "name: \"POORVA EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"23:00\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"17:35\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"05:25\"\n" +
            "},\n" +
            "{\n" +
            "no: 26,\n" +
            "number: \"12560\",\n" +
            "name: \"SHIVGANGA\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"01:00\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"18:55\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:05\"\n" +
            "},\n" +
            "{\n" +
            "no: 27,\n" +
            "number: \"22410\",\n" +
            "name: \"ANVT-GAYA GARIB RATH\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"01:25\",\n" +
            "from: {\n" +
            "code: \"ANVT\",\n" +
            "name: \"ANAND VIHAR TRM\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"19:10\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:15\"\n" +
            "},\n" +
            "{\n" +
            "no: 28,\n" +
            "number: \"12226\",\n" +
            "name: \"KAIFIAT EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"01:35\",\n" +
            "from: {\n" +
            "code: \"DLI\",\n" +
            "name: \"DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"19:15\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:20\"\n" +
            "},\n" +
            "{\n" +
            "no: 29,\n" +
            "number: \"12818\",\n" +
            "name: \"ANVT HTE EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"01:45\",\n" +
            "from: {\n" +
            "code: \"ANVT\",\n" +
            "name: \"ANAND VIHAR TRM\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"19:45\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:00\"\n" +
            "},\n" +
            "{\n" +
            "no: 30,\n" +
            "number: \"12554\",\n" +
            "name: \"VAISHALI EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"02:20\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"19:50\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:30\"\n" +
            "},\n" +
            "{\n" +
            "no: 31,\n" +
            "number: \"12402\",\n" +
            "name: \"MAGADH EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"02:30\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"20:00\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:30\"\n" +
            "},\n" +
            "{\n" +
            "no: 32,\n" +
            "number: \"12556\",\n" +
            "name: \"GORAKHDHAM EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"03:10\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"20:25\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:45\"\n" +
            "},\n" +
            "{\n" +
            "no: 33,\n" +
            "number: \"12562\",\n" +
            "name: \"SWATANTRTA SENANI  EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"02:40\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"20:35\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:05\"\n" +
            "},\n" +
            "{\n" +
            "no: 34,\n" +
            "number: \"14218\",\n" +
            "name: \"UNCHAHAR EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"05:05\",\n" +
            "from: {\n" +
            "code: \"SZM\",\n" +
            "name: \"SUBZI MANDI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"20:48\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"08:17\"\n" +
            "},\n" +
            "{\n" +
            "no: 35,\n" +
            "number: \"12428\",\n" +
            "name: \"ANVT-REWA EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"03:15\",\n" +
            "from: {\n" +
            "code: \"ANVT\",\n" +
            "name: \"ANAND VIHAR TRM\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"21:15\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:00\"\n" +
            "},\n" +
            "{\n" +
            "no: 36,\n" +
            "number: \"12418\",\n" +
            "name: \"PRAYAG RAJ EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"03:30\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"21:20\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:10\"\n" +
            "},\n" +
            "{\n" +
            "no: 37,\n" +
            "number: \"13414\",\n" +
            "name: \"FARKKA EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"N\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"05:30\",\n" +
            "from: {\n" +
            "code: \"DLI\",\n" +
            "name: \"DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"21:40\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"07:50\"\n" +
            "},\n" +
            "{\n" +
            "no: 38,\n" +
            "number: \"14724\",\n" +
            "name: \"KALINDI EXPRESS\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"11:10\",\n" +
            "from: {\n" +
            "code: \"DLI\",\n" +
            "name: \"DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"21:50\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"13:20\"\n" +
            "},\n" +
            "{\n" +
            "no: 39,\n" +
            "number: \"12802\",\n" +
            "name: \"NDLS-PURI PURSHOTTAM\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"03:55\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"22:15\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"05:40\"\n" +
            "},\n" +
            "{\n" +
            "no: 40,\n" +
            "number: \"12582\",\n" +
            "name: \"NDLS-MUV-SUPERFAST-EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"04:45\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"22:25\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:20\"\n" +
            "},\n" +
            "{\n" +
            "no: 41,\n" +
            "number: \"14056\",\n" +
            "name: \"BRAHMPUTRA MAIL\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"05:50\",\n" +
            "from: {\n" +
            "code: \"DLI\",\n" +
            "name: \"DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"23:40\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"N\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:10\"\n" +
            "},\n" +
            "{\n" +
            "no: 42,\n" +
            "number: \"12452\",\n" +
            "name: \"SHRAM SHKTI EXP\",\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"MON\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"TUE\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"WED\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"THU\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"FRI\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SAT\"\n" +
            "},\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "}\n" +
            "],\n" +
            "dest_arrival_time: \"06:30\",\n" +
            "from: {\n" +
            "code: \"NDLS\",\n" +
            "name: \"NEW DELHI\"\n" +
            "},\n" +
            "to: {\n" +
            "code: \"CNB\",\n" +
            "name: \"KANPUR CENTRAL\"\n" +
            "},\n" +
            "src_departure_time: \"23:55\",\n" +
            "classes: [\n" +
            "{\n" +
            "class-code: \"SL\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3E\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"FC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"2S\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"CC\",\n" +
            "available: \"N\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"3A\",\n" +
            "available: \"Y\"\n" +
            "},\n" +
            "{\n" +
            "class-code: \"1A\",\n" +
            "available: \"Y\"\n" +
            "}\n" +
            "],\n" +
            "travel_time: \"06:35\"\n" +
            "}\n" +
            "],\n" +
            "total: 42,\n" +
            "error: \"\"\n" +
            "}";
}
