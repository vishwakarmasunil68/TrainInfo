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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.pojo.pnrstatus.PNRResponsePOJO;
import com.sundroid.traininfo.pojo.pnrstatus.PassengersPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PNRStatusActivity extends AppCompatActivity implements WebServicesCallBack,View.OnClickListener {
    private final static String PNR_CALL_API="pnr_call_api";
    private final String TAG=getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_click)
    Button btn_click;
    @BindView(R.id.et_pnr_text)
    EditText et_pnr_text;
    @BindView(R.id.iv_clear_et)
    ImageView iv_clear_et;
    @BindView(R.id.tv_pnr_number)
    TextView tv_pnr_number;
    @BindView(R.id.tv_train_name)
    TextView tv_train_name;
    @BindView(R.id.tv_from_to)
    TextView tv_from_to;
    @BindView(R.id.tv_boarding_date)
    TextView tv_boarding_date;
    @BindView(R.id.tv_chart_prepared)
    TextView tv_chart_prepared;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.ll_pnr_status)
    LinearLayout ll_pnr_status;
    @BindView(R.id.ll_scroll_view)
    LinearLayout ll_scroll_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnrstatus);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_click.setOnClickListener(this);
        iv_clear_et.setOnClickListener(this);
        ll_pnr_status.setVisibility(View.GONE);
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
            case R.id.btn_click:
                    callAPI();
                break;
            case R.id.iv_clear_et:
                et_pnr_text.setText("");
                break;
        }
    }

    public void callAPI(){
        if(et_pnr_text.getText().toString().length()>0) {
            String url = WebUrls.getPNRStatusURL(et_pnr_text.getText().toString());
            Log.d(TAG,"url:-"+url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, PNR_CALL_API).execute(url);
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
            case PNR_CALL_API:
//                    Log.d(TAG,"response:-"+response);
                parsePNRResponse(response);
                break;
        }
    }

    public void parsePNRResponse(String response){
        Gson gson=new Gson();
        PNRResponsePOJO pojo=gson.fromJson(response,PNRResponsePOJO.class);
        if(pojo!=null){
            try{
                if(pojo.getResponse_code().equals("200")){
                    Log.d(TAG,"pnr status response:-"+pojo.toString());


                    tv_pnr_number.setText(pojo.getPnr());
                    tv_train_name.setText(pojo.getTrain_name());
                    tv_from_to.setText(pojo.getPnboarding_pointr().getName()+"("+pojo.getPnboarding_pointr().getCode()+")-"
                    +pojo.getReservation_upto().getName()+"("+pojo.getReservation_upto().getCode()+")");

                    tv_boarding_date.setText(pojo.getDoj());
                    if(pojo.getChart_prepared().equals("N")){
                        tv_chart_prepared.setText("Chart is Not Prepared");
                    }
                    else{
                        tv_chart_prepared.setText("Chart is Prepared");
                    }
                    tv_class.setText(pojo.getClass_pnr());
                    inflateLinearLayout(pojo.getList_passengers());
                    ll_pnr_status.setVisibility(View.VISIBLE);
                }else{
                    if(pojo.getResponse_code().equals("404")){
                        Toast.makeText(getApplicationContext(), "Service is Temporary Down", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e){
                Log.d(TAG,"error:-"+e.toString());
            }
        }
    }
    public void inflateLinearLayout(List<PassengersPOJO> list_passengers) {
        for (int i = 0; i < list_passengers.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_pnr_passengers, null);
            TextView tv_serial_no = (TextView) view.findViewById(R.id.tv_serial_no);
            TextView tv_booking_status = (TextView) view.findViewById(R.id.tv_booking_status);
            TextView tv_current_status = (TextView) view.findViewById(R.id.tv_current_status);

            tv_serial_no.setText(list_passengers.get(i).getNo());
            tv_booking_status.setText(list_passengers.get(i).getBooking_status());
            tv_current_status.setText(list_passengers.get(i).getCurrent_status());

            ll_scroll_view.addView(view);
        }
    }
}
