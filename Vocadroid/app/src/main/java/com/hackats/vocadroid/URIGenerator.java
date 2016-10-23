package com.hackats.vocadroid;

import android.speech.tts.TextToSpeech;

import java.io.File;
import java.net.URI;
import java.util.HashMap;

public class URIGenerator {
    public void generateURI(String appPath, String text, HashMap<String, URI> map, TextToSpeech tts) {
        try {
            File tempFile = new File(appPath, text + ".wav");
            HashMap<String, String> renderHash = new HashMap<>();

            tts.synthesizeToFile()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
