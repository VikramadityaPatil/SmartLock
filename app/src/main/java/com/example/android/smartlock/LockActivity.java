package com.example.android.smartlock;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class LockActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayList<BluetoothDevice> discoveredDevices;
    private ArrayList<BluetoothDevice> devices;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        listView = (ListView) findViewById(R.id.list_view);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = bluetoothAdapter.getBondedDevices();
        devices = new ArrayList<>();
        DeviceAdapter deviceAdapter = new DeviceAdapter(this,devices);
        listView.setAdapter(deviceAdapter);

        displayPairedDevices(pairedDevices,deviceAdapter);


    }

    private void displayPairedDevices(Set pairedDevices,DeviceAdapter adapter) {
        /*if(pairedDevices.size()>0){
            for(BluetoothDevice device: pairedDevices){
                devices.add(device);
            }
        }*/
        devices.addAll(pairedDevices);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothAdapter.disable();
    }
}
