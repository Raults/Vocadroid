package com.hackats.vocadroid;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.harman.everestelite.CommonListner;

/**
 * Created by rhotate on 10/23/16.
 */

// Listener class
public class MetronomeListener extends AppCompatActivity implements CommonListner {

    long time = 0;

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

    //public double varArray[];

    @Override
    public void get9AxisRawDataReply(double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15, double var17){
        /*double tempArr[] = {var1, var3, var5, var7, var9, var11, var13, var15, var17};
        varArray = tempArr;*/
        long currTime = System.currentTimeMillis();
        long delta = currTime - time;
        System.out.println(delta + " ");
        time = currTime;
    }

    // Return the global values in a usable format
    /*public double[] get9AxisRawData(){
        return varArray;
    }*/

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