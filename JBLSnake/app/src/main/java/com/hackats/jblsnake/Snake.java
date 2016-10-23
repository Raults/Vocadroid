package com.hackats.jblsnake;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.harman.pulsesdk.DeviceModel;
import com.harman.pulsesdk.ImplementPulseHandler;
import com.harman.pulsesdk.PulseColor;
import com.harman.pulsesdk.PulseHandlerInterface;
import com.harman.pulsesdk.PulseNotifiedListener;
import com.harman.pulsesdk.PulseThemePattern;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Snake extends AppCompatActivity {
    int mode = 0;
    int direction = 0;
    int[][] field = new int[9][11];
    Point head;
    ArrayList<Point> body = new ArrayList<>();
    Timer timer;
    Point prey;
    PulseHandlerInterface pulseHander;
    Activity thisContext = this;
    float prevX = -1, prevY = -1;
    int invincible = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

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
        timer = new Timer();
        findViewById(R.id.play).setVisibility(View.GONE);
        findViewById(R.id.activity_snake).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mode == 1) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            float currentX = event.getX();
                            float currentY = event.getY();
                            if (prevX >= 0 && prevY >= 0) {
                                float deltaX = currentX - prevX;
                                float deltaY = currentY - prevY;
                                if (Math.sqrt(deltaX * deltaX + deltaY * deltaY) > 5) {
                                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
                                        if (deltaX > 0) {
                                            direction = 0;
                                        }
                                        if (deltaX < 0) {
                                            direction = 2;
                                        }
                                    } else {
                                        if (deltaY > 0) {
                                            direction = 1;
                                        }
                                        if (deltaY < 0) {
                                            direction = 3;
                                        }
                                    }
                                }
                            }
                            prevX = currentX;
                            prevY = currentY;
                            break;
                        case MotionEvent.ACTION_UP:
                            prevX = -1;
                            prevY = -1;
                            break;
                    }
                }
                return true;
            }
        });
        findViewById(R.id.newGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pulseHander.ConnectMasterDevice(thisContext);
                startOver();
                findViewById(R.id.play).setVisibility(View.VISIBLE);
                findViewById(R.id.newGame).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode == 1) mode = 3;
                else if (mode == 3) mode = 1;
            }
        });
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mode == 1) {
                    move();
                    render();
                } else if(mode == 2) {
                    System.out.println("waaa");
                    for (int i = 0; i < 10; i++) {
                        field[(int) ((Math.random() - .001) * 9)][(int) ((Math.random() - .001) * 11)]
                                = (int) ((Math.random() - .001) * 4);
                    }
                    render();
                }
            }
        }, 0, 400);
    }

    void move() {
        if (!body.isEmpty()) {
            for (int i = body.size() - 1; i > 0; i--) {
                body.get(i).x = body.get(i - 1).x;
                body.get(i).y = body.get(i - 1).y;
            }
            body.get(0).x = head.x;
            body.get(0).y = head.y;
        }
        switch (direction) {
            case 0:
                head.x ++;
                break;
            case 1:
                head.y ++;
                break;
            case 2:
                head.x --;
                break;
            case 3:
                head.y --;
                break;
        }
        if (head.x > 10) head.x = 0;
        if (head.x < 0) head.x = 10;
        if (head.y > 8) head.y = 0;
        if (head.y < 0) head.y = 8;
        if (overlap(head, prey)) {
            if (body.isEmpty()) {
                body.add(new Point(head.x, head.y));
            } else {
                Point tail = body.get(body.size() - 1);
                body.add(new Point(tail.x, tail.y));
            }
            invincible = 1;
            randomizePrey();
        }
        render();
        for (Point bodyPoint: body) {
            if (overlap(bodyPoint, head)) {
                if (invincible-- <= 0) {
                    gameOver();
                    break;
                }
            }
        }
    }

    boolean overlap(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    void randomizePrey() {
        boolean keepSearching = true;
        int x;
        int y;
        do {
            x = (int) ((Math.random() + .1) * 10);
            y = (int) ((Math.random() + .1) * 8);
            keepSearching = false;
            if (head.x == x && head.y == y) keepSearching = true;
            for (Point bodyPoint: body) {
                if (bodyPoint.x == x && bodyPoint.y == y) keepSearching = true;
            }
        } while (keepSearching);
        prey = new Point(x, y);
    }
    void startOver() {
        head = new Point(5, 4);
        body.clear();
        randomizePrey();
        mode = 1;
    }

    void gameOver() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.newGame).setVisibility(View.VISIBLE);
            }
        });
        mode = 2;
    }

    void render() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mode == 1) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 11; j++) {
                            field[i][j] = 0;
                        }
                    }
                    field[prey.y][prey.x] = 3;
                    field[head.y][head.x] = 1;
                    for (Point bodyPoint : body) {
                        field[bodyPoint.y][bodyPoint.x] = 2;
                    }
                }
                PulseColor[] pixes = new PulseColor[99];

                for (int i = 0; i < 99; i++) {
                    switch (field[i / 11][i % 11]) {
                        case 0:
                            pixes[i] = new PulseColor((byte) 0, (byte) 0, (byte) 0);
                            break;
                        case 1:
                            pixes[i] = new PulseColor((byte) 64, (byte)128, (byte) 0);
                            break;
                        case 2:
                            pixes[i] = new PulseColor((byte) 0, (byte)64, (byte) 128);
                            break;
                        case 3:
                            pixes[i] = new PulseColor((byte) 0, (byte)0, (byte) 200);
                            break;
                    }
                }
                pulseHander.SetColorImage(pixes);
            }
        });
    }
}
