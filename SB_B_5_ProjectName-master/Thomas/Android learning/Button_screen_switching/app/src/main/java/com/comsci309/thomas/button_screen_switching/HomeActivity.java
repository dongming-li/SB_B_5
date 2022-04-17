package com.comsci309.thomas.button_screen_switching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
Button homeButton1, homeButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeButton1 = (Button)findViewById(R.id.but1);
        homeButton2 = (Button)findViewById(R.id.but2);
        homeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeActivity.this,Activity2. class);
                startActivity(i1);
            }
        });
        homeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(HomeActivity.this,Activity2. class);
                startActivity(i2);
            }
        });
    }
}
