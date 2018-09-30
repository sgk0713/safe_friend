package com.seoul_app_contest.safe_friend.map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.seoul_app_contest.safe_friend.CancelServiceActivity;
import com.seoul_app_contest.safe_friend.R;


public class CancelDialog extends Dialog {
    Context mContext;
    public CancelDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_menu);
        final Spinner spinner = findViewById(R.id.cancelList);
        String[] list = {"개인적 사정","지킴이와의 불화","위치 및 시간 변경","기타"};

        spinner.setAdapter(new SpinnerAdapter(mContext, list));

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CancelServiceActivity.class);
                mContext.startActivity(intent);
                dismiss();
                ((Activity)mContext).finish();
            }
        });
    }

}
