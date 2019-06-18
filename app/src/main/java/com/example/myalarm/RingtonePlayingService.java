package com.example.myalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class RingtonePlayingService extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer MediaPlayer;
    private int startId;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            String state=intent.getExtras().getString("extra");

            assert state !=null;
            switch (state){
                case "on":
                    startId =1;
                    break;
                case "off":
                    startId=0;
                    break;
                default:
                    startId=0;
                    break;
            }
            //MediaPlayer.start();
            if(!this.isRunning&&startId ==1){
                MediaPlayer = MediaPlayer.create(this, R.raw.nafla);
                MediaPlayer.start();

                this.isRunning = true;
                this.startId = 0;

            }
            else if(this.isRunning&&startId ==0){
                MediaPlayer.stop();
                MediaPlayer.reset();

                this.isRunning=false;
                this.startId = 0;
            }
            else if(!this.isRunning&&startId ==0)
            {
                this.isRunning=false;
                this.startId = 0;
            }
            else if(this.isRunning&&startId ==1)
            {
                this.isRunning=true;
                this.startId = 1;
            }






            return START_NOT_STICKY;

        }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }







}