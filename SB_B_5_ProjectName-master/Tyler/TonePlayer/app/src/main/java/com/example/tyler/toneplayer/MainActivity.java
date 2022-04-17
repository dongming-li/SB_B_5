package com.example.tyler.toneplayer;

import android.app.DownloadManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static android.R.attr.delay;
import static android.R.attr.offset;


public class MainActivity extends AppCompatActivity {

    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Steve Pomeroy <steve@staticfree.info>
    private int duration = 2; // seconds
    private int sampleRate = 8000;
    private int numSamples = duration * sampleRate;
    private double sample[] = new double[numSamples];
    private double song[];
    private double freqOfTone; // hz
    private boolean isPlayingC;
    private boolean isPlayingD;
    private boolean isPlayingE;
    private boolean isPlayingG;
    private boolean isPlayingA;

    private final byte generatedSnd[] = new byte[2 * numSamples];

    Handler handler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        freqOfTone = 440;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    void genTone(){
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone))
                    + .4f * Math.sin(2 * Math.PI * i / (sampleRate/(freqOfTone * 2)))
                    + .1f * Math.sin(2 * Math.PI * i / (sampleRate/(freqOfTone * 3)))
                    + .1f * Math.sin(2 * Math.PI * i / (sampleRate/(freqOfTone * 4)))
                    + .4f * Math.sin(2 * Math.PI * i / (sampleRate/(freqOfTone * 5)))
                    + .1f * Math.sin(2 * Math.PI * i / (sampleRate/(freqOfTone * 6)))
                    + .1f * Math.sin(2 * Math.PI * i / (sampleRate/(freqOfTone * 7)));
        }

        for (double sampleOne: sample){
            Log.e("Val", "" + sampleOne);
        }

        int idx = 0;

        for (int i = 0; i < numSamples; i++) {
            double dVal = sample[i];

            final short val;

            //fade out logic
            if (i >= numSamples * .9f) {
                val = (short) ((dVal * 5000 * ((numSamples-i)/(numSamples * .1f))));
            }
            //normal playing logic
            else {
                val = (short) ((dVal * 5000));
            }

            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }

    void playSound(){
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STREAM);

        audioTrack.play();
        audioTrack.pause();
        audioTrack.setPlaybackHeadPosition(100);

        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }
    void playC(View v) throws Exception{
        freqOfTone = 103.83;
        // Use a new tread as this can take a while
        genTone();
        playSound();

    }

    void playD(View v) {
        freqOfTone = 138.59;
        // Use a new tread as this can take a while
        genTone();
        playSound();
    }

    void playE(View v) {
        freqOfTone =  185.00;
        // Use a new tread as this can take a while

        genTone();
        playSound();
    }

    void playG(View v) {
        freqOfTone = 233.08;
        // Use a new tread as this can take a while
        genTone();
        playSound();
    }

    void playA(View v) {
        freqOfTone = 108.00;
        // Use a new tread as this can take a while
        genTone();
        playSound();
    }



    public boolean play() {
        int minBufferSize = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
        int bufferSize = 512;
        AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize, AudioTrack.MODE_STREAM);

        int i = 0;
        byte[] music = null;
        InputStream is = this.getResources().openRawResource(R.raw.instrument);

        at = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize, AudioTrack.MODE_STREAM);

        try{
            music = new byte[512];
            at.play();

            while((i = is.read(music)) != -1)
                at.write(music, 0, i);

        } catch (IOException e) {
            e.printStackTrace();
        }

        at.stop();
        at.release();
        return true;
    }




}
