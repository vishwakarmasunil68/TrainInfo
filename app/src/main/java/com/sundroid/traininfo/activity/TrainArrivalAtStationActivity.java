package com.sundroid.traininfo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.pojo.trainarrivalatstation.TrainArrivalPOJO;
import com.sundroid.traininfo.pojo.trainarrivalatstation.TrainPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sundroid.traininfo.R.id.tv_date;

public class TrainArrivalAtStationActivity extends AppCompatActivity implements View.OnClickListener, WebServicesCallBack, TimePickerDialog.OnTimeSetListener{

    private final static String TRAIN_ARRIVAL_API="train_arrival_api";
    private final String TAG=getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_station_code)
    EditText et_station_code;
    @BindView(R.id.et_hour)
    EditText et_hour;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_arrival_at_station);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btn_search.setOnClickListener(this);
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
            String url = WebUrls.getTrainArrivalsURL(et_station_code.getText().toString(),
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
        }
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
}
