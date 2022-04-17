package coms309.songusoid;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Garrett on 10/5/2017.
 * Profile stores useful information about the logged in user
 */
public class Profile implements Serializable {

    //String array which holds the profile information
    private ArrayList<Song> songList = new ArrayList<Song>();

    //list of friends for this user
    private ArrayList<Friend> friends = new ArrayList<Friend>();

    //Determines whether or not the profile was created successfully
    private boolean success;

    String username;
    String password;
    int numSongs=0;

    /**
     * Creates a new Profile object with the given profile information
     */
    public Profile() {

    }

    /**
     * Returns this profiles songList
     * @return
     * ArrayList of songs owned by the user
     */
    public ArrayList<Song> getSongList() {
        return songList;
    }
    /**
     * Returns this profile's list of songs as their corresponding names
     * @return
     * String array containing all song names
     */
    public Song[] getSongListArray() {
        Song[] songsArray=new Song[songList.size()];
        for(int x=0;x<songList.size();x++){
            songsArray[x]= songList.get(x);
        }
        return songsArray;
    }

    public Song[] getYourSongs(){
        ArrayList<Song> tempList= new ArrayList<Song>();
        for(Song s: songList){
            if(s.getComposer().equals(username))
                tempList.add(s);
        }
        Song[] songsArray=new Song[tempList.size()];
        for(int x=0;x<tempList.size();x++){
            songsArray[x]= tempList.get(x);
        }
        return songsArray;
    }
    /**
     * Returns this profile's list of songs as their corresponding names
     * @return
     * String ArrayList containing all song names
     */
    public String[] getSongNames() {
        ArrayList<String> songNames = new ArrayList<String>();
        for(int i = 0; i < songList.size(); i++) {
            songNames.add(songList.get(i).getSongName());
        }
        return songNames.toArray(new String[songNames.size()]);
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    /**
     * Adds a new song to the profile
     * @param song
     * song to add
     */
    public void addSong(Song song) {
        numSongs++;
        songList.add(song);
    }

    /**
     * returns whether or not the profile creation was successful
     * @return
     * true if profile was created successfully, false otherwise
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * Sets the success variable for this profile
     * @param success
     * whether or not the profile was set successfully.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Sets the username of this profile
     * @param username
     * username for this profile
     */
    public void setUsername (String username) {
        this.username = username;
    }

    /**
     * Returns the username of this profile
     * @return
     * username
     */
    public String getUsername () {
        return username;
    }

    /**
     * Sets the password for this profile
     * @param password
     * password for this profile
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the password for this profile
     * @return
     * password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the number of songs
     * @return numSongs
     */
    public int getNumSongs(){return numSongs;}


    public int getUpvotes() {
        int result = 0;
        Song[] yourSongs=this.getYourSongs();
        for (Song song : yourSongs) {
            result += song.getUpvotes();
        }
        return result;
    }
    /**
     * Adds a friend to this user's friend list
     * @param friend
     * friend to add
     */
    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    /**
     * Removes a friend from this user's friend list #Savage
     * @param friend
     * friend to remove
     */
    public void removeFriend(Friend friend) {
        friends.remove(friend);
    }

    /**
     * Removes last friend added.
     */
    public void removeFriend() {
        friends.remove(friends.size()-1);
    }

    /**
     * Returns the list of friends for this user
     * @return
     * friends
     */
    public ArrayList<Friend> getFriends() {
        return friends;
    }

    /**
     * Removes the last song added
     */
    public void removeSong() {
        songList.remove(songList.size()-1);
    }

    /**
     * Returns the list of songs owned by this profile
     * @return
     * songs in profile
     */
    public ArrayList<Song> getSongs() {
        return songList;
    }

    /**
     * removes a specific song from this songList
     * @param song
     * song to remove
     */
    public void removeSong(Song song) {
        songList.remove(song);
    }
}
