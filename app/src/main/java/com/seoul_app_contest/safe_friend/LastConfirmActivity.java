package com.seoul_app_contest.safe_friend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.seoul_app_contest.safe_friend.waitingncomfirm.WatingActivity;

public class LastConfirmActivity extends AppCompatActivity {

    TextView stationNameTv, timeTv;
    Button confirmBtn;
    String stop_nm, stop_no, xcode, ycode, line, time, street;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_confirm);

        getIntentData();

        stationNameTv = findViewById(R.id.activity_last_confirm_station_name_tv);
        timeTv = findViewById(R.id.activity_last_confirm_time_tv);
        confirmBtn = findViewById(R.id.activity_last_confirm_btn);

        stationNameTv.setText(stop_nm);
        timeTv.setText(time);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putIntentData();
                startActivity(intent);
                SearchPlaceActivity._SearchPlaceActivity.finish();
                SetTimeActivity._SetTimeActivity.finish();
                finish();
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
        time = intent.getStringExtra("time");
    }

    void putIntentData(){
        intent = new Intent(LastConfirmActivity.this, WatingActivity.class);
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
