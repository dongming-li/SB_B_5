package com.example.rockm.learningfragments;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class createSong extends AppCompatActivity {
    private final int duration = 2; // seconds
    private final int sampleRate = 8000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private double freqOfTone; // hz
    private boolean isPlayingC;
    private boolean isPlayingD;
    private boolean isPlayingE;
    private boolean isPlayingG;
    private boolean isPlayingA;

    private final byte generatedSnd[] = new byte[2 * numSamples];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_song2);
        freqOfTone = 440;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    void genTone(){
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }

    void gen2Tone(double tone1, double tone2){
        // fill out the array
        for (int i = 0; i < numSamples/2; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/tone1));
        }
        for (int i = numSamples/2; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/tone2));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }

    void playSound(){
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STREAM);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }
    void playC(View v) {
        freqOfTone = 261.626;
        genTone();
        playSound();

    }

    void playD(View v) {
        freqOfTone = 293.665;
        // Use a new tread as this can take a while
        genTone();
        playSound();
    }

    void playE(View v) {
        freqOfTone =  329.628;
        // Use a new tread as this can take a while

        genTone();
        playSound();
    }

    void playG(View v) {
        freqOfTone = 391.995;
        // Use a new tread as this can take a while
        genTone();
        playSound();
    }

    void playA(View v) {
        freqOfTone = 440.000;
        // Use a new tread as this can take a while
        genTone();
        playSound();
    }


}
