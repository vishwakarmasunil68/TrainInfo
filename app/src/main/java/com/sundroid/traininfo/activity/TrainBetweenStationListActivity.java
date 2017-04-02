package com.sundroid.traininfo.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sundroid.traininfo.R;
import com.sundroid.traininfo.pojo.trainbetween.ClassesPOJO;
import com.sundroid.traininfo.pojo.trainbetween.DaysPOJO;
import com.sundroid.traininfo.pojo.trainbetween.TrainBetweenStationPOJO;
import com.sundroid.traininfo.pojo.trainbetween.TrainPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainBetweenStationListActivity extends AppCompatActivity {
    private final String TAG=getClass().getSimpleName();
    @BindView(R.id.ll_train_list)
    LinearLayout ll_train_list;
    TrainBetweenStationPOJO trainBetweenStationPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_station_list);
        ButterKnife.bind(this);
        trainBetweenStationPOJO= (TrainBetweenStationPOJO) getIntent().getSerializableExtra("trainbetweenlist");
        if(trainBetweenStationPOJO!=null){
            inflateTrainList(trainBetweenStationPOJO.getList_train_pojo());
        }
        else{
            finish();
        }
    }

    public void inflateTrainList(final List<TrainPOJO> list_trains){
        for (int i = 0; i < list_trains.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_list_train_between, null);
            CardView cv_train = (CardView) view.findViewById(R.id.cv_train);
            TextView tv_train_name = (TextView) view.findViewById(R.id.tv_train_name);
            TextView tv_from_to_name = (TextView) view.findViewById(R.id.tv_from_to_name);
            TextView tv_from_to_time = (TextView) view.findViewById(R.id.tv_from_to_time);
            TextView tv_running_days = (TextView) view.findViewById(R.id.tv_running_days);
            TextView tv_travel_time = (TextView) view.findViewById(R.id.tv_travel_time);
            TextView tv_class = (TextView) view.findViewById(R.id.tv_class);

            List<String> list_running_days=getListRunningDays(list_trains.get(i).getList_days());
            List<String> list_classes=getClassesDays(list_trains.get(i).getList_classes());

            String string_running_days="";
            String string_classes="";
            for(int j=0;j<list_running_days.size();j++){
                if((j+1)==list_running_days.size()){
                    string_running_days+=list_running_days.get(j);
                }
                else{
                    string_running_days+=list_running_days.get(j)+" , ";
                }
            }

            for(int j=0;j<list_classes.size();j++){
                if((j+1)==list_classes.size()){
                    string_classes+=list_classes.get(j);
                }
                else{
                    string_classes+=list_classes.get(j)+" , ";
                }
            }
            Log.d(TAG,"running days:-"+list_running_days.toString());
            Log.d(TAG,"classes:-"+list_classes.toString());

            tv_train_name.setText(list_trains.get(i).getName()+"("+list_trains.get(i).getNumber()+")");

            tv_from_to_name.setText(list_trains.get(i).getFromPOJO().getName()+" - "+
            list_trains.get(i).getToPOJO().getName());

            tv_from_to_time.setText(list_trains.get(i).getSrc_departure_time()+"("+
                list_trains.get(i).getFromPOJO().getCode()+") - "+
                list_trains.get(i).getDest_arrival_time()+"("+list_trains.get(i).getToPOJO().getCode()+")");

            tv_travel_time.setText(list_trains.get(i).getTravel_time());

            tv_running_days.setText(string_running_days);
            tv_class.setText(string_classes);
            final int finalI = i;
            cv_train.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(list_trains.get(finalI).getNumber());
                }
            });
            ll_train_list.addView(view);
        }
    }

    public List<String> getClassesDays(List<ClassesPOJO> list_classes){
        List<String> list_classes_available=new ArrayList<>();
        for(int i=0;i<list_classes.size();i++){
            ClassesPOJO pojo=list_classes.get(i);

            if(pojo.getAvailable().equals("Y")){
                list_classes_available.add(pojo.getClass_code());
            }
            else{

            }
        }
        return list_classes_available;
    }

    public List<String> getListRunningDays(List<DaysPOJO> list_days){
        List<String> list_days_added=new ArrayList<>();
        for(int i=0;i<list_days.size();i++){
            DaysPOJO pojo=list_days.get(i);

            if(pojo.getRuns().equals("Y")){
                list_days_added.add(pojo.getDay_code());
            }
            else{

            }
        }
        return list_days_added;
    }
    public void showDialog(final String train_no){
        final Dialog dialog1 = new Dialog(TrainBetweenStationListActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dilaog_train_option);
        dialog1.setTitle("Search For");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ll_live_train= (LinearLayout) dialog1.findViewById(R.id.ll_live_train);
        LinearLayout ll_train_route= (LinearLayout) dialog1.findViewById(R.id.ll_train_route);

        ll_live_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                Intent intent=new Intent(TrainBetweenStationListActivity.this,LiveTrainStatusActivity.class);
                intent.putExtra("train",train_no);
                startActivity(intent);
            }
        });

        ll_train_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                Intent intent=new Intent(TrainBetweenStationListActivity.this,TrainRouteActivity.class);
                intent.putExtra("trainno",train_no);
                startActivity(intent);
            }
        });

    }
}
