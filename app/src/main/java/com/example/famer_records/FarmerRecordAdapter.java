package com.example.famer_records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FarmerRecordAdapter extends ArrayAdapter<FarmerRecord> {

    public FarmerRecordAdapter(Context context, List<FarmerRecord> records) {
        super(context, 0, records);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_record, parent, false);
        }

        FarmerRecord record = getItem(position);
        TextView tvRecordItem = convertView.findViewById(R.id.tvRecordItem);
        if (record != null) {
            tvRecordItem.setText(record.toString());
        }

        return convertView;
    }
}