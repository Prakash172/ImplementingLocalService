package com.tvs.implementinglocalservice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ServiceWorker implements Runnable {

    /*
    * This class is defining the work of the service
    * that is checking the internet connection and notifying the activity if internet is not available
    * check the run method for logic implementation
    * */
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

    /*
    * Checks internet connections
    * @return boolean if internet is available of not
    * */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void run() {
        Log.i(TAG, "Service worker " + Thread.currentThread().getName());
        Log.i(TAG, "service started:  checking internet connections.....");
        //            Thread.sleep(100000);
        int x = 0;

        try {
            while (true) {
                Thread.sleep(1000);
                Intent intent = new Intent();
                intent.setAction("abc.efg");
//                if (!isNetworkAvailable()) {
                    context.sendBroadcast(intent);
//                }
                Log.i(TAG, "run: "+Thread.currentThread().toString()+" count"+x++);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "run: waking up....");

    }
}
