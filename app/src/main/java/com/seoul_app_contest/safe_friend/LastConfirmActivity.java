package com.seoul_app_contest.safe_friend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LastConfirmActivity extends AppCompatActivity {

    TextView stationNameTv;
    TextView timeTv;
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_confirm);

        String stationName = getIntent().getStringExtra("stationName");
        String time = getIntent().getStringExtra("time");

        stationNameTv = findViewById(R.id.activity_last_confirm_station_name_tv);
        timeTv = findViewById(R.id.activity_last_confirm_time_tv);
        confirmBtn = findViewById(R.id.activity_last_confirm_btn);

        stationNameTv.setText(stationName);
        timeTv.setText(time);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"대기화면으로 이동할 버튼", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
