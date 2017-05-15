package com.example.android.smartlock;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class LockActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayList<BluetoothDevice> discoveredDevices;
    private ArrayList<BluetoothDevice> devices;
    private DeviceAdapter deviceAdapter;
    private DeviceAdapter deviceAdapter2;
    private ListView listView;
    private ListView listView2;
    private IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        listView = (ListView) findViewById(R.id.list_view);
        listView2 = (ListView) findViewById(R.id.list_view_2);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = bluetoothAdapter.getBondedDevices();
        discoveredDevices = new ArrayList<>();
        devices = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(this,devices);
        deviceAdapter2 = new DeviceAdapter(this,discoveredDevices);
        listView.setAdapter(deviceAdapter);
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(mReceiver,filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver,filter);
        listView2.setAdapter(deviceAdapter2);
        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        bluetoothAdapter.startDiscovery();

        displayPairedDevices(pairedDevices,deviceAdapter);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice mdevice = devices.get(position);
                Toast.makeText(getApplicationContext(), mdevice.getName(),Toast.LENGTH_SHORT).show();
            }
        });


    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice mdevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                discoveredDevices.add(mdevice);
                deviceAdapter2.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),mdevice.getName(),Toast.LENGTH_SHORT).show();
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                Toast.makeText(getApplicationContext(),"Discovery Started",Toast.LENGTH_SHORT).show();
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                Toast.makeText(getApplicationContext(),"Discovery Finished",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void displayPairedDevices(Set pairedDevices,DeviceAdapter adapter) {
        devices.addAll(pairedDevices);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice mdevice = devices.get(position);
                Toast.makeText(getApplicationContext(), mdevice.getName(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothAdapter.disable();
        bluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);
    }
}
