package com.seoul_app_contest.safe_friend.map;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seoul_app_contest.safe_friend.R;


public class SpinnerAdapter extends BaseAdapter {


    Context context;
    String[] data;
    LayoutInflater inflater;


    public SpinnerAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data != null) return data.length;
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_normal, parent, false);
        }

        if (data != null) {
            //데이터세팅
            ((TextView) convertView.findViewById(R.id.spinnerNormalTextView)).setText(data[position]);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_dropdown, parent, false);
        }

        //데이터세팅
        ((TextView) convertView.findViewById(R.id.spinnerDropdownTextView)).setText(data[position]);
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}


