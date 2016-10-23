package com.hackats.vocadroid;

public class Data {
    static final int CHANNELS = 1;
    static final int SAMPLE_RATE = 22050;
    static final int SR_BPS_C = 44100;
    static final int BITS_PER_SAMPLE_CHANNEL_8 = 2;
    static final int BITS_PER_SAMPLE = 16;
    byte[] byteData;
    int loopStart;
    int loopStop;

    public void seekLoop() {
        int max = 0;
        int index = 0;
        for (int i = 44; i < byteData.length; i += 2) {
            int current = getIntFromBytes(byteData[i], byteData[i + 1]);
            if (current > max) {
                max = current;
                index = i;
            }
        }
    }

    public static int getIntFromBytes(byte b1, byte b2) {
        return ((b1 & 0xFF) << 8) + (b2 & 0xFF);
    }
}
