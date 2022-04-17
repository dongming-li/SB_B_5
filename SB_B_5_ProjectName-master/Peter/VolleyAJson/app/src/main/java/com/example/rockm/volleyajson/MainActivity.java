package com.example.rockm.volleyajson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView mTextView = (TextView) findViewById(R.id.textView);
        queue = Volley.newRequestQueue(this);
        url ="http://proj-309-sb-b-5.cs.iastate.edu:8088/login";
        JSONObject temp= new JSONObject();
        try {
            temp.put("username", "tfritzy");
            temp.put("password","password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTextView.setText(temp.toString());


        // Request a string response from the provided URL.
//

// Add the request to the
    }
    public void sendJson(View view){
        JSONObject temp= new JSONObject();
        try {
            temp.put("username", "tfritzy");
            temp.put("password","password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final TextView mTextView = (TextView) findViewById(R.id.textView);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, temp, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mTextView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("Error");
                    }
                });
        queue.add(jsObjRequest);

    }

}

