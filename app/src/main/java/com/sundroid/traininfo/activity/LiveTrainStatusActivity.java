package com.sundroid.traininfo.activity;

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

import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveTrainStatusActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,WebServicesCallBack{
    private final static String LIVE_TRAIN_API_CALL="live_train_api_call";
    private final String TAG=getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_train_number)
    EditText et_train_number;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.btn_search)
    Button btn_search;
    String string_date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status);
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
            case R.id.tv_date:
                selectDate();
                break;
            case R.id.btn_search:
                callAPI();
                break;
        }
    }
    public void callAPI(){
        if(et_train_number.getText().toString().length()>0&&string_date.length()>0) {
            String url = WebUrls.getLiveTrainURL(et_train_number.getText().toString(), string_date);
            string_date = "";
            Log.d(TAG,"url:-"+url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, LIVE_TRAIN_API_CALL).execute(url);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please Enter Information Correctly",Toast.LENGTH_LONG).show();
        }
    }
    public void selectDate(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                LiveTrainStatusActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month=monthOfYear+1;
        if(String.valueOf(month).length()==1){
            string_date=String.valueOf(year)+"0"+String.valueOf(month)+String.valueOf(dayOfMonth);
        }
        else{
            string_date=String.valueOf(year)+String.valueOf(month)+String.valueOf(dayOfMonth);
        }

        tv_date.setText(dayOfMonth+"/"+month+"/"+year);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case LIVE_TRAIN_API_CALL:
                Log.d(TAG,"response:-"+response);
                break;
        }
    }

}
