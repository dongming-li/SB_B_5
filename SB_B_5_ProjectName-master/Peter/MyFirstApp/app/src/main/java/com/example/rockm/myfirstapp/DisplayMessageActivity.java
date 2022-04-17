package com.example.rockm.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent= getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("Are you sure you want to send that to your Mother?");

    }
    public void sendTheMessage(View view){
        startActivity(new Intent(this, SendMessageActivity.class));
    }
}

