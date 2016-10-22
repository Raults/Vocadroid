package com.hackats.vocadroid;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class TTSTest extends AppCompatActivity {
    Context thisContext = this;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttstest);
        tts = new TextToSpeech(thisContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);

                }
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String text = ((TextView) findViewById(R.id.editText)).getText().toString();
                    HashMap<String, String> hashRender = new HashMap<>();
                    hashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, text);
                    File tempFile = new File(getFilesDir(), text + ".wav");
                    tempFile.delete();
                    tts.synthesizeToFile(text, hashRender, getFilesDir() + "/" + text + ".wav");
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {

                        }

                        @Override
                        public void onDone(String utteranceId) {
                            if (utteranceId.equals(text)) {
                                File toPlay = new File(getFilesDir(), text + ".wav");
                                MediaPlayer player = MediaPlayer.create(thisContext, Uri.fromFile(toPlay));
                                player.start();
                            }
                        }

                        @Override
                        public void onError(String utteranceId) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
