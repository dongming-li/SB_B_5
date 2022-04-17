package coms309.songusoid;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Garrett on 9/24/2017.
 * This method takes the frequency, duration, and delay array from the SongAlgo class
 * to create a song which can be played through an android device
 */
public class SongCreate extends AsyncTask<String, Void, String> {


    private int[] frequency;
    private int[] duration;
    private int[] delay;

    private Context context;
    private SoundPoolPlayer player;
    private Integer instrument;
    /**
     * Creates a new SongCreate object with the specified frequencies, durations, and delays
     * @param song
     * The SongAlgo object to create a listenable song for
     */
    public SongCreate(SongAlgo song, Context context) {
        this.frequency = song.getFrequency();
        this.duration = song.getDuration();
        this.delay = song.getDelay();
        this.context = context;
    }
    /**
     * This function creates a song from an array input of frequencies, durations, and delays.
     */



    public void playSong(Integer selectedInstrument){
        this.instrument = selectedInstrument;
        this.player = new SoundPoolPlayer(this.context,instrument);
        this.execute();
    }

    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; i < frequency.length; i++){

            player.play(context, frequency[i]/290.0f, instrument, duration[i]);

            try {
                Thread.sleep(delay[i]);

                // if this is the last note, don't cut the song short.
                if (i == duration.length-1){
                    Thread.sleep(duration[i]);
                }
            } catch (InterruptedException e) {
                player.release();
                Toast toast = Toast.makeText(context, "Playback interrupted", Toast.LENGTH_LONG);
                toast.show();
                Thread.interrupted();
            }

        }


        player.release();



        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {


        player.release();



        // might want to change "executed" for the returned string passed
        // into onPostExecute() but that is upto you
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}

}