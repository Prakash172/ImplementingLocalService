package com.tvs.implementinglocalservice;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity implements NetworkCallbackInterface {

    private static final String TAG = "MainActivity";
    Button start, stop, next;
    static int counter = 1;
    private Intent intent;
    private Dialog alertDialog;

    private BroadcastReceiver receiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startService);
        stop = findViewById(R.id.stopService);
        next = findViewById(R.id.next_activity);
        final TextView countTv = findViewById(R.id.count);


        // onReceive will run on main thread, so we cannot do long task without threading.
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                NetowrkCheckingUtilAndDialog.createNetworkAlertDialog(MainActivity.this);
                countTv.setText(String.valueOf(counter++));
            }
        };

        // local registering , but it wont work if activity is not there,
        // preferred and recommended way is registering through manifest file.....

        registerReceiver(receiver, new IntentFilter("abc.efg"));
        intent = new Intent(MainActivity.this, BackgroundService.class);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Starting service… counter = "
                        + counter);

                intent.putExtra("counter", counter++);
                startService(intent);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NextActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        if (receiver != null)
            registerReceiver(receiver, new IntentFilter("abc.efg"));
        //this is working
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: main");
//        Toast.makeText(this, "on Stop: service killed", Toast.LENGTH_SHORT).show();
//        stopService(intent);
        super.onStop();
        unregisterReceiver(receiver);
    }


    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: main");
//        stopService(intent);
        super.onDestroy();
    }


    @Override
    public void createDialog() {
        NetowrkCheckingUtilAndDialog.createNetworkAlertDialog(getBaseContext());
    }
}
