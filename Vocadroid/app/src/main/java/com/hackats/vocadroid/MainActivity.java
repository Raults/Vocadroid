package com.hackats.vocadroid;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.harman.pulsesdk.DeviceModel;
import com.harman.pulsesdk.PulseColor;
import com.harman.pulsesdk.PulseThemePattern;
import com.harman.pulsesdk.PulseHandlerInterface;
import com.harman.pulsesdk.PulseNotifiedListener;
import com.harman.pulsesdk.ImplementPulseHandler;

public class MainActivity extends AppCompatActivity {

    PulseHandlerInterface pulseHander;
    Activity thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pulseHander = new ImplementPulseHandler();


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PulseColor[] pixes = new PulseColor[99];
                for (int i = 0; i < 99; i++) {
                    //set image by yourself
                    if (i == 24 || i == 25 || i == 34 || i == 33) {
                        pixes[i] = new PulseColor((byte) 255, (byte) 255, (byte) 255);
                    } else {
                        pixes[i] = new PulseColor((byte) 0, (byte) 255, (byte) 0);
                    }
                }
                pulseHander.SetColorImage(pixes);

            }
        });
    }

    public void connect(View view) {
        pulseHander.ConnectMasterDevice(this);
    }
}
