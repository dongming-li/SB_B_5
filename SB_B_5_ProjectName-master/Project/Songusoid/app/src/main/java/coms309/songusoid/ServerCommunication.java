package coms309.songusoid;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.Serializable;

/**
 * Created by Garrett on 10/5/2017.
 * ServerCommunication handles the Volley code necessary to communicate with the server
 * sending and receiving json files will be the primary method of communication
 */
public class ServerCommunication {

    //Request queue for server communication
    private RequestQueue queue;

    //Current activity necessary for changing activities
    private Context activity;

    //need download activity to update download song list
    private DownloadFragment downloadActivity;

    //Profile of the user
    private Profile profile;

    private String generalURL="http://proj-309-sb-b-5.cs.iastate.edu:8088/";
    public ServerCommunication(Context context, Profile profile) {
        queue = Volley.newRequestQueue(context);
        activity = context;
        this.profile = profile;
    }

    /**
     * Sends a LoginRequest to the server with details contained in a JSONObject
     * @param json
     * JSONObject containing the username and password
     */
    public void LoginRequest(JSONObject json) {

        String URL = generalURL+"login";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        //decode the response from the server, create a new profile
                        jsonFile.LoginDecode(response, profile);

                        if(profile.getSuccess()) {
                            //Change activity to Navigation Drawer, pass the created profile
                            Intent navDraw = new Intent(activity, NavigationDrawer.class);
                            navDraw.putExtra("Profile", profile);
                            activity.startActivity(navDraw);
                        }

                        else {
                            Toast.makeText(activity, "Unsuccessful Login", Toast.LENGTH_LONG).show();
                        }

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Connection error", Toast.LENGTH_LONG).show();

                    }
                });

        queue.add(jsObjRequest);
    }

    /**
     * Sends information to the server from the app, but expects no response
     * @param json
     * JSONObject which contains information to be sent to server
     * @param URL
     * URL for which the information is sent to and executed
     */
    public void noResponse(JSONObject json, String URL) {


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        queue.add(jsObjRequest);
    }

    /**
     * Sends details of a new account to be created to the server, and creates a Toast to
     * let the user know if successful or not
     * @param json
     * contains the username and password of the account to be created
     */
    public void createAcc(JSONObject json) {

        String URL = "http://proj-309-sb-b-5.cs.iastate.edu:8088/createProfile";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getBoolean("wasSuccess") == false){
                                Toast.makeText(activity, response.getString("errorMessage"), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(activity, "Account successfully created", Toast.LENGTH_LONG).show();

                            }
                        } catch(JSONException e){

                            Toast.makeText(activity, "There was a json error", Toast.LENGTH_LONG).show();
                        }


                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(activity, "Account Create Failed", Toast.LENGTH_LONG).show();

                    }
                });

        queue.add(jsObjRequest);
    }

    /**
     * Saves a song onto the server, server will respond with success or fail
     * On response, if successful, add the song to the profile.  If failed Toast the acitvity to
     * let the user know.
     * @param json
     * json containing the name of the song to save
     */
    public void saveSong(JSONObject json) {
        String URL = "http://proj-309-sb-b-5.cs.iastate.edu:8088/saveSong";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("wasSuccess") == false) {
                                Toast.makeText(activity, "Save Unsuccessful", Toast.LENGTH_LONG).show();
                                profile.removeSong();
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(activity, "Save Unsuccessful", Toast.LENGTH_LONG).show();
                            profile.removeSong();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(activity, "Save Unsuccessful", Toast.LENGTH_LONG).show();
                        profile.removeSong();
                    }
                });

        queue.add(jsObjRequest);
    }

    /**
     * Loads a song from the server.  Server will respond with success or fail
     * On response, if successful, create a SongAlgo object and set the frequency, duration,
     * and delay, to match the response.  Then use SongCreate to generate the song and play it.
     * If failed, Toast the activity to let the user know it failed.
     * @param json
     */
    public void loadSong(JSONObject json, Context context, int instrument) {
        String URL = "http://proj-309-sb-b-5.cs.iastate.edu:8088/getSong";
        final Context mContext = context;
        final int mInstrument = instrument;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        SongAlgo song = jsonFile.loadSongDecode(response, profile);
                        song.setContext(mContext);
                        song.play(mInstrument);

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(activity, "Add Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(jsObjRequest);
    }



    /**
     * Attemps to add a friend to the user
     * @param json
     */
    public void addFriendRequest(final JSONObject json) {

        String URL = "http://proj-309-sb-b-5.cs.iastate.edu:8088/addFriend";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("wasSuccess")) {
                                profile.removeFriend();
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(activity, "Add Unsuccessful", Toast.LENGTH_LONG).show();
                            profile.removeFriend();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(activity, "Add Unsuccessful", Toast.LENGTH_LONG).show();
                        profile.removeFriend();
                    }
                });

        queue.add(jsObjRequest);
    }


    /**
     * Takes in the users name and the new password and send it to the server to change it.
     * @param json
     */
    public void changePass(JSONObject json,String newPassword){
        String URL = generalURL+"changePassword";
        profile.setPassword(newPassword);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(activity, "Changed Password", Toast.LENGTH_LONG);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Password not changed", Toast.LENGTH_LONG);

                    }
                });

        queue.add(jsObjRequest);
    }

    /**
     * Uploads a song to the server for others to download/rate
     * @param json
     * file containing information to be uploaded
     */
    public void uploadSong(JSONObject json) {
        String URL = "http://proj-309-sb-b-5.cs.iastate.edu:8088/shareSong";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("wasSuccess") == false) {
                                Toast.makeText(activity, response.getString("errorMessage"), Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(activity, "song successfully added to global songs", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(activity, "Upload Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(activity, "Upload Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(jsObjRequest);
    }

    /**
     * Downloads a song from the server
     * @param json
     * file with information of song to be downloaded
     */
    public void downloadSong(JSONObject json) {
        String URL = "http://proj-309-sb-b-5.cs.iastate.edu:8088/shareSongWithUser";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("wasSuccess") == false) {
                                Toast.makeText(activity, response.getString("errorMessage"), Toast.LENGTH_LONG).show();
                                profile.removeSong();
                            }
                            else {
                                Toast.makeText(activity, response.getString("errorMessage"), Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(activity, "Download Unsuccessful", Toast.LENGTH_LONG).show();
                            profile.removeSong();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(activity, "Download Unsuccessful", Toast.LENGTH_LONG).show();
                        profile.removeSong();
                    }
                });

        queue.add(jsObjRequest);
    }

    /**
     * Removes a song from the user
     * @param json
     * information of song to remove
     */
    public void removeSong(JSONObject json){
        String URL = generalURL+"removeSong";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(activity, "Removed Song", Toast.LENGTH_LONG).show();
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Remove Song Failed", Toast.LENGTH_LONG).show();

                    }
                });

        queue.add(jsObjRequest);
    }

    /**
     * Upvotes a song
     * @param json
     * information of song to upvote
     */
    public void upvoteSong(JSONObject json){
        String URL = generalURL+"vote";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Toast.makeText(activity, response.getString("errorMessage"), Toast.LENGTH_LONG);

                        } catch (JSONException e){

                            Toast.makeText(activity, "Upvote failed", Toast.LENGTH_LONG);
                        }

                        Toast.makeText(activity, "Upvoted Song", Toast.LENGTH_LONG);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Upvote Failed", Toast.LENGTH_LONG);

                    }


                });

        queue.add(jsObjRequest);
    }

    /**
     * Gets 15 songs that are available for download from the server based off the JSON
     * @param json
     */
    public void getDownloads(JSONObject json){

        String URL = generalURL+"getGlobalSongs";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        downloadActivity.setSongs(jsonFile.getDownloadsDecode(response));
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Get Downloads Failed", Toast.LENGTH_LONG);

                    }
                });

        queue.add(jsObjRequest);
    }

    public void setDownloadActivity(DownloadFragment frag) {
        downloadActivity = frag;
    }
}
