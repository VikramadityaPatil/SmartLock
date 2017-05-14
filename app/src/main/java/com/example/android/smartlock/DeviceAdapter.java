package com.example.android.smartlock;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Vikramaditya Patil on 15-05-2017.
 */

public class DeviceAdapter extends ArrayAdapter<BluetoothDevice> {
    private ArrayList<BluetoothDevice> deviceList;
    public DeviceAdapter(Context context,ArrayList<BluetoothDevice> mdeviceList){
        super(context,R.layout.device_list_item,mdeviceList);
        this.deviceList = mdeviceList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BluetoothDevice device = deviceList.get(position);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView= inflater.inflate(R.layout.device_list_item,parent,false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.device_name);
        name.setText(device.getName());
        TextView paired = (TextView) convertView.findViewById(R.id.paired);
        paired.setText("PAIRED");

        return convertView;
    }
}
