package com.sundroid.traininfo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sundroid.traininfo.R;
import com.sundroid.traininfo.pojo.livetrainstatus.Route;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveTrainStationInfoActivity extends AppCompatActivity {

    @BindView(R.id.tv_train_name)
    TextView tv_train_name;
    @BindView(R.id.tv_journey_station)
    TextView tv_journey_station;
    @BindView(R.id.tv_journey_date)
    TextView tv_journey_date;
    @BindView(R.id.tv_scheduled_arrival)
    TextView tv_scheduled_arrival;
    @BindView(R.id.tv_actual_arrival)
    TextView tv_actual_arrival;
    @BindView(R.id.tv_scheduled_departure)
    TextView tv_scheduled_departure;
    @BindView(R.id.tv_actual_departure)
    TextView tv_actual_departure;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_departed)
    TextView tv_departed;
    @BindView(R.id.tv_arrived)
    TextView tv_arrived;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_station_info);
        ButterKnife.bind(this);
        route = (Route) getIntent().getSerializableExtra("route");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (route != null) {
            tv_journey_station.setText(route.getStation_().getName() + "(" + route.getStation_().getCode() + ")");
            getSupportActionBar().setTitle(route.getStation_().getName() + "(" + route.getStation_().getCode() + ")");
            tv_journey_date.setText(route.getScharr_date());
            tv_scheduled_arrival.setText(route.getScharr());
            tv_actual_arrival.setText(route.getActarr());
            tv_scheduled_departure.setText(route.getSchdep());
            tv_actual_departure.setText(route.getActdep());
            tv_status.setText(route.getStatus());
            tv_distance.setText(route.getDistance()+"");
            tv_departed.setText(route.isHas_departed() + "");
            tv_arrived.setText(route.isHas_arrived() + "");
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
