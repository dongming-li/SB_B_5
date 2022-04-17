package coms309.songusoid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    //holds the server communication for this activity
    private ServerCommunication serverComm;

    //holds the profile for the user which is logging in
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        profile = new Profile();
        serverComm = new ServerCommunication(this, profile);
    }

    /**
     * Sends username and password to server to verify login
     * If the username and password is correct, stores profile information,
     * and switches to the navigation drawer activity
     * @param view
     */
    public void Login(View view) {
        //retrieve the username and password given
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        //Set username and password
        profile.setUsername(username.getText().toString());
        profile.setPassword(password.getText().toString());

        //Send userInfo and get songList
        JSONObject toSend;

        //Send login details to server, and receive a response
        toSend = jsonFile.LoginEncode(profile);
        serverComm.LoginRequest(toSend);
    }

    /**
     * Makes the create account pop up visible
     * allowing user to create a new account
     * @param view
     */
    public void createAcc(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        TextView accToCreate = (TextView) findViewById(R.id.createAcc);

        accToCreate.setText("username: " + username.getText().toString());

        //Make the popup visible
        View layout = (View) findViewById(R.id.LoginPopUp1);
        layout.setVisibility(View.VISIBLE);
    }

    public void createAccYes(View view) {
        //retrieve the username and password given
        EditText usernameInBox = (EditText) findViewById(R.id.username);
        EditText passwordInBox = (EditText) findViewById(R.id.password);

        //Send userInfo and get songList
        JSONObject toSend;

        profile.username = usernameInBox.getText().toString();
        profile.password = passwordInBox.getText().toString();

        //Send login details to server, and receive a response
        toSend = jsonFile.LoginEncode(profile);
        serverComm.createAcc(toSend);

        //Make the popup invisible
        View layout = (View) findViewById(R.id.LoginPopUp1);
        layout.setVisibility(View.INVISIBLE);
    }

    public void createAccNo(View view) {
        //Make the popup invisible
        View layout = (View) findViewById(R.id.LoginPopUp1);
        layout.setVisibility(View.INVISIBLE);
    }

    /**
     * Fake login for testing purposes
     * @param view
     */
    public void fakeLogin(View view) {
        profile.setUsername("temp");
        profile.setPassword("temp");


        for(int i = 0; i < 15; i++) {
            Song temp = new Song("song" + i, "temp", 0);
            profile.addSong(temp);
            Friend tempFriend = new Friend("friend" + i);
            profile.addFriend(tempFriend);
        }

        //Change activity to Navigation Drawer, pass the created profile
        Intent navDraw = new Intent(this, NavigationDrawer.class);
        navDraw.putExtra("Profile", profile);
        this.startActivity(navDraw);
    }
}
