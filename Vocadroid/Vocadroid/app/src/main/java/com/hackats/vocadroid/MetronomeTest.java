package com.hackats.vocadroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MetronomeTest extends AppCompatActivity {

    Metronome met;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome_test);
        try {
            met = new Metronome();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
