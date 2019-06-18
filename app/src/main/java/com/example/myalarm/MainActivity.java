package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.util.Log;
import android.os.Build;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //make alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    MainActivity inst;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            this.context = this;


            //initiallize alarm manager
            alarm_manager=(AlarmManager) getSystemService(ALARM_SERVICE);

            //initiallize our timepicker
            alarm_timepicker = (TimePicker) findViewById(R.id.TimePicker);

             //initiallize  update box
            update_text = (TextView)findViewById(R.id.update_text);

            //instance of calender

            final Calendar calendar = Calendar.getInstance();

            //start button
            Button alarm_on = (Button)findViewById(R.id.alarm_on);

            //create a intent to the receiver class

            final Intent my_intent = new Intent(this.context,Alarm_Receiver.class);

            alarm_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //달력
                    calendar.set(Calendar.HOUR_OF_DAY,alarm_timepicker.getHour());
                    calendar.set(Calendar.MINUTE,alarm_timepicker.getMinute());

                    int hour= alarm_timepicker.getHour();
                    int minute=alarm_timepicker.getMinute();
                    //convert int to strings
                    String hour_string = String.valueOf(hour);
                    String minute_string = String.valueOf(minute);

                    if(hour>12)
                    {
                        hour_string = String.valueOf(hour -12);
                    }
                    if(minute < 10){
                        minute_string = "0" + String.valueOf(minute);
                    }



                    //create a pending intent that delays the intent until the specified calendar
                    my_intent.putExtra("extra","on");
                    pending_intent = PendingIntent.getBroadcast(MainActivity.this,0,my_intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    //set the alarm manager
                    alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                            pending_intent);

                    set_alarm_text("알람이 " + hour_string + "시" + minute_string +"분으로 예약되었습니다.");
                }
            });

            //stop button
            Button alarm_off = (Button)findViewById(R.id.alarm_off);
            alarm_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    set_alarm_text("알람 꺼짐");


                    //알람 취소


                    //링톤 멈추다
                    my_intent.putExtra("extra","off");
                    sendBroadcast(my_intent);

                    alarm_manager.cancel(pending_intent);
                    set_alarm_text((" 알람 취소됨"));
                }
            });
    }

    private void set_alarm_text(String s) {
        update_text.setText(s);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
