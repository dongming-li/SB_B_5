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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {

    //stores profile information to be displayed on home fragment
    private Profile profile;

    //contains the currently active server communication for sending information to server
    private ServerCommunication serverComm;

    //Currently selected song
    private String songSelected = new String();
    private String composerSelected = new String();
    private int upvotesSelected = 0;

    // Instrument
    private Integer instrument = R.raw.piano;

    //Keeps track of download page
    private int page = 0;

    //The Grid Displaying the songs
    GridView gridView;

    //Songs SelectedToDisplay
    private Song[] songsSelectedToDisplay;

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);

        //Retrieve the profile passed to this fragment
        this.profile = (Profile) getArguments().getSerializable("Profile");

        return view;
    }

    /**
     * Creates a new HomeFragment object which allows the passing of profile information
     * and server communication
     * @param profile
     * profile information passed from the navDraw activity
     * @param serverComm
     * server communication object created by navigation drawer
     * @return
     * returns a new HomeFragment
     */
    public static DownloadFragment newInstance(Profile profile, ServerCommunication serverComm) {
        //Create a new fragment and bundle the profile to it
        DownloadFragment fragment = new DownloadFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Profile", profile);
        fragment.setArguments(bundle);

        fragment.setServerComm(serverComm);

        //return a home fragment with the profile attached
        return fragment;
    }

    /**
     * Sets the server communication object for the fragment
     * @param serverComm
     */
    public void setServerComm(ServerCommunication serverComm) {
        this.serverComm = serverComm;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.scrollWindow2);
        Button[] buttons = new Button[15];
        LinearLayout.LayoutParams buttonParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //Set up spinner
        Spinner instrumentSpinner=intiateSpinner(R.array.InstrumentChoices,R.id.instrumentDisplayedSpinner);
        instrumentSpinner.setOnItemSelectedListener(instrumentSelect());

        //Set the functions for the rest of the buttons
        Button prevDownload = getActivity().findViewById(R.id.prevDownloads);
        prevDownload.setOnClickListener(getPrevDownloads());

        Button nextDownload = getActivity().findViewById(R.id.nextDownloads);
        nextDownload.setOnClickListener(getNextDownloads());

        Button closePopUp = getActivity().findViewById(R.id.DownloadCloseSong);
        closePopUp.setOnClickListener(downloadClose());

        Button downloadSong = getActivity().findViewById(R.id.DownloadSong);
        downloadSong.setOnClickListener(downloadSong());

        //Make a call to server to get first download page
        serverComm.setDownloadActivity(this);

        getDownloads(page);
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
     * Displays the songs that are wanted to display
     * @param context
     */
    private void displaySongs(Context context){
        gridView = (GridView) getView().findViewById(R.id.scrollWindow);
        SongAdapter songAdapter = new SongAdapter(context, songsSelectedToDisplay);
        gridView.setAdapter(songAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

//                songSelected = songsSelectedToDisplay[position].getSongName();
//                //Create popup to play or share song
//                View homePopUp = (View) getView().findViewById(R.id.HomePopUp1);
//                homePopUp.setVisibility(View.VISIBLE);
                songSelected = songsSelectedToDisplay[position].getSongName();
                composerSelected = songsSelectedToDisplay[position].getComposer();
                upvotesSelected = songsSelectedToDisplay[position].getUpvotes();

                JSONObject toSend;

                //Create the jsonFile to return to the server
                toSend = jsonFile.loadSongEncode(songSelected, profile);

                //send the load song request to the server
                serverComm.loadSong(toSend, getContext(),instrument);

                //Create popup to play or share song
                View homePopUp = (View) getView().findViewById(R.id.DownloadPopUp1);
                homePopUp.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Creates a popup option when a song is clicked
     * @param button
     * button corresponding to the song
     * @return
     * returns OnClickListener for dynamic buttons
     */
    private View.OnClickListener songOnClick(Button button) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                Button song = (Button) view;
                String songName = song.getText().toString();
                songSelected = songName;

                JSONObject toSend;

                //Create the jsonFile to return to the server
                toSend = jsonFile.loadSongEncode(songName, profile);

                //send the load song request to the server
                serverComm.loadSong(toSend, getContext(),instrument);

                //Create popup to play or share song
                View homePopUp = (View) getView().findViewById(R.id.DownloadPopUp1);
                homePopUp.setVisibility(View.VISIBLE);
            }
        };
    }

    /**
     * Sets the names of the songs in the fragment
     * @param songs
     * list of songs with names to be set
     */
    public void setSongs(ArrayList<Song> songs) {
        songsSelectedToDisplay= new Song[songs.size()];
        for(int i = 0; i < songs.size(); i++) {
            songsSelectedToDisplay[i]=songs.get(i);
        }
        displaySongs(this.getContext());
    }

    /**
     * Gets the previous page of downloadable songs
     * @return
     */
    private View.OnClickListener getPrevDownloads() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                if(page > 0) {
                    page--;

                    getDownloads(page);
                }
            }
        };
    }

    private View.OnClickListener getNextDownloads() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                if(songsSelectedToDisplay.length == 15) {
                    page++;
                    getDownloads(page);
                }
            }
        };
    }



    /**
     * Helper function to get downloads
     * @param page
     */
    private void getDownloads(int page) {
        JSONObject toSend;

        toSend = jsonFile.getDownloadsEncode(page, profile);

        serverComm.getDownloads(toSend);
    }

    /**
     * Creates an on click listener for the close button of the popup window which
     * closes the popup window
     * @return
     * on click listener
     */
    private View.OnClickListener downloadClose() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                closeDownloadPopUp();
            }
        };
    }

    /**
     * Closes the popup window on the download screen
     */
    private void closeDownloadPopUp() {
        View homePopUp = (View) getView().findViewById(R.id.DownloadPopUp1);
        homePopUp.setVisibility(View.INVISIBLE);
    }

    /**
     * Downloads the selected song
     * @return
     */
    private View.OnClickListener downloadSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                JSONObject toSend;
                String songName = songSelected;
                String composer = composerSelected;
                int upvotes = upvotesSelected;

                //edit upvotes
                Song temp = new Song(songName, composer, upvotes);
                profile.addSong(temp);

                toSend = jsonFile.shareSong(songName, profile.getUsername(), composer);

                serverComm.downloadSong(toSend);

                closeDownloadPopUp();
            }
        };
    }
}
