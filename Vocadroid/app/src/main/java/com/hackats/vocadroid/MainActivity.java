package com.hackats.vocadroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
        pulseHander.registerPulseNotifiedListener(new PulseNotifiedListener() {
            @Override
            public void onConnectMasterDevice() {

            }

            @Override
            public void onDisconnectMasterDevice() {

            }

            @Override
            public void onRetBrightness(int i) {

            }

            @Override
            public void onLEDPatternChanged(PulseThemePattern pulseThemePattern) {

            }

            @Override
            public void onSoundEvent(int i) {

            }

            @Override
            public void onRetCaptureColor(PulseColor pulseColor) {

            }

            @Override
            public void onRetCaptureColor(byte b, byte b1, byte b2) {

            }

            @Override
            public void onRetSetDeviceInfo(boolean b) {

            }

            @Override
            public void onRetGetLEDPattern(PulseThemePattern pulseThemePattern) {

            }

            @Override
            public void onRetRequestDeviceInfo(DeviceModel[] deviceModels) {

            }

            @Override
            public void onRetSetLEDPattern(boolean b) {

            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pulseHander.ConnectMasterDevice(thisContext);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PulseColor[] pixes = new PulseColor[99];
                Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
                Bitmap newBitmap = Bitmap.createBitmap(11, 9, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(newBitmap);
                Rect src = new Rect(0, 0, original.getWidth(), original.getHeight());
                Rect dest = new Rect(0, 0, newBitmap.getWidth(), newBitmap.getHeight());
                canvas.drawBitmap(original, src, dest, null);
                ((ImageView) findViewById(R.id.imageView2)).setImageBitmap(newBitmap);
                for (int i = 0; i < 99; i++) {
                    int x = i % 11;
                    int y = i / 11;
                    int current = newBitmap.getPixel(x, y);
                    pixes[i] = new PulseColor((byte) ((current >> 16) & 0xFF),
                            (byte) ((current >> 8) & 0xFF),
                            (byte) (current & 0xFF));
                }
                pulseHander.SetColorImage(pixes);

            }
        });
    }
}
