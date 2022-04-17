package com.example.tfrit.soundpoolattempt;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    void playFlute(View v) {
        SoundPoolPlayer sound = new SoundPoolPlayer(this);
        Random r = new Random();
        float random = .5f + r.nextFloat() * (1.5f - .5f);
        sound.playShortResource(R.raw.flute, random);
        Cleanup cleanup = new Cleanup();
        cleanup.setSoundPool(sound);
        cleanup.execute();
    }

    private class Cleanup extends AsyncTask<String, Void, String> {

        private SoundPoolPlayer soundPool;

        void setSoundPool(SoundPoolPlayer soundPool){
            this.soundPool = soundPool;

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            soundPool.release();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }




}
