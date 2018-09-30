package com.seoul_app_contest.safe_friend;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.seoul_app_contest.safe_friend.confirmmap.ConfirmMapActivity;

public class SetTimeActivity extends AppCompatActivity{
    public static Activity _SetTimeActivity;
    TimePicker timePicker;
    Button nextBtn;
    String stop_nm, stop_no, xcode, ycode, line, street, time;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        _SetTimeActivity = SetTimeActivity.this;

        getIntentData();

        timePicker = findViewById(R.id.activity_set_time_timer_tp);
        nextBtn = findViewById(R.id.activity_set_time_confirm_btn);

        timePicker.setIs24HourView(true);
        timePicker.setHour(22);
        timePicker.setMinute(0);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String min;
                String hour;
                if(timePicker.getMinute()<10){
                    min = "0"+timePicker.getMinute();
                }else min = timePicker.getMinute()+"";

                if(timePicker.getHour()>=0 && timePicker.getHour()<2){
                    hour = "0"+timePicker.getHour();
                }else hour = timePicker.getHour() + "";

                time = hour + " : " + min;

                putIntentData();
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

    void getIntentData(){
        Intent intent = getIntent();
        stop_nm = intent.getStringExtra("stop_nm");
        stop_no = intent.getStringExtra("stop_no");
        xcode = intent.getStringExtra("xcode");
        ycode = intent.getStringExtra("ycode");
        street = intent.getStringExtra("street");
        line = intent.getStringExtra("line");
    }

    void putIntentData(){
        intent = new Intent(SetTimeActivity.this, LastConfirmActivity.class);
        intent.putExtra("time", time);
        intent.putExtra("stop_nm", stop_nm);
        intent.putExtra("stop_no", stop_no);
        intent.putExtra("xcode", xcode);
        intent.putExtra("ycode", ycode);
        intent.putExtra("line", line);
        intent.putExtra("street", street);
        Log.d("Wating_DEBUG", "stop_nm:"+stop_nm+
                " stop_no:"+stop_no+
                " xcode:"+xcode+
                " ycode:"+ycode+
                " line:"+line+
                " street:"+street+
                " time:"+time);

    }

}
