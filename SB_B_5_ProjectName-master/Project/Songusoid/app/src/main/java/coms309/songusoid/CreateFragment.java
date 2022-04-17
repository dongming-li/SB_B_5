package coms309.songusoid;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {

    //keeps track of which algorithm has been selected to be used to create songs
    private int algorithmSelection;

    //Saves the most recently used song, so that it may be used in other functions
    private SongAlgo song;

    //stores profile information
    private Profile profile;

    //contains the currently active server communication for sending information to server
    private ServerCommunication serverComm;

    private LilBigSong lilBigSong;

    private Integer instrument = R.raw.piano;

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        //Retrieve the profile passed to this fragment
        this.profile = (Profile) getArguments().getSerializable("Profile");

        return view;
    }

    public static CreateFragment newInstance(Profile profile, ServerCommunication serverComm) {
        //Create a new fragment and bundle the profile to it
        CreateFragment fragment = new CreateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Profile", profile);
        fragment.setArguments(bundle);
        fragment.setServerComm(serverComm);

        //return a home fragment with the profile attached
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //Set the functions for the algorithm radio buttons
        for(int i = 0; i < 4; i++) {
            Button button = (Button) getActivity().findViewById(getResources().getIdentifier("radioButton" + i, "id",
                    getActivity().getPackageName()));

            button.setOnClickListener(songAlgoSelect());
        }
        //Set up spinner
        Spinner instrumentSpinner=intiateSpinner(R.array.InstrumentChoices,R.id.instrumentDisplayedSpinner);
        instrumentSpinner.setOnItemSelectedListener(instrumentSelect());
        //Set the functions for the lil big song radio buttons
        for(int i = 0; i < 6; i++) {
            Button button = (Button) getActivity().findViewById(getResources().getIdentifier("lilSong"+i, "id",
                    getActivity().getPackageName()));

            button.setOnClickListener(lilSongSelect());
        }

        //Set the functions for the rest of the buttons
        Button createClose1 = getActivity().findViewById(R.id.CreateClose);
        createClose1.setOnClickListener(createClose1());

        Button createClose2 = getActivity().findViewById(R.id.saveSongClose);
        createClose2.setOnClickListener(createClose2());

        Button saveSongConfirm = getActivity().findViewById(R.id.saveSong);
        saveSongConfirm.setOnClickListener(createSaveConfirm());

        Button saveSong = getActivity().findViewById(R.id.CreateSave);
        saveSong.setOnClickListener(createSave());

        Button createSong = getActivity().findViewById(R.id.CreateSong);
        createSong.setOnClickListener(createSong());

        Button createReplay = getActivity().findViewById(R.id.CreateReplay);
        createReplay.setOnClickListener(createReplay());

        Button keepLilSong = getActivity().findViewById(R.id.keepLilSong);
        keepLilSong.setOnClickListener(keepLilSong());

        Button discardLilSong = getActivity().findViewById(R.id.discardLilSong);
        discardLilSong.setOnClickListener(discardLilSong());

        Button addToLilBig = getActivity().findViewById(R.id.addToLilBig);
        addToLilBig.setOnClickListener(addToLilBig());

        Button removeFromLilBig = getActivity().findViewById(R.id.removeFromLilBig);
        removeFromLilBig.setOnClickListener(removeFromLilBig());

        Button playBigSong = getActivity().findViewById(R.id.playBigSong);
        playBigSong.setOnClickListener(playBigSong());

        Button finishBigSong = getActivity().findViewById(R.id.finishBigSong);
        finishBigSong.setOnClickListener(finishBigSong());
    }
    /**
     * Initiates The spinner used on the Home page
     */
    private Spinner intiateSpinner(int menuOptions, int spinnerChoice){
        Spinner spinner = (Spinner) getView().findViewById(spinnerChoice);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), menuOptions, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        return spinner;
    }

    /**
     * Sets up the home page spinner's listeners
     * @return listener
     */
    private  AdapterView.OnItemSelectedListener instrumentSelect(){
        return new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Integer selected=R.raw.piano;
                switch(pos){
                    case 0: selected=R.raw.piano;
                        break;
                    case 1: selected=R.raw.frenchhorn;
                        break;
                    case 2: selected=R.raw.flute;
                        break;
                    case 3: selected=R.raw.saxophone;
                        break;
                    case 4: selected=R.raw.triangle;
                        break;
                    case 5: selected=R.raw.trombone;
                        break;
                    case 6: selected=R.raw.tuba;
                        break;
                    case 7: selected=R.raw.fart;
                        break;

                }
                instrument=selected;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //Piano already set as normal.
            }
        };
    }

    /**
     * Closes the popup window on the home screen
     */
    private void closeCreatePopUp1() {
        View createPopUp = (View) getView().findViewById(R.id.CreatePopUp1);
        createPopUp.setVisibility(View.INVISIBLE);

        //make the create button work again.
        Button  create = (Button) getActivity().findViewById(R.id.CreateSong);
        create.setEnabled(true);
    }

    private void closeCreatePopUp2() {
        View createPopUp = (View) getView().findViewById(R.id.CreatePopUp2);
        createPopUp.setVisibility(View.INVISIBLE);
    }

    private void closeCreatePopUp4() {
        View createPopUp = (View) getView().findViewById(R.id.CreatePopUp4);
        createPopUp.setVisibility(View.INVISIBLE);
    }

    private void closeCreatePopUp3() {
        View createPopUp = (View) getView().findViewById(R.id.CreatePopUp3);
        createPopUp.setVisibility(View.INVISIBLE);
    }

    /**
     * Creates an on click listener for the close button of the popup window which
     * closes the popup window
     * @return
     * on click listener
     */
    private View.OnClickListener createClose1() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                closeCreatePopUp1();
            }
        };
    }

    /**
     * Closes the popup window which appears after trying to save a created song
     * @return
     */
    private View.OnClickListener createClose2() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                closeCreatePopUp2();
            }
        };
    }



    /**
     * Opens save pop up window when user clicks save after creating song
     * @return
     */
    private View.OnClickListener createSave() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                View savePopUp = (View) getView().findViewById(R.id.CreatePopUp2);
                savePopUp.setVisibility(View.VISIBLE);
            }
        };
    }

    private View.OnClickListener createSaveConfirm() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Get the song name the user selected
                EditText songName = (EditText) getView().findViewById(R.id.songName);
                String name = songName.getText().toString();
                if (name == null || name == ""){
                    Toast.makeText(getActivity(), "Song name connot be null", Toast.LENGTH_LONG);
                    return;
                }

                Song tempSong = new Song(name, profile.getUsername(),0);
                profile.addSong(tempSong);

                //Create the file to send to the server
                JSONObject toSend = jsonFile.saveSongEncode(name, song, profile);

                //Send the file to the server
                serverComm.saveSong(toSend);

                //close the popup window
                closeCreatePopUp2();
            }
        };
    }

    /**
     * Keeps track of what algorithm is selected to be used by the song create method.
     * Also updates the description text box which gives information about the currently
     * selected song
     * @return
     */
    private View.OnClickListener songAlgoSelect() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                // Is the button now checked?
                boolean checked = ((RadioButton) view).isChecked();

                //get the textview box to display information about selection
                TextView description = (TextView) getActivity().findViewById(R.id.textView3);

                if (checked) {
                    // Check which radio button was clicked
                    switch (view.getId()) {
                        case R.id.radioButton0:
                            //record which button was clicked and update the description
                            algorithmSelection = 0;
                            description.setText(getActivity().getApplicationContext().getResources().getString(R.string.Description1));
                            break;
                        case R.id.radioButton1:
                            algorithmSelection = 1;
                            description.setText("Creates a new song by sorting an array.  When an element is moved, that note is played.");
                            break;
                        case R.id.radioButton2:
                            algorithmSelection = 2;
                            description.setText("Use a bunch of little songs to make a big song");
                            break;
                        case R.id.radioButton3:
                            algorithmSelection = 3;
                            description.setText("Creates a random song by sweeping random frequencies.  Idea by Peter, scale by Tyler");
                            break;
                    }
                }
            }
        };
    }

    /**
     * creates a new song using the selected algorithm
     * @return
     */
    private View.OnClickListener createSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Create a new SongAlgo object, and define the song using the desired method
                song = new SongAlgo();
                song.setContext(getContext());
                //get the create! button so it can be disabled
                Button create = (Button) view;
                //Controls whether or not the first popup window displays
                boolean popUp = true;

                switch(algorithmSelection) {
                    case -1:
                        return;
                    case 0:
                        song.testAlgo(8);
                        create.setEnabled(false);
                        break;
                    case 1:
                        song.bubbleAlgo();
                        create.setEnabled(false);
                        break;
                    case 2:
                        View createPopUp4 = (View) getView().findViewById(R.id.CreatePopUp4);
                        createPopUp4.setVisibility(View.VISIBLE);
                        lilBigSong = new LilBigSong();
                        song.lilBigSong();
                        popUp = false;
                        create.setEnabled(false);
                        break;
                    case 3:
                        song.peterScale();
                        create.setEnabled(false);
                        break;
                }

                //Use the defined song to create a playable song
                SongCreate songToPlay = new SongCreate(song, getContext());

                //create a playable song and then play it
                //songToPlay.genSong();
                songToPlay.playSong(instrument);

                if(popUp) {
                    //Make the popup window visible so the user can interact with it
                    View songPopUp = (View) getActivity().findViewById(R.id.CreatePopUp1);

                    songPopUp.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    /**
     * Replays the song which was created with the create! button
     * @return
     */
    private View.OnClickListener createReplay() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                SongCreate songToPlay = new SongCreate(song, getContext());

                //songToPlay.genSong();
                songToPlay.playSong(instrument);
            }
        };
    }

    /**
     * Adds the current lil song to lilbigsong object
     * if reach max lil songs go to next popup
     * @return
     */
    private View.OnClickListener keepLilSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //add the song
                lilBigSong.addLilSong(SongAlgo.clone(song));

                //check if list is full
                //if so, go to next screen
                if(lilBigSong.isLilFull()) {
                    //Close current popup
                    closeCreatePopUp4();

                    //SET UP NEXT POPUP
                    View createPopUp3 = (View) getActivity().findViewById(R.id.CreatePopUp3);
                    createPopUp3.setVisibility(View.VISIBLE);
                }

                //make a new lilsong
                else {
                    song.lilBigSong();

                    //Use the defined song to create a playable song
                    SongCreate songToPlay = new SongCreate(song, getContext());

                    //create a playable song and then play it
                    //songToPlay.genSong();
                    songToPlay.playSong(instrument);
                }
            }
        };
    }

    /**
     * Creates a new lil song and plays it
     * @return
     */
    private View.OnClickListener discardLilSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Create a new lil song and play it
                song.lilBigSong();

                //Use the defined song to create a playable song
                SongCreate songToPlay = new SongCreate(song,getContext());

                //create a playable song and then play it
                //songToPlay.genSong();
                songToPlay.playSong(instrument);
            }
        };
    }

    /**
     * Onclicklistener for selecting a lil song radio button
     * plays the song and sets a variable for add method
     * @return
     */
    private View.OnClickListener lilSongSelect() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                // Is the button now checked?
                boolean checked = ((RadioButton) view).isChecked();

                if (checked) {
                    // Check which radio button was clicked
                    switch (view.getId()) {
                        case R.id.lilSong0:
                            lilBigSong.playLilSong(0, getContext(), instrument);
                            lilBigSong.setLilSongSelected(0);
                            break;
                        case R.id.lilSong1:
                            lilBigSong.playLilSong(1,getContext(), instrument);
                            lilBigSong.setLilSongSelected(1);
                            break;
                        case R.id.lilSong2:
                            lilBigSong.playLilSong(2,getContext(), instrument);
                            lilBigSong.setLilSongSelected(2);
                            break;
                        case R.id.lilSong3:
                            lilBigSong.playLilSong(3,getContext(), instrument);
                            lilBigSong.setLilSongSelected(3);
                            break;
                        case R.id.lilSong4:
                            lilBigSong.playLilSong(4,getContext(), instrument);
                            lilBigSong.setLilSongSelected(4);
                            break;
                        case R.id.lilSong5:
                            lilBigSong.playLilSong(5,getContext(), instrument);
                            lilBigSong.setLilSongSelected(5);
                            break;
                    }
                }
            }
        };
    }

    /**
     * Adds a new lil song to the big song
     * makes the lil song visible in the big song
     * @return
     */
    private View.OnClickListener addToLilBig() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Check to see if big song is full
                if(!lilBigSong.isBigFull()) {
                    //Add the currently selected little song to the big song
                    lilBigSong.addToLilBig(lilBigSong.getLilSongSelected());

                    //make the button corresponding to that little song visible
                    Button button = (Button) getActivity().findViewById(getResources().getIdentifier(
                            "bigSong"+lilBigSong.getBigSize(), "id", getActivity().getPackageName()));

                    button.setVisibility(View.VISIBLE);
                }

            }
        };
    }

    /**
     * Removes the most recently added lil song from the big song
     * @return
     */
    private View.OnClickListener removeFromLilBig() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Check to see if big song is empty
                if(!lilBigSong.isBigEmpty()) {
                    //remove the last song from big song
                    lilBigSong.removeFromBigSong();

                    //make the corresponding button invisible
                    Button button = (Button) getActivity().findViewById(getResources().getIdentifier(
                            "bigSong"+(lilBigSong.getBigSize()+1), "id", getActivity().getPackageName()));

                    button.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    /**
     * Plays the big song if it contains at least one little song
     * @return
     */
    private View.OnClickListener playBigSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Check to see if big song is empty
                if(!lilBigSong.isBigEmpty()) {
                    //play the Big Song
                    song = lilBigSong.makeBig();

                    //Use the defined song to create a playable song
                    SongCreate songToPlay = new SongCreate(song,getContext());

                    //create a playable song and then play it
                    //songToPlay.genSong();
                    songToPlay.playSong(instrument);
                }
            }
        };
    }

    /**
     * Completes the big song, creating a single song
     * @return
     */
    private View.OnClickListener finishBigSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Check to see if big song is empty
                if(!lilBigSong.isBigEmpty()) {
                    //play the Big Song
                    song = lilBigSong.makeBig();

                    //Make popup1 visible
                    View createPopUp1 = (View) getActivity().findViewById(R.id.CreatePopUp1);
                    createPopUp1.setVisibility(View.VISIBLE);

                }
                //close the popup window
                View createPopUp3 = (View) getActivity().findViewById(R.id.CreatePopUp3);
                createPopUp3.setVisibility(View.INVISIBLE);
            }
        };
    }

    /**
     * Sets the server communication object for the fragment
     * @param serverComm
     */
    public void setServerComm(ServerCommunication serverComm) {
        this.serverComm = serverComm;
    }

}
