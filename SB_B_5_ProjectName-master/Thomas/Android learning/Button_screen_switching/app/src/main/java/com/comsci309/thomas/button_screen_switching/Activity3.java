package com.comsci309.thomas.button_screen_switching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity3 extends AppCompatActivity {
    Button Act2Button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Act2Button1 = (Button)findViewById(R.id.but3);
        Act2Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(Activity3.this,HomeActivity. class);
                startActivity(i4);
            }
        });
    }
}
