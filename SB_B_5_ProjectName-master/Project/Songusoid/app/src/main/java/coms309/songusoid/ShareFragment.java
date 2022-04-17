package coms309.songusoid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {

    //stores profile information to be displayed on home fragment
    private Profile profile;

    //contains the currently active server communication for sending information to server
    private ServerCommunication serverComm;

    //Currently selected friends
    private ArrayList<String> friendsSelected = new ArrayList<String>();

    private String songSelected;

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        //Retrieve the profile passed to this fragment
        this.profile = (Profile) getArguments().getSerializable("Profile");

        //Retrieve the song passed to this fragment
        this.songSelected = (String) getArguments().getSerializable("Song");

        return view;
    }

    public static ShareFragment newInstance(Profile profile, ServerCommunication serverComm, String song) {
        //Create a new fragment and bundle the profile to it
        ShareFragment fragment = new ShareFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Profile", profile);
        bundle.putSerializable("Song",song);
        fragment.setArguments(bundle);

        fragment.setServerComm(serverComm);

        //return a home fragment with the profile attached
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.scrollWindow2);
        ArrayList<Friend> friends = profile.getFriends();
        CheckBox[] buttons = new CheckBox[friends.size()];

        LinearLayout.LayoutParams buttonParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < friends.size(); i++) {
            buttons[i] = new CheckBox(getActivity());
            buttons[i].setText(friends.get(i).getName());
            buttons[i].setLayoutParams(buttonParam);
            buttons[i].setOnClickListener(friendOnClick(buttons[i]));
            buttons[i].setId(3000+i);
            layout.addView(buttons[i]);
        }

        //Set the functions for the rest of the buttons
        Button shareSong = getActivity().findViewById(R.id.shareToFriends);
        shareSong.setOnClickListener(shareSong());

        Button addAll = getActivity().findViewById(R.id.addAll);
        addAll.setOnClickListener(addAll());

        Button removeAll = getActivity().findViewById(R.id.removeAll);
        removeAll.setOnClickListener(removeAll());
    }

    /**
     * Sets the server communication object for the fragment
     * @param serverComm
     */
    public void setServerComm(ServerCommunication serverComm) {
        this.serverComm = serverComm;
    }

    /**
     * adds the friend to a list of friends which the song should be shared with
     * @param button
     * @return
     */
    private View.OnClickListener friendOnClick(CheckBox button) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                CheckBox friend = (CheckBox) view;
                String friendName = friend.getText().toString();

                if(friend.isChecked()) {
                    friendsSelected.add(friendName);
                }
                else {
                    friendsSelected.remove(friendName);
                }
            }
        };
    }

    /**
     * Select all friends to share with
     * @return
     */
    private View.OnClickListener addAll() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                friendsSelected = new ArrayList<String>();

                for(int i = 0; i < profile.getFriends().size(); i++) {
                    CheckBox buttons = (CheckBox) getView().findViewById(3000+i);
                    buttons.setChecked(true);
                    friendsSelected.add(buttons.getText().toString());
                }
            }
        };
    }

    /**
     * Remove all friends selected to be shared with
     * @return
     */
    private View.OnClickListener removeAll() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                friendsSelected = new ArrayList<String>();

                for(int i = 0; i < profile.getFriends().size(); i++) {
                    CheckBox buttons = (CheckBox) getView().findViewById(3000+i);
                    buttons.setChecked(false);
                }
            }
        };
    }

    private View.OnClickListener shareSong() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                String songName = songSelected;
                JSONObject toSend;
                String URL = "http://proj-309-sb-b-5.cs.iastate.edu:8088/shareSongWithUser";
                for(int i = 0; i < friendsSelected.size(); i++) {
                    //Create the jsonFile to return to the server
                    toSend = jsonFile.shareSong(songName, friendsSelected.get(i), profile.getUsername());

                    //send the load song request to the server
                    serverComm.noResponse(toSend, URL);
                }

                //Load home fragment
                HomeFragment homeFragment = HomeFragment.newInstance(profile, serverComm);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relativeLayout_for_fragment, homeFragment, homeFragment.getTag()).commit();
            }
        };
    }

}
