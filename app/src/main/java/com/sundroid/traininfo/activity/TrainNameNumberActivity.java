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

import com.google.gson.Gson;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.Utils.WebUrls;
import com.sundroid.traininfo.pojo.trainnamenumber.TrainNameNumberPOJO;
import com.sundroid.traininfo.webservices.WebServiceBase;
import com.sundroid.traininfo.webservices.WebServicesCallBack;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainNameNumberActivity extends AppCompatActivity implements View.OnClickListener,WebServicesCallBack{
    private final static String TRAIN_NAME_NUMBER="train_name_number";
    private final String TAG=getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_train_name)
    EditText et_train_name;
    @BindView(R.id.tv_train_name)
    TextView tv_train_name;
    @BindView(R.id.tv_running_days)
    TextView tv_running_days;
    @BindView(R.id.btn_search)
    Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_name_number);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_search:
                callTrainAPI();
                break;
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

    public void callTrainAPI(){
        if(et_train_name.getText().toString().length()>0){
            String url = WebUrls.getTrainNameNumberURL(et_train_name.getText().toString());
            Log.d(TAG,"url:-"+url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            new WebServiceBase(nameValuePairs, this, TRAIN_NAME_NUMBER).execute(url);
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
            case TRAIN_NAME_NUMBER:
                parseTrainResponse(response);
                break;
        }
    }
    public void parseTrainResponse(String response){
//        Log.d(TAG,"response:-"+response);
        Gson gson=new Gson();
        TrainNameNumberPOJO pojo=gson.fromJson(response,TrainNameNumberPOJO.class);
        if(pojo!=null){
            try{
                if(pojo.getResponse_code().equals("200")){
                    Log.d(TAG,"TrainPOJO route response:-"+pojo.toString());
                    List<String> list_running_days=getListRunningDays(pojo.getTrain_pojo().getList_days_pojo());
                    String string_running_days="";
                    for(int j=0;j<list_running_days.size();j++){
                        if((j+1)==list_running_days.size()){
                            string_running_days+=list_running_days.get(j);
                        }
                        else{
                            string_running_days+=list_running_days.get(j)+" , ";
                        }
                    }
                    tv_running_days.setText(string_running_days);
                    tv_train_name.setText(pojo.getTrain_pojo().getName()+"("+
                    pojo.getTrain_pojo().getNumber()+")");
                }else{
                    Toast.makeText(getApplicationContext(),"No Response",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Log.d(TAG,"error:-"+e.toString());
            }
        }
    }

    public List<String> getListRunningDays(List<com.sundroid.traininfo.pojo.trainnamenumber.DaysPOJO> list_days){
        List<String> list_days_added=new ArrayList<>();
        for(int i=0;i<list_days.size();i++){
            com.sundroid.traininfo.pojo.trainnamenumber.DaysPOJO pojo=list_days.get(i);

            if(pojo.getRuns().equals("Y")){
                list_days_added.add(pojo.getDay_code());
            }
            else{

            }
        }
        return list_days_added;
    }

    String train_name_number="{\n" +
            "response_code: 200,\n" +
            "train: {\n" +
            "days: [\n" +
            "{\n" +
            "runs: \"Y\",\n" +
            "day-code: \"SUN\"\n" +
            "},\n" +
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
            "}\n" +
            "],\n" +
            "name: \"HWH MUMBAI MAIL\",\n" +
            "number: \"12321\"\n" +
            "}\n" +
            "}";
}
