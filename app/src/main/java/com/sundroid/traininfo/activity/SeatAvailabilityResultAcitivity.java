package com.sundroid.traininfo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sundroid.traininfo.R;
import com.sundroid.traininfo.pojo.seatavailability.AvailabilityPOJO;
import com.sundroid.traininfo.pojo.seatavailability.SeatAvailabilityPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatAvailabilityResultAcitivity extends AppCompatActivity {
    SeatAvailabilityPOJO seatAvailabilityPOJO;

    @BindView(R.id.tv_train_name)
    TextView tv_train_name;
    @BindView(R.id.tv_from_to)
    TextView tv_from_to;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.ll_availability_scroll)
    LinearLayout ll_availability_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_availability_result_acitivity);
        ButterKnife.bind(this);
        seatAvailabilityPOJO= (SeatAvailabilityPOJO) getIntent().getSerializableExtra("seatavailabilitypojo");
        if(seatAvailabilityPOJO!=null){
            tv_train_name.setText(seatAvailabilityPOJO.getTrain_name());

            tv_from_to.setText(seatAvailabilityPOJO.getFromPOJO().getName()+"("+
            seatAvailabilityPOJO.getFromPOJO().getCode()+") to "+
            seatAvailabilityPOJO.getToPOJO().getName()+"("+seatAvailabilityPOJO.getToPOJO().getCode()+")");

            tv_date.setText(seatAvailabilityPOJO.getLastUpdatedPOJO().getDate()+" "+
                    seatAvailabilityPOJO.getLastUpdatedPOJO().getTime());

            tv_class.setText(seatAvailabilityPOJO.getClassPOJO().getClass_name()+"("+
            seatAvailabilityPOJO.getClassPOJO().getClass_code()+")");
            List<AvailabilityPOJO> list_availability=seatAvailabilityPOJO.getList_availability();
            inflateLinearList(list_availability);
        }
        else{
            finish();
        }

    }
    public void inflateLinearList(List<AvailabilityPOJO> list_availability){
        for (int i = 0; i < list_availability.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_seat_availability_result, null);
            TextView tv_availability = (TextView) view.findViewById(R.id.tv_availability);
            TextView tv_inflate_date = (TextView) view.findViewById(R.id.tv_inflate_date);
            LinearLayout ll_available = (LinearLayout) view.findViewById(R.id.ll_available);

            tv_availability.setText(list_availability.get(i).getStatus());
            tv_inflate_date.setText(list_availability.get(i).getDate());

            ll_availability_scroll.addView(view);
        }
    }
}
