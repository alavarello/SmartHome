package com.grupo1.hci.smarthome.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by francisco on 18/11/2017.
 */

public class BootListenerReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, ApiService.class);
            context.startService(serviceIntent);
        }



    }


}
