package com.wipro.dialogtestmodule;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

public class DialogTest {
    private static AlertDialog alertDialog =  null;
    public static void createNetworkAlertDialog(final Context context) {

        if (alertDialog != null && alertDialog.isShowing()) return;
        alertDialog = new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Network is not available")
                .setMessage("Open network setting")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        ComponentName cName = new ComponentName("com.android.phone", "com.android.phone.Settings");
                        intent.setComponent(cName);
                        context.startActivity(intent);
                    }
                })
                //set negative button
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.cancel();
                    }
                })
                .create();

        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
    }
}
