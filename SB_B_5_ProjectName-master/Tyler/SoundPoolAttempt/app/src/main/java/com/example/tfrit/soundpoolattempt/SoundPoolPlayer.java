package com.example.tfrit.soundpoolattempt;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by tfrit on 11/21/2017.
 */

public class SoundPoolPlayer{
    private SoundPool mShortPlayer= null;
    private HashMap mSounds = new HashMap();

    public SoundPoolPlayer(Context pContext)
    {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);


        mSounds.put(R.raw.flute, this.mShortPlayer.load(pContext, R.raw.flute, 1));
        mSounds.put(R.raw.flute, this.mShortPlayer.load(pContext, R.raw.flute, 1));
    }

    public void playShortResource(int piResource, float speed) {
        final int iSoundId = (Integer) mSounds.get(piResource);
        final float playbackSpeed = speed;
        mShortPlayer.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(iSoundId,100, 100, 0, 0, playbackSpeed);

            }
        });
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }







}


