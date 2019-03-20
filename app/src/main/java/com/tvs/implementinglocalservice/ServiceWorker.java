package com.tvs.implementinglocalservice;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

public class ServiceWorker implements Runnable {
    private int counter = -1;
    private Dialog alertDialog;
    private static final String TAG = "ServiceWorker";
    private Context context;
    NetworkCallbackInterface anInterface;
    public ServiceWorker(int counter, Context context) {
        this.counter = counter;
        this.context = context;
        anInterface = new MainActivity();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void run() {
        Log.i(TAG, "Service worker "+ Thread.currentThread().getName());
        Log.i(TAG, "run: sleeping.....");
        //            Thread.sleep(100000);
        try {
            Thread.sleep(10000);
            Intent intent = new Intent();
            intent.setAction("abc.efg");
            context.sendBroadcast(intent);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "run: waking up....");

    }

}
