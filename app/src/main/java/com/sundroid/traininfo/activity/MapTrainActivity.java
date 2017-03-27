package com.sundroid.traininfo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sundroid.traininfo.R;
import com.sundroid.traininfo.pojo.trainroute.RoutePOJO;
import com.sundroid.traininfo.pojo.trainroute.TrainRoutePOJO;

import java.util.ArrayList;
import java.util.List;

import static com.sundroid.traininfo.R.id.map;

public class MapTrainActivity extends AppCompatActivity implements OnMapReadyCallback {
    TrainRoutePOJO trainRoutePOJO;
    GoogleMap mMap;

    private final String TAG=getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_train);
        trainRoutePOJO= (TrainRoutePOJO) getIntent().getSerializableExtra("trainroute");
        if(trainRoutePOJO!=null){

        }
        else{
            finish();
        }
        setUpGoogleMap();
    }
    public void setUpGoogleMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<RoutePOJO> list_route_pojo=trainRoutePOJO.getList_route_pojo();
        List<LatLng> list_latlng=new ArrayList<>();
        for(int i=0;i<list_route_pojo.size();i++) {
            RoutePOJO routePOJO=list_route_pojo.get(i);
            try {
                double latitude= Double.parseDouble(routePOJO.getLat());
                double longitude= Double.parseDouble(routePOJO.getLng());
                LatLng latLng=new LatLng(latitude, longitude);
                list_latlng.add(latLng);
//                Log.d(TAG,"lat lna:-"+latitude+" , "+longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title((i+1)+"."+routePOJO.getFullname()+" , "+latLng.latitude+","+latLng.longitude).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            catch (Exception e){
                Log.d(TAG,e.toString());
            }
        }

        PolylineOptions line=
                new PolylineOptions().addAll(list_latlng).width(5).color(Color.RED);
        mMap.addPolyline(line);
    }
}
