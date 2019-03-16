package com.tvs.implementinglocalservice;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity implements NetworkCallbackInterface {

    private static final String TAG = "MainActivity";
    Button start, stop,next;
    int counter = 1;
    private Intent intent;
    private Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startService);
        stop = findViewById(R.id.stopService);
        next = findViewById(R.id.next_activity);
        intent = new Intent(MainActivity.this,BackgroundService.class);
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
                Intent intent = new Intent(getApplicationContext(),NextActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: service killed");
        Toast.makeText(this, "on Stop: service killed", Toast.LENGTH_SHORT).show();
        stopService(intent);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: service killed");
        stopService(intent);
        super.onDestroy();
    }


    private void createNetworkAlertDialog() {

        if (alertDialog != null && alertDialog.isShowing()) return;
        alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Network is not available")
                .setMessage("Open network setting")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        ComponentName cName = new ComponentName("com.android.phone", "com.android.phone.Settings");
                        intent.setComponent(cName);
                        startActivity(intent);
                    }
                })
                //set negative button
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.cancel();
                    }
                })
                .show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void createDialog() {
        createNetworkAlertDialog();
    }
}
