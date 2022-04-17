package com.example.rockm.sqlstuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayCont extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cont);
        Intent intent= getIntent();
        String message = intent.getStringExtra(SendActivity.EXTRA_DBASE);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }
}
