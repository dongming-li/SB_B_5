package coms309.songusoid;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by tfrit on 11/21/2017.
 */

public class SoundPoolPlayer {
    private SoundPool mShortPlayer= null;
    private HashMap mSounds = new HashMap();
    private int noteDuration;

    public SoundPoolPlayer(Context pContext, int instrument)
    {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mShortPlayer.setLoop(1,0);
        mSounds.put(instrument, this.mShortPlayer.load(pContext, instrument, 1));
        mSounds.put(instrument, this.mShortPlayer.load(pContext, instrument, 1));
    }

    public void playShortResource(int piResource, float speed) {
        final int iSoundId = (Integer) mSounds.get(piResource);
        final float playbackSpeed = speed;
        mShortPlayer.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(iSoundId,100, 100, 0, 0, playbackSpeed);
                Cleanup cleanup = new Cleanup();
                cleanup.setSoundPool(soundPool, noteDuration);
                cleanup.execute();
            }
        });
    }

    // Cleanup
    public void release() {
        // Cleanup

        if (mShortPlayer != null){
            mShortPlayer.unload(1);
            this.mShortPlayer.release();
            this.mShortPlayer = null;
        }

    }


    void play(Context context, float playSpeed, int instrument, int duration){
        SoundPoolPlayer sound = new SoundPoolPlayer(context,instrument);
        this.noteDuration = duration;
        sound.playShortResource(instrument, playSpeed);

    }

    private class Cleanup extends AsyncTask<String, Void, String> {

        private SoundPool soundPool;
        private int noteDuration;

        void setSoundPool(SoundPool soundPool, int duration){
            this.soundPool = soundPool;
            this.noteDuration = duration;

        }

        @Override
        protected String doInBackground(String... params) {
            try{
                Thread.sleep(noteDuration);
            } catch(InterruptedException e ) {
                soundPool.release();
            }
            soundPool.release();

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }







}


