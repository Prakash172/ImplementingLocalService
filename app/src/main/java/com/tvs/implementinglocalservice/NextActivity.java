package com.tvs.implementinglocalservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NextActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // this will run on main thread.
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NetowrkCheckingUtilAndDialog.createNetworkAlertDialog(NextActivity.this);
            }
        };

        // local registering , but it wont work if activity is not there,
        // preferred and recommended way is registering through manifest file.....
        registerReceiver(receiver, new IntentFilter("abc.efg"));
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
////        if (receiver != null)
//        Toast.makeText(this, "In OnStart", Toast.LENGTH_SHORT).show();
//            registerReceiver(receiver, new IntentFilter("abc.efg"));
//    }
//


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
}
