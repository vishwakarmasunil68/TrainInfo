package com.sundroid.traininfo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.StringUtils;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.pojo.trainroute.ClassesPOJO;
import com.sundroid.traininfo.pojo.trainroute.DaysPOJO;
import com.sundroid.traininfo.pojo.trainroute.TrainRoutePOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainRouteActivity extends AppCompatActivity implements View.OnClickListener, WebServicesCallBack {
    private final static String TRAIN_ROUTE_API_CALL = "pnr_call_api";
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_click)
    Button btn_click;
    @BindView(R.id.et_train_route)
    EditText et_train_route;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.iv_sun)
    ImageView iv_sun;
    @BindView(R.id.iv_mon)
    ImageView iv_mon;
    @BindView(R.id.iv_tue)
    ImageView iv_tue;
    @BindView(R.id.iv_wed)
    ImageView iv_wed;
    @BindView(R.id.iv_thu)
    ImageView iv_thu;
    @BindView(R.id.iv_fri)
    ImageView iv_fri;
    @BindView(R.id.iv_sat)
    ImageView iv_sat;
    @BindView(R.id.iv_cc)
    ImageView iv_cc;
    @BindView(R.id.iv_fc)
    ImageView iv_fc;
    @BindView(R.id.iv_1a)
    ImageView iv_1a;
    @BindView(R.id.iv_sl)
    ImageView iv_sl;
    @BindView(R.id.iv_2s)
    ImageView iv_2s;
    @BindView(R.id.iv_3a)
    ImageView iv_3a;
    @BindView(R.id.iv_2a)
    ImageView iv_2a;
    @BindView(R.id.iv_3e)
    ImageView iv_3e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_route);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_click.setOnClickListener(this);
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
        int id = view.getId();
        switch (id) {
            case R.id.btn_click:
                callAPI();
                break;
        }
    }

    public void callAPI() {
        if (et_train_route.getText().toString().length() > 0) {
            String url = WebUrls.getTrainRouteStatusURL(et_train_route.getText().toString());
            Log.d(TAG, "url:-" + url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, TRAIN_ROUTE_API_CALL).execute(url);
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Information Correctly", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String resposne = msg[1];
        switch (apicall) {
            case TRAIN_ROUTE_API_CALL:
//                Log.d(TAG,"response:-"+resposne);
                parseTrainRouteResponse(resposne);
                break;
        }
    }

    public void parseTrainRouteResponse(String response) {
        Gson gson = new Gson();
        TrainRoutePOJO pojo = gson.fromJson(response, TrainRoutePOJO.class);
        if (pojo != null) {
            try {
                if (pojo.getResponse_code().equals("200")) {
                    Log.d(TAG, "TrainPOJO route response:-" + pojo.toString());
                    getTrainClassStatus(pojo.getTrain_pojo().getList_classes());
                    getDayStatus(pojo.getTrain_pojo().getList_days());

                } else {
                    Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, "error:-" + e.toString());
            }
        }
    }

    public void getTrainClassStatus(List<ClassesPOJO> list_classes) {
        for(ClassesPOJO pojo:list_classes) {
            switch (pojo.getClass_code()) {
                case StringUtils.CC:
                    if (pojo.getAvailable().equals("N")) {
                        iv_cc.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_cc.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils.FC:
                    if (pojo.getAvailable().equals("N")) {
                        iv_fc.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_fc.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils._1A:
                    if (pojo.getAvailable().equals("N")) {
                        iv_1a.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_1a.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils.SL:
                    if (pojo.getAvailable().equals("N")) {
                        iv_sl.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_sl.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils._2S:
                    if (pojo.getAvailable().equals("N")) {
                        iv_2s.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_2s.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils._3A:
                    if (pojo.getAvailable().equals("N")) {
                        iv_3a.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_3a.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils._2A:
                    if (pojo.getAvailable().equals("N")) {
                        iv_2a.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_2a.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils._3E:
                    if (pojo.getAvailable().equals("N")) {
                        iv_3e.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_3e.setImageResource(R.drawable.ic_right);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    public void getDayStatus(List<DaysPOJO> list_days) {
        for(DaysPOJO pojo:list_days) {
            switch (pojo.getDay_code()) {
                case StringUtils.SUN:
                    if (pojo.getRuns().equals("N")) {
                        iv_sun.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_sun.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils.MON:
                    if (pojo.getRuns().equals("N")) {
                        iv_mon.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_mon.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils.TUE:
                    if (pojo.getRuns().equals("N")) {
                        iv_tue.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_tue.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils.WED:
                    if (pojo.getRuns().equals("N")) {
                        iv_wed.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_wed.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils.THU:
                    if (pojo.getRuns().equals("N")) {
                        iv_thu.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_thu.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils.FRI:
                    if (pojo.getRuns().equals("N")) {
                        iv_fri.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_fri.setImageResource(R.drawable.ic_right);
                    }
                    break;
                case StringUtils.SAT:
                    if (pojo.getRuns().equals("N")) {
                        iv_sat.setImageResource(R.drawable.ic_cancel);
                    }
                    else{
                        iv_sat.setImageResource(R.drawable.ic_right);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
