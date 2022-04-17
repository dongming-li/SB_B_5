package com.comsci309.thomas.talktowebserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.*;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
/*
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
*/
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;



import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    Button button1, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.but1);
        button2 = (Button)findViewById(R.id.but2);

        String username = "user";
        String password = "pass";
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkInternetConnection();
                if(checkInternetConnection() ) {
                    // display "connected"
                    Toast.makeText(MainActivity.this, " Connected to network ", Toast.LENGTH_LONG).show();
                }
                else {
                    // display "not connected"
                    Toast.makeText(MainActivity.this, " Not connected to network ", Toast.LENGTH_LONG).show();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send the post request to the server.
                String username = "user";
                String password = "pass";
                String url = "http://proj-309-sb-b-5.cs.iastate.edu:8092/login";
                String serverResponse = null;
                JSONObject theJsonObject = makeJsonObject(username,password);
               /*
                serverResponse = post(url, theJsonObject);
                */
                post(url, theJsonObject);
                //serverResponse = "sent json";
                //Toast.makeText(MainActivity.this, serverResponse, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connect
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        //Toast message = new Toast(this);
        NetworkInfo activeNetwork = connect.getActiveNetworkInfo();
        boolean connected = ( activeNetwork != null && activeNetwork.isConnectedOrConnecting() );

        return connected;
    }

    private JSONObject makeJsonObject(String username, String password) {
        JSONObject jsonObject = null;
        try {
            /*JSONObject*/ jsonObject = new JSONObject();
            jsonObject.accumulate("username", username);
            jsonObject.accumulate("password", password);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    }

    // use HttpURLConnection to post a json object
    private static void post(final String url_string,  final JSONObject jsonObject)
    {
        String response;
        // Makes a new thread that will send a json object to the specified url.
        Thread postThread = new Thread( new Runnable()
        {
            String postResponse = "";
            @Override
            public void run()
            {
                InputStream inputStream = null;
                //String postResponse = "";
                String jsonString = "";
                HttpURLConnection urlConnection = null;
                //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    URL url = new URL( url_string);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setDoOutput(true);

                    // Set up the post request
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");

                    // change the json object to a string so it can be sent.
                    jsonString = jsonObject.toString();

                    // Send the json string by writing to the output stream.


                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    out.write(jsonString);
                    out.close();


                    // Read the response from the server using an InputStreamReader
                    //InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());

                    // get the response code from the server
                    int responseCode = urlConnection.getResponseCode();
                    // if response code is 200, the post request was successful
                    if(responseCode == 200)
                    {
                        // recieve the login information from the server


                        // make an input stream reader to read the input stream from the server.
                        InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
                        // use a buffered reader to read the input stream more efficintly
                        BufferedReader br = new BufferedReader(isr);
                        // read each line of the bufferedReader and store the entire input in the string buffer.
                        StringBuffer stringBuf = new StringBuffer();
                        String line;

                        while ((line = br.readLine()) != null) {
                            stringBuf.append(line);
                        }
                        br.close();
                        isr.close();
                        postResponse = stringBuf.toString();

                        //object =  new JSONObject(stringBuffer.toString());

                    }

                }
                catch(MalformedURLException e)
                {
                    System.err.println("MalformedURL Exception: " + e.getMessage());
                }
                catch(ProtocolException e)
                {
                    System.err.println("Protocol Exception: " + e.getMessage());
                }
                catch(IOException e)
                {
                    System.err.println("IO Exception: " + e.getMessage());
                }
                finally {

                    if(urlConnection != null)
                    {
                        urlConnection.disconnect();
                    }


                }
            }
            public String getPostResponse()
            {
                return this.postResponse;
            }

        } );
        postThread.start();

    }

}
