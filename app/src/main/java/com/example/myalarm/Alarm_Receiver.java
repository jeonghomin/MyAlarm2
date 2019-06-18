package com.example.myalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String get_your_string = intent.getExtras().getString("extra");
            //create an intent to the ringtone service

        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("extra",get_your_string);
        //start the ringtone service
        context.startService(service_intent);

    }
}
