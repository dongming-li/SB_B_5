package coms309.songgen;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Steve Pomeroy <steve@staticfree.info>
    private final int sampleRate = 8000;  //number of samples per second
    //Arrays to store details of a song
    private double[] frequency;
    private int[] duration;
    private int[] delay;
    private byte[] generatedSnd;

    //Some common tones
    int numTones = 5;
    double toneC = 261.626;
    double toneD = 293.665;
    double toneE = 329.628;
    double toneG = 391.995;
    double toneA = 440.000;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * This function creates a song from an array input of frequencies, durations, and delays.
     */
    void genSong(){
        double[] song;
        int numSamples;
        int sampleCount;
        //store sampleRate as a double for integer multiplication, covert to samples/millisecond
        double sampleRate = (double)this.sampleRate/1000;

        //begin by determining the total number of samples required for the song.
        //if index out of bound errors occur, see if int cast is causing too few indices
        numSamples = 0;
        for(int i = 0; i < frequency.length; i++) {
            //add the number of samples until the next tone
            if((i+1) == frequency.length) {
                //if this is the last note, ignore the delay
                numSamples += (int)(sampleRate*duration[i]);
            }
            else {
                //add number of samples until next tone
                numSamples += (int) (sampleRate * delay[i]);
            }
        }

        //initialize the array to store our song, initialize generatedSnd to covert song
        song = new double[numSamples];
        generatedSnd = new byte[2 * numSamples];

        //Add each tone into the song array
        sampleCount = 0;
        for(int i = 0; i < frequency.length; i++) {
            //For each tone, add numSamples samples to the song
            numSamples = (int)(sampleRate*duration[i]);
            for(int j = 0; j < numSamples; j++) {
                //sample = sin(2*pi*freq*(sampleNum/sampleRate))
                song[sampleCount + j] += Math.sin(2*Math.PI*j/(this.sampleRate/frequency[i]));
            }
            //start new note after the delay of the last note
            sampleCount += (int)(sampleRate*delay[i]);
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : song) {
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
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

    /**
     * Create and play a random song
     * @param v
     * view
     */
    public void playSong(View v) {
        int numKeys = 8;  //number of tones to play
        Random rand = new Random();

        frequency = new double[numKeys];
        duration = new int[numKeys];
        delay = new int[numKeys];

        //Create a sequency of random tones with random durations and delays
        for(int i = 0; i < numKeys; i++) {

            //randomize the tone
            switch (rand.nextInt(numTones)) {
                case 0:

                    frequency[i] = toneC;
                    break;
                case 1:
                    frequency[i] = toneD;
                    break;
                case 2:
                    frequency[i] = toneE;
                    break;
                case 3:
                    frequency[i] = toneG;
                    break;
                case 4:
                    frequency[i] = toneA;
                    break;
            }

            //randomize duration and delay
            duration[i] = rand.nextInt(1200) + 300;  //duration between 300 and 1500 milliseconds
            delay[i] = rand.nextInt(1700) + 300;     //delay between 300 and 2000 milliseconds
        }

        //The song has been defined, now create and play it.
        //Create a new thread as this can take a while
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                genSong();
                handler.post(new Runnable() {

                    public void run() {
                        playSound();
                    }
                });
            }
        });
        thread.start();
    }
}
