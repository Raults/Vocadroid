package com.hackats.vocadroid;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.harman.everestelite.Bluetooth;
import com.harman.everestelite.BluetoothListener;
import com.harman.everestelite.CommonListner;
import com.harman.everestelite.HeadPhoneCtrl;
import com.harman.everestelite.Log;

import java.io.IOException;

public class MetronomeTest2 extends AppCompatActivity implements BluetoothListener{

    Bluetooth mBluetooth;

    Runnable updateResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Hello");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome_test2);
        updateResult = new Runnable() {
            @Override
            public void run() {

            }
        };
        try {
            mBluetooth = new Bluetooth(this, this, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBluetooth.start();
    }
    // This class takes an instance of the BluetoothListener interface, Activity, and a boolean for secure (true)
    // or insecure (false) RF socket connection.

    // declare the variable..
    private HeadPhoneCtrl headphCtrl;
    Activity callingActivity;

    /*public Metronome() throws IOException {
        mBluetooth = new Bluetooth(this, this, true);
        mBluetooth.start();
        System.out.println("doop");
    }*/


    @Override
    public void bluetoothAdapterChangedState(Bluetooth bluetooth, int i, int i1) {

    }

    @Override
    public void bluetoothDeviceBondStateChanged(Bluetooth bluetooth, BluetoothDevice bluetoothDevice, int i, int i1) {

    }

    @Override
    public void bluetoothDeviceConnected(Bluetooth bluetooth, BluetoothDevice bluetoothDevice, BluetoothSocket bluetoothSocket) {
        System.out.println("wow");
        headphCtrl = HeadPhoneCtrl.getInstance(this, bluetoothSocket);
        headphCtrl.commonCtrl.set9AxisPushFrequency(0);
        headphCtrl.commonCtrl.set9AxisSensorStatus(true);
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        //MetronomeListener metronome = new MetronomeListener();
        headphCtrl.setCommonListner(new MetronomeListener());
        headphCtrl.commonCtrl.get9AxisRawData();
        if (headphCtrl != null && headphCtrl.getSocket().equals(bluetoothSocket)) {
            System.out.println();
            headphCtrl.resetHeadPhoneCtrl(bluetoothSocket);
        } else {
            try {
                headphCtrl.close();
                headphCtrl = null;
                return;
            } catch (Exception e) {
            }
            // use this to interface with the headphones
            headphCtrl = HeadPhoneCtrl.getInstance(callingActivity, bluetoothSocket);
            //
        }
    }

    @Override
    public void bluetoothDeviceDisconnected(Bluetooth bluetooth, BluetoothDevice bluetoothDevice) {
        Log.d("EVEREST", "disconnected");
        headphCtrl = null;
    }

    @Override
    public void bluetoothDeviceDiscovered(Bluetooth bluetooth, BluetoothDevice bluetoothDevice) {

    }

    @Override
    public void bluetoothDeviceFailedToConnect(Bluetooth bluetooth, BluetoothDevice bluetoothDevice, Exception e) {

    }
}