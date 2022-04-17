package coms309.songusoid;

import android.support.v4.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.lang.Integer;

import java.util.ArrayList;

/**
 * Created by Garrett on 10/5/2017.
 * jasonFile stores useful static functions for encoding and decoding json files
 * json files are sent to the server, and sent by the server for communication
 */
public class jsonFile {

    /**
     * Creates a Json file which contains the given username and password
     * @param profile
     * Profile for this user
     * password given at login
     * @return
     * returns a string corresponding to the json file
     */
    public static JSONObject LoginEncode(Profile profile) {
        JSONObject profileInfo =new JSONObject();
        try {
            //Puts the username and password into the JSONObject
            profileInfo.put("username",profile.getUsername());
            profileInfo.put("password",profile.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returns the object that Object
        return profileInfo;
//
    }

    /**
     * Converts the json file returned after logging in, to an array of Strings
     * which contains profile information such as: Songs by the user
     * At some point I hope to return a list of the objects called song instead.
     * That way, we can access information about each song in the main
     * without having to communicate to the server every time.
     * @param obj
     * json file which is returned by the server after login
     * @return result
     * String array containing profile information
     */
    public static void LoginDecode(JSONObject obj, Profile profile) {

        //The array of songs from the JSONObject given
        JSONArray returnedList;

        //The array of friends from the JSONObject given
        JSONArray returnedList2;

        //Temp JSONObject that is a song from the list
        JSONObject song;
        //Temp JSONObject that is a friend from the list
        String friend;

        //temp Song object to be given to profile
        Song tempSong;
        //temp Friend object ot be given to profile
        Friend tempFriend;
        try {
            //Gets the list of songs from the user

            returnedList = obj.getJSONArray("songs");
            returnedList2 = obj.getJSONArray("friends");
            //Traverse the song list
            for(int i = 0; i<returnedList.length();i++){
                //Get the song from the array
                song=returnedList.getJSONObject(i);
                //Create the song object add to the song ArrayList
                tempSong = new Song(
                        song.getString("songName"),
                        song.getString("composer"),
                        song.getInt("upvotes"));
                //add the song to the profile
                profile.addSong(tempSong);
            }

            //add friends to profile
            for(int i = 0; i<returnedList2.length();i++) {
                //get friend
                friend=returnedList2.getString(i);
                //create friend object
                tempFriend = new Friend(friend);
                profile.addFriend(tempFriend);
            }
            //set success on profile
            profile.setSuccess(true);

        } catch (JSONException e) {
            profile.setSuccess(false);
        }

    }
    /**
     * Creates a JSON Object that holds the song's name and returns it
     * @param songName
     * Name of the song to be shared
     */
    public static JSONObject shareSong(String songName, String friend, String username){
        JSONObject song =new JSONObject();
        try {
            song.put("songName",songName);
            song.put("sharedWith",friend);
            song.put("sharedBy",username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return song;

    }

    /**
     * Creates a JSONObject for the given song.  Stores the length of the song in
     * "length" as the number of notes.  Stores the frequencies, durations, and delays, in a single
     * string "song" as "frequency[1] duration[1] delay[1] frequency[2] duration[2] delay[2] ..."
     * Stores the profile's username and password in "username" and "password", Stores the song name
     * into "name"
     * @param song
     * song to be save on the server
     * @return
     */
    public static JSONObject saveSongEncode(String songName, SongAlgo song, Profile profile) {
        //TODO
        JSONObject songToBeSaved = new JSONObject();
        String songString = "";

        // Obtain information to send using the json object
        songString = combineSongComponents(song);
        // Put data in the json object
        try
        {
            songToBeSaved.put("songName", songName);
            songToBeSaved.put("composer",profile.getUsername());
            songToBeSaved.put("songBody", songString);

        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        return songToBeSaved;
    }

    /**
     * Decodes the response from saving a song.  Gets the details about the new song and adds
     * it to the profile
     * @param file
     * file containing song details
     * @param profile
     * profile of the user
     */
    public static void saveSongDecode(JSONObject file, Profile profile) {
        //temp Song object to be given to profile
        Song tempSong;

        try {
            tempSong = new Song(
                    file.getString("name"),
                    file.getString("composer"),
                    file.getInt("upvotes"));
            //add the song to the profile
            profile.addSong(tempSong);
            profile.setSuccess(true);

        }
        catch (JSONException e) {
            profile.setSuccess(false);
        }
    }

    /**
     * Creates a JSONObject for the given song.  Stores the song name in "name".  Stores the
     * username in "username".  Stores the password in "password"
     * @param songName
     * @param profile
     * @return
     */
    public static JSONObject loadSongEncode(String songName, Profile profile) {
        JSONObject toLoad = new JSONObject();
        try {
            toLoad.put("songName",songName);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return toLoad;
    }

    /**
     * Decodes the response from loading a song from the server.  Server should responsd with arrays
     * of frequencies, durations, and delays.  Create a new SongAlgo and set the frequencies, durations,
     * and delays, with these values.  Return the SongAlgo
     * @param response
     * JSONObject containing details of the song
     * @param profile
     * profile of the user
     * @return
     */
    public static SongAlgo loadSongDecode(JSONObject response, Profile profile) {
        //TODO
        String songBody = "";
        int numbersInString = 0;
        int[][] songComponents;
        SongAlgo loadedSong = new SongAlgo();

        try
        {

            songBody = response.getString("songBody");

            songComponents = seperateSongComponents(songBody);

            // set the frequency, duration, and delay arrays of the song algo
            loadedSong.setFrequency(songComponents[0]);
            loadedSong.setDuration(songComponents[1]);
            loadedSong.setDelay(songComponents[2]);
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return loadedSong;
    }


    /**
     * Creates a JSONFILE to be sent to the server containing the user of the profile and
     * a friend to be added
     * @param friend
     * Friend to add
     * @param profile
     * Profile of user
     * @return
     * JSONFILE to be sent to server
     */
    public static JSONObject addFriendEncode(String friend, Profile profile) {
        JSONObject addFriend =new JSONObject();
        try {
            addFriend.put("addFriend",friend);
            addFriend.put("username",profile.getUsername());
            addFriend.put("password",profile.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return addFriend;
    }

    /**
     * Decodes the response sent from the server after adding a friend
     * @param file
     * contains response from server
     * @param profile
     * profile of user
     */
    public static void addFriendDecode(JSONObject file, Profile profile) {
        //temp friend to add to profile
        Friend tempFriend;

        try {
            tempFriend = new Friend(file.getString("addFriend"));
            //add the song to the profile
            profile.addFriend(tempFriend);
            profile.setSuccess(true);

        }
        catch (JSONException e) {
            profile.setSuccess(false);
        }

    }

    /**
     * Encodes a JSONObject to be sent to the server for uploading a song
     * @param song
     * song to be uploaded
     * @param profile
     * profile of user
     * @return
     * returns file to be sent to server
     */
    public static JSONObject uploadSongEncode(String song, Profile profile) {
        //need composer
        JSONObject upload =new JSONObject();
        try {
            upload.put("songName",song);
            upload.put("composer",profile.getUsername());
            upload.put("password",profile.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return upload;
    }

    /**
     * Creates a JSONObject to be sent to the server for downloading a song
     * @param song
     * song to be downloaded
     * @param profile
     * profile of user
     * @return
     * returns file to be sent to server
     */
    public static JSONObject downloadSongEncode(String song, Profile profile) {
        JSONObject download =new JSONObject();
        try {
            download.put("song",song);
            download.put("username",profile.getUsername());
            download.put("password",profile.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return download;
    }

    public static JSONObject getDownloadsEncode(int page, Profile profile) {
        JSONObject download =new JSONObject();
        try {
            download.put("page",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return download;
    }

    /**
     * Decodes response of downloaded songs
     * @param obj
     * Response from server
     */
    public static ArrayList<Song> getDownloadsDecode(JSONObject obj) {

        //The array of songs from the JSONObject given
        JSONArray returnedList;

        //Temp JSONObject that is a song from the list
        JSONObject song;

        //Temp song to be put in list
        Song tempSong;

        ArrayList<Song> downloadSongs = new ArrayList<Song>();

        try {
            //Gets the list of songs from the user
            returnedList = obj.getJSONArray("songs");

            //Traverse the song list
            for(int i = 0; i<returnedList.length();i++){
                //Get the song from the array
                song=returnedList.getJSONObject(i);
                //Create the song object add to the song ArrayList
                tempSong = new Song(
                        song.getString("songName"),
                        song.getString("composer"),
                        song.getInt("upvotes"));
                //add the song to the profile
                downloadSongs.add(tempSong);
            }

            return downloadSongs;

        } catch (JSONException e) {
            return null;
        }

    }

    /**
     * Sets up the JSONObject to be sent to the server in order to remove a song
     * @param song
     * song to be removed
     * @param profile
     * profile of user
     * @return
     * returns file to be sent to server
     */
    public static JSONObject removeSongEncode(String song, Profile profile) {
        JSONObject remove =new JSONObject();
        try {
            remove.put("songName",song);
            remove.put("username",profile.getUsername());
            remove.put("password",profile.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return remove;
    }

    /**
     * Sets up the JSONObject to be sent to the server in order to upvote a song
     * @param song
     * song to be removed
     * @param profile
     * profile of user
     * @return
     * returns file to be sent to server
     */
    public static JSONObject upvoteSongEncode(String song, Profile profile) {
        JSONObject upvote =new JSONObject();
        try {
            upvote.put("songName",song);
            upvote.put("username",profile.getUsername());
            upvote.put("password",profile.getPassword());
            upvote.put("upOrDown",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return upvote;
    }

    /**
     * Creates a json to be used to change the password.
     * @param newPassword
     * @param profile
     * @return
     */
    public static JSONObject changePassword(String newPassword, Profile profile) {
        JSONObject obj = new JSONObject();
        try {
            //Puts the username and password into the JSONObject
            obj.put("username", profile.getUsername());
            obj.put("oldPassword", profile.getPassword());
            obj.put("newPassword", newPassword);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * This helper method takes a song algo and combines the frequency, duration,
     * and delay arrays into a single string that looks like this:
     * "Frequency[0] Duration[0] Delay[0] Frequency[1] Duration[1] Delay[1] ... "
     * This string is returned.
     * @param algo
     * @return
     */
    private static String combineSongComponents(SongAlgo algo)
    {
        String song = "";

        int[] frequencies = algo.getFrequency();
        int[] durations = algo.getDuration();
        int[] delays = algo.getDelay();

        // for each note, add the frequency, duration, and delay to the string
        for(int i = 0; i < frequencies.length; i++)
        {
            song += Integer.toString(frequencies[i]) + " ";
            song += Integer.toString(durations[i]) + " ";
            song += Integer.toString(delays[i]) + " ";
        }


        return song;
    }

    /**
     * This helper method takes in a song body and returns the song's frequencies,
     * durations, and delays in a 2d array of integers. The first set of integers
     * is the frequencies, the second set is the durations, and the third set is
     * the delays
     * @param songBody
     * @return
     */
    private static int[][] seperateSongComponents(String songBody) {
        int numbersInString = 0;
        int[][] components;

        int i = 0;
        int frequenciesAdded = 0;
        int durationsAdded = 0;
        int delaysAdded = 0;

        Scanner bodyScanner = new Scanner(songBody);

        // get the number of tokens in the string
        numbersInString = (new StringTokenizer(songBody, " ")).countTokens();

        int componentLength = numbersInString / 3;

        // Create the frequency durations, and delay arrays.
        int[] frequencies = new int[componentLength];
        int[] durations = new int[componentLength];
        int[] delays = new int[componentLength];

        // Initialize the components array.
        components = new int[3][componentLength];

        while (bodyScanner.hasNext()) {
            if (i % 3 == 0) {
                frequencies[frequenciesAdded] = new Integer(bodyScanner.next());
                frequenciesAdded++;
            }
            if (i % 3 == 1) {
                durations[durationsAdded] = new Integer(bodyScanner.next());
                durationsAdded++;
            }
            if (i % 3 == 2) {
                delays[delaysAdded] = new Integer(bodyScanner.next());
                delaysAdded++;
            }
            i++;
        }
        bodyScanner.close();

        components[0] = frequencies;
        components[1] = durations;
        components[2] = delays;

        return components;
    }

}
