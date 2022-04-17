package coms309.songusoid;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Garrett on 10/1/2017.
 * This class contains the methods to define a song based on a sequence
 * of frequencies with durations and delays
 */
public class SongAlgo {

    //Arrays to store details of a song
    private int[] frequency;
    private int[] duration;
    private int[] delay;

    //Some common tones
    private int numTones = 5;
    private int toneC = 260;
    private int toneD = 290;
    private int toneE = 330;
    private int toneG = 390;
    private int toneA = 440;

    private Context context;

    public SongAlgo() {

    }

    /**
     * Temporary test algorithm to see if we can create and play songs
     * @param numKeys
     * the number of notes contained in this song
     */
    public void testAlgo(int numKeys) {
        Random rand = new Random();

        frequency = new int[numKeys];
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
            duration[i] = rand.nextInt(1000) + 200;  //duration between 200 and 1200 milliseconds
            delay[i] = rand.nextInt(1400) + 100;     //delay between 100 and 1500 milliseconds
        }
    }

    /**
     * Creates a new song by sorting an array using bubble sort
     */
    public void bubbleAlgo() {
        Random rand = new Random();

        ArrayList<Integer> frequency = new ArrayList<Integer>();
        ArrayList<Integer> duration = new ArrayList<Integer>();
        ArrayList<Integer> delay = new ArrayList<Integer>();

        int[] toSort = new int[10];
        for(int i = 0; i < 10; i++) {
            toSort[i] = rand.nextInt(numTones);
        }

        boolean done = false;
        int temp;
        boolean changed = false;
        while(!done) {
            for(int i = 1; i < 10; i++) {
                if(toSort[i] < toSort[i-1]) {
                    changed = true;
                    temp = toSort[i-1];
                    toSort[i-1] = toSort[i];
                    toSort[i] = temp;

                    switch(toSort[i]) {
                        case 0:
                            frequency.add(toneC);
                            break;
                        case 1:
                            frequency.add(toneD);
                            break;
                        case 2:
                            frequency.add(toneE);
                            break;
                        case 3:
                            frequency.add(toneG);
                            break;
                        case 4:
                            frequency.add(toneA);
                            break;
                    }

                    //randomize duration and delay
                    duration.add(rand.nextInt(1000) + 200);  //duration between 200 and 1200 milliseconds
                    delay.add(rand.nextInt(1000) + 100);     //delay between 100 and 1100 milliseconds
                }
            }
            if(changed) {
                changed = false;
            }
            else {
                done = true;
            }
        }

        this.frequency = new int[frequency.size()];
        this.duration = new int[duration.size()];
        this.delay = new int[delay.size()];

        for(int i = 0; i < frequency.size(); i++) {
            this.frequency[i] = frequency.get(i);
            this.duration[i] = duration.get(i);
            this.delay[i] = delay.get(i);
        }
    }

    /**
     * Creates a new song by sweeping frequencies
     */
    public void peterScale() {
        Random rand = new Random();

        ArrayList<Integer> frequency = new ArrayList<Integer>();
        ArrayList<Integer> duration = new ArrayList<Integer>();
        ArrayList<Integer> delay = new ArrayList<Integer>();

        int loops = 7;
        int noteA;
        int noteB;

        for(int i = 0; i < 7; i++) {
            noteA = rand.nextInt(numTones);
            noteB = rand.nextInt(numTones);

            if(noteA > noteB) {
                for(int j = noteA; noteA >= noteB; noteA--) {
                    switch(j) {
                        case 0:
                            frequency.add(toneC);
                            break;
                        case 1:
                            frequency.add(toneD);
                            break;
                        case 2:
                            frequency.add(toneE);
                            break;
                        case 3:
                            frequency.add(toneG);
                            break;
                        case 4:
                            frequency.add(toneA);
                            break;
                    }
                    //randomize duration and delay
                    duration.add(rand.nextInt(1000) + 200);  //duration between 200 and 1200 milliseconds
                    delay.add(rand.nextInt(1000) + 100);     //delay between 100 and 1100 milliseconds
                }
            }

            else {
                for(int j = noteA; noteA <= noteB; noteA++) {
                    switch(j) {
                        case 0:
                            frequency.add(toneC);
                            break;
                        case 1:
                            frequency.add(toneD);
                            break;
                        case 2:
                            frequency.add(toneE);
                            break;
                        case 3:
                            frequency.add(toneG);
                            break;
                        case 4:
                            frequency.add(toneA);
                            break;
                    }
                    //randomize duration and delay
                    duration.add(rand.nextInt(1000) + 200);  //duration between 200 and 1200 milliseconds
                    delay.add(rand.nextInt(1000) + 100);     //delay between 100 and 1100 milliseconds
                }
            }
        }

        this.frequency = new int[frequency.size()];
        this.duration = new int[duration.size()];
        this.delay = new int[delay.size()];

        for(int i = 0; i < frequency.size(); i++) {
            this.frequency[i] = frequency.get(i);
            this.duration[i] = duration.get(i);
            this.delay[i] = delay.get(i);
        }
    }

    public void setContext(Context givenContext){
        this.context = givenContext;
    }

    public void debugAlgo() {
        frequency = new int[5];
        duration = new int[5];
        delay = new int[5];

        frequency[0] = toneA;
        frequency[1] = toneC;
        frequency[2] = toneD;
        frequency[3] = toneE;
        frequency[4] = toneG;

        duration[0]=duration[1]=duration[2]=duration[3]=duration[4] = 1000;
        delay[0]=delay[1]=delay[2]=delay[3]=delay[4] = 1500;
    }

    public void lilBigSong() {
        Random rand = new Random();

        //create a new song containing 3-7 notes
        int numKeys = rand.nextInt(4)+3;

        testAlgo(numKeys);
    }

    /**
     * Returns the frequency array for this song
     * @return
     */
    public int[] getFrequency(){
        return this.frequency;
    }

    /**
     * Returns the duration array for this song
     * @return
     */
    public int[] getDuration() {
        return this.duration;
    }

    /**
     * Returns the delay array for this song
     * @return
     */
    public int[] getDelay() {
        return this.delay;
    }

    /**
     * Sets the frequencies for this song
     * @param frequency
     * list of frequencies
     */
    public void setFrequency(int[] frequency) {
        this.frequency = frequency;
    }

    /**
     * Sets the durations for this song
     * @param duration
     * list of durations
     */
    public void setDuration(int[] duration){
        this.duration = duration;
    }

    /**
     * Sets the delays for this song
     * @param delay
     * list of delays
     */
    public void setDelay(int[] delay) {
        this.delay = delay;
    }

    /**
     * Creates a clone of the given song
     * @param song
     * Song to be cloned
     * @return
     * Deep copy of the given song
     */
    public static SongAlgo clone(SongAlgo song) {
        SongAlgo temp = new SongAlgo();
        temp.setFrequency(song.getFrequency());
        temp.setDuration(song.getDuration());
        temp.setDelay(song.getDelay());

        return temp;
    }

    public void play(int instrument) {
        //Use the defined song to create a playable song
        SongCreate songToPlay = new SongCreate(this, this.context);

        //create a playable song and then play it
        //songToPlay.genSong();
        songToPlay.playSong(instrument);
    }
}
