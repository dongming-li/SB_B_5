package coms309.songusoid;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rockm on 10/8/2017.
 */

public class Song implements Serializable {

    private String name;
    private String composer;
    private int upvotes;

    /**
     * Creates a new song with the given name, composer, location, and upvotes
     * @param name
     * name of the song
     * @param composer
     * composer who created the song
     * @param upvotes
     * number of upvotes for this song
     */
    public Song(String name, String composer,int upvotes){
        this.name = name;
        this.composer = composer;
        this.upvotes = upvotes;
    }

    /**
     * Returns the name of this song
     * @return
     * name of this song
     */
    public String getSongName(){
        return name;
    }

    /**
     * Returns the composer of this song
     * @return
     * composer of this song
     */
    public String getComposer(){
        return composer;
    }

    /**
     * Returns the upvotes for this song
     * @return
     * upvotes for this song
     */
    public int getUpvotes() {
        return upvotes;
    }
}
