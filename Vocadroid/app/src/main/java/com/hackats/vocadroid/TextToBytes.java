package com.hackats.vocadroid;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class TextToBytes {
    static public void generateBytes(final String appPath, final String text, final HashMap<String, Data> dataMap, TextToSpeech tts) {
        try {
            HashMap<String, String> hashRender = new HashMap<>();
            hashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, text);
            File tempFile = new File(appPath, text + ".wav");
            tempFile.delete();
            tts.synthesizeToFile(text, hashRender, appPath + "/" + text + ".wav");
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    if (utteranceId.equals(text)) {
                        File tempFile = new File(appPath, text + ".wav");
                        int fileSize = (int) tempFile.length();
                        Data data = new Data();
                        data.byteData = new byte[fileSize];
                        try {
                            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(tempFile));
                            inputStream.read(data.byteData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dataMap.put(text, data);
                        /*
                        int minBufferSize = AudioTrack.getMinBufferSize(Data.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                                AudioFormat.ENCODING_PCM_16BIT);
                        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, Data.SAMPLE_RATE,
                                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize, AudioTrack.MODE_STREAM);
                        audioTrack.play();
                        audioTrack.write(data.byteData, 0, data.byteData.length);

                        String outputString = "";
                        for (int i = 0; i < 44; i++) {
                            String temp = String.format("%8s", Integer.toBinaryString(data.byteData[i] & 0xFF)).replace(' ', '0');
                            outputString += temp + " ";
                        }

                        outputString += "\nSize: ";
                        outputString += Long.toString(getLongFromBytes(data.byteData[7], data.byteData[6], data.byteData[5], data.byteData[4]));
                        outputString += "\n17-20: " + Long.toString(getLongFromBytes(data.byteData[19], data.byteData[18], data.byteData[17], data.byteData[16]));
                        outputString += "\nPCM: " + Long.toString(getLongFromBytes((byte) 0, (byte) 0, data.byteData[21], data.byteData[20]));
                        outputString += "\nChannels: " + Long.toString(getLongFromBytes((byte) 0, (byte) 0, data.byteData[23], data.byteData[22]));
                        outputString += "\nSample rate: " + Long.toString(getLongFromBytes(data.byteData[27], data.byteData[26], data.byteData[25], data.byteData[24]));
                        outputString += "\n29-32: " + Long.toString(getLongFromBytes(data.byteData[31], data.byteData[30], data.byteData[29], data.byteData[28]));
                        outputString += "\nBits per sample * channels: " + Long.toString(getLongFromBytes((byte) 0, (byte) 0, data.byteData[33], data.byteData[32]));
                        outputString += "\nBits per sample: " + Long.toString(getLongFromBytes((byte) 0, (byte) 0, data.byteData[35], data.byteData[34]));
                        outputString += "\nFile size: " + Long.toString(getLongFromBytes(data.byteData[43], data.byteData[42], data.byteData[41], data.byteData[40]));
                        System.out.println(outputString);
                        System.out.println(data.byteData.length);*/
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
    public static long getLongFromBytes(byte b1, byte b2, byte b3, byte b4) {
        long result = 0;
        result |= (b1 & 0xFF);
        result <<= 8;
        result |= (b2 & 0xFF);
        result <<= 8;
        result |= (b3 & 0xFF);
        result <<= 8;
        result |= (b4 & 0xFF);
        return result;
    }
}

