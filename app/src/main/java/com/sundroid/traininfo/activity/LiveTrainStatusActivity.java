package com.sundroid.traininfo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.ToastClass;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.pojo.livetrainstatus.LiveDateStatus;
import com.sundroid.traininfo.pojo.livetrainstatus.LiveDateTrainPOJO;
import com.sundroid.traininfo.pojo.livetrainstatus.LiveTrainStatus;
import com.sundroid.traininfo.pojo.livetrainstatus.Route;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @BindView(R.id.ll_date_scroll)
    LinearLayout ll_date_scroll;
    @BindView(R.id.spinner_date)
    Spinner spinner_date;
    @BindView(R.id.spinner_station)
    Spinner spinner_station;


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


        spinner_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setOnspinnerChangeListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_station.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setOnspinnerChangeListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setOnspinnerChangeListener(){
        Log.d(TAG,"date:-"+spinner_date.getSelectedItemId());
        Log.d(TAG,"station:-"+spinner_station.getSelectedItemId());
        String date=list_of_dates.get(spinner_date.getSelectedItemPosition());
        String station=list_of_stations.get(spinner_station.getSelectedItemPosition());
        if(date.equals("All")){
            date="";
        }
        if (station.equals("All")){
            station="";
        }
//        if(spinner_date.getSelectedItemPosition()==0&&spinner_station.getSelectedItemPosition()==0){
//
//        }else{
//            if(spinner_date.getSelectedItemPosition()>0&&spinner_station.getSelectedItemPosition()>0){
//                date=list_of_dates.get(sp)
//            }
//        }
        if(date.length()>0&&station.length()>0){
            List<Route> list_routes=new ArrayList<>();
            for(Route route:list_of_routes){
                String stations=route.getStation_().getName()+"("+route.getStation_().getCode()+")";
                if(route.getScharr_date().equals(date)&&stations.equals(station)){
                    list_routes.add(route);
                }
            }
            inflateRouteList(list_routes);
        }
        else{
            if(date.length()>0){
                List<Route> list_routes=new ArrayList<>();
                for(Route route:list_of_routes){
                    if(route.getScharr_date().equals(date)){
                        list_routes.add(route);
                    }
                }
                inflateRouteList(list_routes);
            }
            else{
                if(station.length()>0){
                    List<Route> list_routes=new ArrayList<>();
                    for(Route route:list_of_routes){
                        String stations=route.getStation_().getName()+"("+route.getStation_().getCode()+")";
                        if(stations.equals(station)){
                            list_routes.add(route);
                        }
                    }
                    inflateRouteList(list_routes);
                }
                else{
                    inflateRouteList(list_of_routes);
                }
            }
        }
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
        if(et_train_number.getText().toString().length()>0) {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            Date d=new Date();
            Log.d(TAG,"date:-"+sdf.format(d));
            String url = WebUrls.getLiveTrainURL(et_train_number.getText().toString(), sdf.format(d));
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
//                Log.d(TAG,"response:-"+response);
                parseLiveTrainResponse(response);
                break;
        }
    }
    List<Route> list_of_routes=new ArrayList<>();
    List<String> list_of_dates=new ArrayList<>();
    List<String> list_of_stations=new ArrayList<>();
    public void parseLiveTrainResponse(String response){
//        Log.d(TAG,"live train response:-"+response);
        try{
            Gson gson=new Gson();
            LiveTrainStatus liveTrainStatus=gson.fromJson(response,LiveTrainStatus.class);
            if(liveTrainStatus.getResponse_code()==200){
                Log.d(TAG,liveTrainStatus.toString());
//                formatStatusAccToDate(liveTrainStatus);
                list_of_routes.clear();
                list_of_dates.clear();
                list_of_stations.clear();

                list_of_routes.addAll(liveTrainStatus.getRouteList());
                Set<String> set_of_dates=new HashSet<>();

                list_of_stations.add("All");
                list_of_dates.add("All");
                for(Route route:liveTrainStatus.getRouteList()){
                    set_of_dates.add(route.getScharr_date());
                    list_of_stations.add(route.getStation_().getName()+"("+route.getStation_().getCode()+")");
                }
                list_of_dates.addAll(set_of_dates);

                //setting stations
                ArrayAdapter<String> station_adapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_spinner_dropdown_item,list_of_stations);
                spinner_station.setAdapter(station_adapter);

                ArrayAdapter<String> date_adapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_spinner_dropdown_item,list_of_dates);
                spinner_date.setAdapter(date_adapter);

                inflateRouteList(liveTrainStatus.getRouteList());

            }
            else{
                if(liveTrainStatus.getResponse_code()==510){
                    ToastClass.showLongToast(getApplicationContext(),"Train not scheduled to run on the given date.");
                }
                else{
                    ToastClass.showLongToast(getApplicationContext(),"Train is not running on the given date");
                }
            }
        }
        catch (Exception e){
            ToastClass.showLongToast(getApplicationContext(),"Server is temporary down");
        }
    }

    public void formatStatusAccToDate(LiveTrainStatus liveTrainStatus){
        List<Route> routeList=liveTrainStatus.getRouteList();
        Set<String> setDates=new HashSet<>();
        List<String> listDates=new ArrayList<>();
        if(routeList.size()>0){
            for(Route route:routeList){
                setDates.add(route.getScharr_date());
            }
            listDates.addAll(setDates);
            Collections.sort(listDates, new Comparator<String>() {

                @Override
                public int compare(String arg0, String arg1) {
                    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                    int compareResult = 0;
                    try {
                        Date arg0Date = format.parse(arg0);
                        Date arg1Date = format.parse(arg1);
                        compareResult = arg0Date.compareTo(arg1Date);
                    } catch (Exception e) {
                        e.printStackTrace();
                        compareResult = arg0.compareTo(arg1);
                    }
                    return compareResult;
                }
            });
            JSONObject object = new JSONObject();
            JSONArray array = new JSONArray();
            for(String string_date:listDates){
                JSONObject date_object = new JSONObject();
                JSONArray date_array = new JSONArray();
                for(Route route:routeList){
                    if(string_date.equals(route.getScharr_date())){
                        try {
                            JSONObject object1 = new JSONObject();
                            object1.put("day", route.getDay());
                            object1.put("status", route.getStatus());
                            object1.put("distance", route.getDistance());
                            object1.put("scharr_date", route.getScharr_date());
                            object1.put("schdep", route.getSchdep());
                            object1.put("station", route.getStation());

                            JSONObject object2=new JSONObject();
                            object2.put("name",route.getStation_().getName());
                            object2.put("code",route.getStation_().getCode());

                            object1.put("station_",object2);



                            object1.put("no", route.getNo());
                            object1.put("actarr", route.getActarr());
                            object1.put("actdep", route.getActdep());
                            object1.put("scharr", route.getScharr());
                            object1.put("has_departed", route.isHas_departed());
                            object1.put("latemin", route.getLatemin());
                            object1.put("actarr_date", route.getActarr_date());
                            object1.put("has_arrived", route.isHas_arrived());

                            date_array.put(object1);
                        } catch (Exception e) {
                            Log.d(TAG, e.toString());
                        }
                    }
                }
                try {
                    date_object.put("date",string_date);
                    date_object.put("result",date_array);
                    array.put(date_object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                if(array.length()>0) {
                    object.put("success", true);
                    object.put("result", array);
                }
                else{
                    object.put("success", false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            parseDateLiveResponse(object.toString());
        }
        else{
            ToastClass.showLongToast(getApplicationContext(),"No Route Found");
        }
    }

    public void parseDateLiveResponse(String response){
//        Log.d(TAG,"date status:-"+response);
        try{
            Gson gson=new Gson();
            LiveDateStatus liveDateStatus=gson.fromJson(response,LiveDateStatus.class);
            if(liveDateStatus.getSuccess().equals("true")){
                Log.d(TAG,"live train:-"+liveDateStatus.toString());
                inflateDateLayout(liveDateStatus.getLiveDateTrainPOJOs());

            }
            else{
                ToastClass.showLongToast(getApplicationContext(),"No Route Found");
            }
        }
        catch (Exception e){
            ToastClass.showLongToast(getApplicationContext(),"Something went wrong");
        }
    }

    public void inflateDateLayout(List<LiveDateTrainPOJO> liveDateTrainPOJOs){
        for (int i = 0; i < liveDateTrainPOJOs.size(); i++) {
            LiveDateTrainPOJO liveDateTrainPOJO=liveDateTrainPOJOs.get(i);
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_live_date, null);
            TextView tv_date= (TextView) view.findViewById(R.id.tv_date);
            LinearLayout ll_route_scroll= (LinearLayout) view.findViewById(R.id.ll_route_scroll);

            tv_date.setText(liveDateTrainPOJO.getDate());
            List<Route> routeList=liveDateTrainPOJO.getRouteList();
//            inflateRouteList(routeList,ll_route_scroll);

            ll_date_scroll.addView(view);
        }
    }
    public void inflateRouteList(List<Route> routeList){
        ll_date_scroll.removeAllViews();
        for(int i=0;i<routeList.size();i++){
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_route_data, null);

            final Route route=routeList.get(i);

            TextView tv_station_info= (TextView) view.findViewById(R.id.tv_station_info);
            TextView tv_sch_arr= (TextView) view.findViewById(R.id.tv_sch_arr);
            TextView tv_sch_dep= (TextView) view.findViewById(R.id.tv_sch_dep);
            TextView tv_act_arr= (TextView) view.findViewById(R.id.tv_act_arr);
            TextView tv_act_dep= (TextView) view.findViewById(R.id.tv_act_dep);
            TextView tv_distance= (TextView) view.findViewById(R.id.tv_distance);
            TextView tv_status= (TextView) view.findViewById(R.id.tv_status);
            TextView tv_date= (TextView) view.findViewById(R.id.tv_date);
            TextView tv_day= (TextView) view.findViewById(R.id.tv_day);
            LinearLayout ll_route= (LinearLayout) view.findViewById(R.id.ll_route);

            tv_date.setText(route.getScharr_date());
            tv_day.setText("Day : "+route.getDay());
            tv_station_info.setText("Station : "+route.getStation_().getName()+" ("+route.getStation_().getCode()+")");
            tv_sch_arr.setText("Sch Arr : "+route.getScharr());
            tv_sch_dep.setText("Sch Dep : "+route.getSchdep());
            tv_act_arr.setText("Act Arr : "+route.getActarr());
            tv_act_dep.setText("Act Dep : "+route.getActdep());

            tv_distance.setText("Distance : "+route.getDistance());
            tv_status.setText("\tStatus : "+route.getStatus());

            ll_route.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(LiveTrainStatusActivity.this,LiveTrainStationInfoActivity.class);
                    intent.putExtra("route",route);
                    startActivity(intent);
                }
            });

            ll_date_scroll.addView(view);
        }
    }
}
