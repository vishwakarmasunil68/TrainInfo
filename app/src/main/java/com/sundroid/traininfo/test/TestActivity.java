package com.sundroid.traininfo.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sundroid.traininfo.R;

import retrofit2.Retrofit;

public class TestActivity extends AppCompatActivity {
    private final String TAG=getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.androidhive.info/contacts/")
                .build();

//        Log.d(TAG,"result:-"+retrofit.);
    }
}
