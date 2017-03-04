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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.pojo.cancelledtrain.CancelledTrainPOJO;
import com.sundroid.traininfo.pojo.cancelledtrain.TrainsPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CancelledTrainActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener,
        WebServicesCallBack{
    private final String TAG=getClass().getSimpleName();
    private final String CALL_CANCELLED_TRAIN_API="call_cancelled_train_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.btn_find)
    Button btn_find;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_train);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cancelled Trains");

        tv_date.setOnClickListener(this);
        btn_find.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_date:
                openDateDialog();
                break;
            case R.id.btn_find:
                callCancelledTrainAPI();
                break;
        }
    }
    public void callCancelledTrainAPI(){
        if(tv_date.getText().toString().length()>0||tv_date.getText().toString().equals("select Date")){
            String url = WebUrls.getCancelledTrainsURL(tv_date.getText().toString());
            Log.d(TAG,"url:-"+url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, CALL_CANCELLED_TRAIN_API).execute(url);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please Enter Information Correctly",Toast.LENGTH_LONG).show();
        }
    }


    public void openDateDialog(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                CancelledTrainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tv_date.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case CALL_CANCELLED_TRAIN_API:
                parsecancelledTrainResponse(response);
                break;
        }
    }
    public void parsecancelledTrainResponse(String response){
        Gson gson=new Gson();
        try{
            CancelledTrainPOJO cancelledTrainPOJO=gson.fromJson(response,CancelledTrainPOJO.class);
            if(cancelledTrainPOJO.getResponse_code().equals("200")){
                Log.d(TAG,cancelledTrainPOJO.toString());
                inflateCancelledTrains(cancelledTrainPOJO.getList_trains());
            }
            else{
                Toast.makeText(getApplicationContext(),"Server Down",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
        }
    }


    public void inflateCancelledTrains(List<TrainsPOJO> list_trains) {
        for (int i = 0; i < list_trains.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_cancelled_trains, null);
            TextView tv_train_name = (TextView) view.findViewById(R.id.tv_train_name);
            TextView tv_source_stn = (TextView) view.findViewById(R.id.tv_source_stn);
            TextView tv_dest_stn = (TextView) view.findViewById(R.id.tv_dest_stn);

            tv_train_name.setText(list_trains.get(i).getTrain().getName()+"("+
                    list_trains.get(i).getTrain().getNumber()+")");

            tv_source_stn.setText(list_trains.get(i).getDestPOJO().getName()+"("+
                    list_trains.get(i).getDestPOJO().getCode()+")");

            tv_dest_stn.setText(list_trains.get(i).getSourcePOJO().getName()+"("+
                    list_trains.get(i).getSourcePOJO().getCode()+")");

            ll_scroll.addView(view);
        }
    }
}
