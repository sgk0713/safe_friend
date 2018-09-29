package com.seoul_app_contest.safe_friend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.waitingncomfirm.WatingActivity;

import org.w3c.dom.Text;

public class LastConfirmActivity extends AppCompatActivity {

    TextView stationNameTv;
    TextView timeTv;
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_confirm);

        String stop_nm = getIntent().getStringExtra("stop_nm");
        String time = getIntent().getStringExtra("time");

        stationNameTv = findViewById(R.id.activity_last_confirm_station_name_tv);
        timeTv = findViewById(R.id.activity_last_confirm_time_tv);
        confirmBtn = findViewById(R.id.activity_last_confirm_btn);

        stationNameTv.setText(stop_nm);
        timeTv.setText(time);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LastConfirmActivity.this, WatingActivity.class);
                intent.putExtra("stop_nm", getIntent().getStringExtra("stop_nm"));
                intent.putExtra("stop_no", getIntent().getStringExtra("stop_no"));
                intent.putExtra("xcode", getIntent().getStringExtra("xcode"));
                intent.putExtra("ycode", getIntent().getStringExtra("ycode"));
                startActivity(intent);
                finish();
            }
        });

    }
}
