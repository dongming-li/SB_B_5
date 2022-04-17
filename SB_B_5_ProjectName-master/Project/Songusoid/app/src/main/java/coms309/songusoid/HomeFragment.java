package coms309.songusoid;


import android.app.ActionBar;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import static coms309.songusoid.R.id.parent;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //stores profile information to be displayed on home fragment
    private Profile profile;

    //contains the currently active server communication for sending information to server
    private ServerCommunication serverComm;

    //Currently selected song
    private String songSelected = new String();

    private Integer instrument = R.raw.piano;


    //The Grid Displaying the songs
    GridView gridView;

    //Songs SelectedToDisplay
    private Song[] songsSelectedToDisplay;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
    public static HomeFragment newInstance(Profile profile, ServerCommunication serverComm) {
        //Create a new fragment and bundle the profile to it
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Profile", profile);
        fragment.setArguments(bundle);

        fragment.setServerComm(serverComm);

        //return a home fragment with the profile attached
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //Set up Spinners
        Spinner homePageSpinner=intiateSpinner(R.array.HomePageChoices,R.id.songDisplayedSpinner);
        homePageSpinner.setOnItemSelectedListener(homeSelect());
        Spinner instrumentSpinner=intiateSpinner(R.array.InstrumentChoices,R.id.instrumentDisplayedSpinner);
        instrumentSpinner.setOnItemSelectedListener(instrumentSelect());
        //Set the functions for the rest of the buttons
        Button playSong = getActivity().findViewById(R.id.HomePlaySong);
        playSong.setOnClickListener(playSong());

        Button shareSong = getActivity().findViewById(R.id.HomeShareSong);
        shareSong.setOnClickListener(shareSong());

        Button homeClose = getActivity().findViewById(R.id.HomeClose);
        homeClose.setOnClickListener(homeClose());

        Button homeClose2 = getActivity().findViewById(R.id.closeFriend);
        homeClose2.setOnClickListener(homeClose2());

        Button addFriend = getActivity().findViewById(R.id.addFriend);
        addFriend.setOnClickListener(addFriend());

        Button addFriendFinal = getActivity().findViewById(R.id.addFriendFinal);
        addFriendFinal.setOnClickListener(addFriendFinal());

        Button viewProfile = getActivity().findViewById(R.id.viewProfile);
        viewProfile.setOnClickListener(viewProfile());

        Button uploadSong = getActivity().findViewById(R.id.HomeUploadSong);
        uploadSong.setOnClickListener(uploadSong());

        Button upvoteSong = getActivity().findViewById(R.id.HomeUpvoteSong);
        upvoteSong.setOnClickListener(upvoteSong());

        Button removeSong = getActivity().findViewById(R.id.HomeRemoveSong);
        removeSong.setOnClickListener(removeSong());
    }

    /**
     * Initiates The spinner used on the Home page
     */
    private Spinner intiateSpinner(int menuOptions,int spinnerChoice){
        Spinner spinner = (Spinner) getView().findViewById(spinnerChoice);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), menuOptions, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        return spinner;
    }
    /**
     * Displays the song to the Screen in a grid format
     * @param context
     * @param choice
     * 0 means all, 1 means your songs only
     */
    private void displaySongs(Context context,int choice){
        gridView = (GridView) getView().findViewById(R.id.scrollWindow);
        if(choice==0)
            songsSelectedToDisplay = profile.getSongListArray();
        else
            songsSelectedToDisplay = profile.getYourSongs();
        SongAdapter songAdapter = new SongAdapter(context, songsSelectedToDisplay);
        gridView.setAdapter(songAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                songSelected = songsSelectedToDisplay[position].getSongName();
                //Create popup to play or share song
                View homePopUp = (View) getView().findViewById(R.id.HomePopUp1);
                homePopUp.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Sets up the home page spinner's listeners
     * @return listener
     */
    private  AdapterView.OnItemSelectedListener homeSelect(){
        return new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                displaySongs(parent.getContext(),pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                displaySongs(parent.getContext(),0);
            }
        };
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
     * Creates the onclick listener for sharing songs.  Loads in the share fragment
     * @return
     * Returns on click listener
     */
    private View.OnClickListener shareSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                String songName = songSelected;
                ShareFragment shareFragment = ShareFragment.newInstance(profile, serverComm, songName);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relativeLayout_for_fragment, shareFragment, shareFragment.getTag()).commit();
            }
        };
    }

    private View.OnClickListener playSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                String songName = songSelected;
                JSONObject toSend;

                //Create the jsonFile to return to the server
                toSend = jsonFile.loadSongEncode(songName, profile);

                //send the load song request to the server
                serverComm.loadSong(toSend, getContext(),instrument);
            }
        };
    }

    /**
     * Creates an on click listener for the close button of the popup window which
     * closes the popup window
     * @return
     * on click listener
     */
    private View.OnClickListener homeClose() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                closeHomePopUp();
            }
        };
    }

    /**
     * Closes the popup window on the home screen
     */
    private void closeHomePopUp() {
        View homePopUp = (View) getView().findViewById(R.id.HomePopUp1);
        homePopUp.setVisibility(View.INVISIBLE);
    }

    /**
     * Closes second popup window
     * @return
     */
    private View.OnClickListener homeClose2() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                closeHomePopUp2();
            }
        };
    }

    /**
     * closes second popup window
     */
    private void closeHomePopUp2() {
        View homePopUp = (View) getView().findViewById(R.id.HomePopUp2);
        homePopUp.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the server communication object for the fragment
     * @param serverComm
     */
    public void setServerComm(ServerCommunication serverComm) {
        this.serverComm = serverComm;
    }

    /**
     * Opens second popup window
     * @return
     */
    private View.OnClickListener addFriend() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Create popup to play or share song
                View homePopUp = (View) getView().findViewById(R.id.HomePopUp2);
                homePopUp.setVisibility(View.VISIBLE);
            }
        };
    }

    /**
     * Adds the friend with the name to this account
     * @return
     */
    private View.OnClickListener addFriendFinal() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                EditText friendName = (EditText) getView().findViewById(R.id.friendName);
                String name = friendName.getText().toString();

                Friend friend = new Friend(name);
                profile.addFriend(friend);

                JSONObject toSend = jsonFile.addFriendEncode(name, profile);

                serverComm.addFriendRequest(toSend);

                closeHomePopUp2();
            }
        };
    }

    /**
     * Changes fragment to profile fragment
     * @return
     */
    private View.OnClickListener viewProfile() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                ProfileFragment profileFragment =ProfileFragment.newInstance(profile, serverComm);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relativeLayout_for_fragment, profileFragment, profileFragment.getTag()).commit();
            }
        };
    }

    /**
     * Uploads a song to the server
     * @return
     */
    private View.OnClickListener uploadSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                String songName = songSelected;
                JSONObject toSend;

                //Create the jsonFile to return to the server
                toSend = jsonFile.uploadSongEncode(songName, profile);

                //send the load song request to the server
                serverComm.uploadSong(toSend);
            }
        };
    }

    /**
     * Upvotes a song
     * @return
     */
    private View.OnClickListener upvoteSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                String songName = songSelected;
                JSONObject toSend;

                //Create the jsonFile to return to the server
                toSend = jsonFile.upvoteSongEncode(songName, profile);

                //send the load song request to the server
                serverComm.upvoteSong(toSend);
            }
        };
    }

    /**
     * Removes the selected song
     * @return
     */
    private View.OnClickListener removeSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                String songName = songSelected;
                JSONObject toSend;

                //Create the jsonFile to return to the server
                toSend = jsonFile.removeSongEncode(songName, profile);

                //send the load song request to the server
                serverComm.removeSong(toSend);

                //remove the song from profile
                ArrayList<Song> songs = profile.getSongs();
                for(int i = 0; i < songs.size(); i++) {
                    if(songs.get(i).getSongName() == songSelected) {
                        profile.removeSong(songs.get(i));
                        return;
                    }
                }
            }
        };
    }

}
