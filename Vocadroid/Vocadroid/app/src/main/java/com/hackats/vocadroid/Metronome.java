package com.hackats.vocadroid;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.TextView;

import com.harman.everestelite.Bluetooth;
import com.harman.everestelite.BluetoothListener;
import com.harman.everestelite.CommonListner;
import com.harman.everestelite.HeadPhoneCtrl;
import com.harman.everestelite.Log;

import java.io.IOException;

/**
 * Created by rhotate on 10/22/16.
 */

public class Metronome extends Activity implements BluetoothListener, CommonListner {
    // This class takes an instance of the BluetoothListener interface, Activity, and a boolean for secure (true)
    // or insecure (false) RF socket connection.
    Bluetooth mBluetooth = new Bluetooth(this, this, true);

    public Metronome() throws IOException {
    }

    public void bluetoothStart(){
        mBluetooth.start();
    }

    // declare the variable..
    private HeadPhoneCtrl headphCtrl;

    @Override
    public void bluetoothAdapterChangedState(Bluetooth bluetooth, int i, int i1) {

    }

    @Override
    public void bluetoothDeviceBondStateChanged(Bluetooth bluetooth, BluetoothDevice bluetoothDevice, int i, int i1) {

    }

    @Override
    public void bluetoothDeviceConnected(Bluetooth bluetooth, BluetoothDevice bluetoothDevice, BluetoothSocket bluetoothSocket) {
        if (headphCtrl != null && headphCtrl.getSocket().equals(bluetoothSocket)) {
            headphCtrl.resetHeadPhoneCtrl(bluetoothSocket);
        } else {
            try {
                headphCtrl.close();
                headphCtrl = null;
            } catch (Exception e) {
            }
            // use this to interface with the headphones
            headphCtrl = HeadPhoneCtrl.getInstance(this, bluetoothSocket);
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

    @Override
    public void getProgrammableIndexButtonReply(int i) {

    }

    @Override
    public void getConfigModelNumberReply(String s) {

    }

    @Override
    public void getConfigProductNameReply(String s) {

    }

    @Override
    public void getAutoOffFeatureReply(boolean b) {

    }

    @Override
    public void getEnableVoicePromptReply(boolean b) {

    }

    @Override
    public void getFirmwareVersionReply(int i, int i1, int i2) {

    }

    @Override
    public void waitCommandReplyElapsedTime(int i) {

    }

    @Override
    public void headPhoneError(Exception e) {

    }

    @Override
    public void setAutoOffFeatureReply(boolean b) {

    }

    @Override
    public void setEnableVoicePromptReply(boolean b) {

    }

    @Override
    public void getCustomButtonReply() {

    }

    @Override
    public void get9AxisRawDataReply(double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15, double var17){
        ((TextView)findViewById(R.id.textView2)).setText("x: " + var1 + var7 + var13);
        ((TextView)findViewById(R.id.textView5)).setText("y: " + var3 + var9 + var15);
        ((TextView)findViewById(R.id.textView6)).setText("z: " + var5 + var9 + var17);
    }

    // If the
    @Override
    public void get9AxisSensorStatusReply(boolean var1){
        if(!var1){
            set9AxisSensorStatusReply(true);
        }
    }

    @Override
    public void get9AxisPushFrequencyReply(int i) {

    }

    @Override
    public void set9AxisSensorStatusReply(boolean b) {

    }

    @Override
    public void set9AxisPushFrequencyReply(boolean b) {

    }
}
