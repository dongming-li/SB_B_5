package coms309.songusoid;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Garrett on 10/21/2017.
 * Helper class to store information for the lil Big Song algorithm song creation
 */
public class LilBigSong {

    //List of lil songs
    private SongAlgo[] lilSongs;
    //Count for current song
    private int lilSongCount;
    //keeps track of index for currently selected lil song
    private int lilSongSelected;
    //keeps track of size of big song
    private int bigSize;
    //keeps track of which lil songs are in the big song
    private ArrayList<Integer> bigSongOrder;

    //keeps track of the information that makes this lil big song
    private ArrayList<Integer> frequencies;
    private ArrayList<Integer> durations;
    private ArrayList<Integer> delays;

    /**
     * Creates a new lilBigSong
     */
    public LilBigSong() {
        lilSongs = new SongAlgo[6];
        lilSongCount = 0;
        lilSongSelected = 0;
        bigSize = 0;

        frequencies = new ArrayList<Integer>();
        durations = new ArrayList<Integer>();
        delays = new ArrayList<Integer>();

        bigSongOrder = new ArrayList<Integer>();
    }

    /**
     * Adds the given song to the lil songs list
     * @param song
     * song to add to lil songs
     */
    public void addLilSong(SongAlgo song) {
        lilSongs[lilSongCount++] = song;
    }

    /**
     * Determines if lil song list is full
     * @return
     * true if full, false otherwise
     */
    public boolean isLilFull() {
        return lilSongCount == 6;
    }

    /**
     * Plays the lil song at the given index
     * @param index
     * index of the lil song you want to play
     */
    public void playLilSong(int index, Context context, int instrument) {
        SongAlgo lilSong = lilSongs[index];

        //Use the defined song to create a playable song
        SongCreate songToPlay = new SongCreate(lilSong,context);

        //create a playable song and then play it
        //songToPlay.genSong();
        songToPlay.playSong(instrument);
    }

    /**
     * Sets the currently selected lil song
     * @param index
     * index of currently selected lil song
     */
    public void setLilSongSelected(int index) {
        lilSongSelected = index;
    }

    /**
     * returns the currently selected lil song
     * @return
     * lil song index
     */
    public int getLilSongSelected() {
        return lilSongSelected;
    }

    /**
     * Adds the lil song with the corresponding index to the big song
     * @param index
     * lil song to add
     */
    public void addToLilBig(int index) {
        //get the notes to be added
        int[] frequencies = lilSongs[index].getFrequency();
        int[] durations = lilSongs[index].getDuration();
        int[] delays = lilSongs[index].getDelay();

        for(int i = 0; i < frequencies.length; i++) {
            this.frequencies.add(frequencies[i]);
            this.durations.add(durations[i]);
            this.delays.add(delays[i]);
        }

        bigSongOrder.add(index);
        bigSize++;
    }

    /**
     * Determines if the big song is full
     * @return
     * returns true if big song has max number of lil songs, false otherwise
     */
    public boolean isBigFull() {
        return bigSize == 10;
    }

    /**
     * Determines if the big song is empty
     * @return
     * true if big size is 0, false otherwise
     */
    public boolean isBigEmpty() {
        return bigSize == 0;
    }

    /**
     * Returns the number of lil songs in big song
     * @return
     * the number of lil songs currently in the big song
     */
    public int getBigSize() {
        return bigSize;
    }

    /**
     * Removes the last lil song from the big song
     */
    public void removeFromBigSong() {
        //get the last song
        int toRemove = bigSongOrder.get(bigSongOrder.size() - 1);

        int[] frequencies = lilSongs[toRemove].getFrequency();

        //remove the notes from the big song
        for(int i = 0; i < frequencies.length; i++) {
            this.frequencies.remove(this.frequencies.size()-1);
            this.durations.remove(this.durations.size()-1);
            this.delays.remove(this.delays.size()-1);
        }

        bigSongOrder.remove(bigSongOrder.size()-1);
        bigSize--;
    }

    /**
     * Creates a new song containing the big song and returns it
     * @return
     * The big song containing several lil songs
     */
    public SongAlgo makeBig() {
        SongAlgo bigSong = new SongAlgo();

        int[] frequencies = new int[this.frequencies.size()];
        int[] durations = new int[this.durations.size()];
        int[] delays = new int[this.delays.size()];

        //get all the frequencies, durations, and delays
        for(int i = 0; i < this.frequencies.size(); i++) {
            frequencies[i] = this.frequencies.get(i);
            durations[i] = this.durations.get(i);
            delays[i] = this.durations.get(i);
        }

        //set the notes for the big song
        bigSong.setFrequency(frequencies);
        bigSong.setDuration(durations);
        bigSong.setDelay(delays);

        return bigSong;
    }
}
