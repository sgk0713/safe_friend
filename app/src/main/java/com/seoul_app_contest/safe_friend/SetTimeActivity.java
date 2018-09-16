package com.seoul_app_contest.safe_friend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class SetTimeActivity extends AppCompatActivity{
    TimePicker timePicker;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        final String stationName = getIntent().getStringExtra("stationName");

        timePicker = findViewById(R.id.activity_set_time_timer_tp);
        nextBtn = findViewById(R.id.activity_set_time_confirm_btn);

        timePicker.setIs24HourView(true);
        timePicker.setHour(22);
        timePicker.setMinute(0);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetTimeActivity.this, LastConfirmActivity.class);

                String min;
                String hour;
                if(timePicker.getMinute()<10){
                    min = "0"+timePicker.getMinute();
                }else min = timePicker.getMinute()+"";
                if(timePicker.getHour()>=0 && timePicker.getHour()<2){
                    hour = "0"+timePicker.getHour();
                }else hour = timePicker.getHour() + "";

                String time = hour + " : " + min;
                intent.putExtra("stationName", stationName);
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //22:00 ~ 1:00
                if(hourOfDay == 1){
                    view.setMinute(0);
                }
                if(hourOfDay < 22 && hourOfDay > 1){
                    if(view.getHour()<23) view.setHour(22);
                    if(view.getHour()>=23) view.setHour(1);
                    view.setMinute(0);
                }

            }
        });





    }

}
