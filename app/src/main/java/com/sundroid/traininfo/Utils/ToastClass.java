package com.sundroid.traininfo.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sunil on 26-03-2017.
 */

public class ToastClass {
    public static void showLongToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
