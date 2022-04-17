package coms309.songusoid;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    /**
     * The selected user's Profile
     */
    private Profile profile;
    /**
     * The SeverCommunication Object
     */
    private ServerCommunication serverComm;
    /**
     * The linear layout used to display the selected List
     */
    private LinearLayout layoutOfList;

    /**
     * Old Password
     */
    private EditText confirmPassText;

    /**
     * Old Password
     */
    private EditText newPassText;
    /**
     * Old Password
     */
    private EditText confirmNewPassText;

    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Retrieve the profile passed to this fragment
        this.profile = (Profile) getArguments().getSerializable("Profile");

        return view;
    }
    /**
     * Creates a new ProfileFragment object which allows the passing of profile information
     * and server communication
     * @param profile
     * profile information passed from the navDraw activity
     * @param serverComm
     * server communication object created by navigation drawer
     * @return
     * returns a new HomeFragment
     */
    public static ProfileFragment newInstance(Profile profile, ServerCommunication serverComm) {
        //Create a new fragment and bundle the profile to it
        ProfileFragment fragment = new ProfileFragment();
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
        //Set up all the Profile specific textboxes
        TextView userName = (TextView) getView().findViewById(R.id.ProfileUserText);
        userName.setText("User: " + profile.getUsername());
        TextView numSongs = (TextView) getView().findViewById(R.id.NumSongCreated);
        numSongs.setText("Number of Songs: " + String.valueOf(profile.getNumSongs()));
        TextView numUpvotes = (TextView) getView().findViewById(R.id.NumUpVotes);
        numUpvotes.setText("Total number of upvotes: " + String.valueOf(profile.getUpvotes()));
        intiateSpinner();
        Button changePassword = getActivity().findViewById(R.id.changeButton);
        changePassword.setOnClickListener(changePasswordOnClick());
        Button submit = getActivity().findViewById(R.id.submit);
        submit.setOnClickListener(passwordHandler());
        Button cancel = getActivity().findViewById(R.id.cancel);
        cancel.setOnClickListener(cancelPopUp());
    }
    /**
     * Initiates The spinner used on the page
     */
    private void intiateSpinner(){
        Spinner spinner = (Spinner) getView().findViewById(R.id.profileSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.ProfilePageChoices, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                displayList(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                displayList(0);
            }
        });
    }

    private void displayList(int choice){
        //Initiate the Scrollable Friend List
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.scrollWindowFriend);
        layout.removeAllViews();
        String[] songNameList=null;
        ArrayList<Friend> friendsList=null;
        int sizeOfList;
        if(choice==0){
            friendsList= profile.getFriends();
            sizeOfList=friendsList.size();
        }

        else{
            songNameList=profile.getSongNames();
            sizeOfList=songNameList.length;
        }

        TextView[] listTextViews = new TextView[sizeOfList];
        LinearLayout.LayoutParams buttonParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < sizeOfList; i++) {
            listTextViews[i] = new TextView(getActivity());
            if(choice==0) listTextViews[i].setText(friendsList.get(i).getName());
            else listTextViews[i].setText(songNameList[i]);
            listTextViews[i].setLayoutParams(buttonParam);
            listTextViews[i].setTextSize(16);
            listTextViews[i].setTextColor(Color.BLACK);
            layout.addView(listTextViews[i]);
        }
    }

    /**
     * Changes the password of the user and tells the user if works
     *
     */
    private View.OnClickListener changePasswordOnClick() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                View popup = (View) getActivity().findViewById(R.id.CreatePopUp1);
                popup.setVisibility(View.VISIBLE);
                confirmPassText = (EditText) getView().findViewById(R.id.oldPasswordText);
                newPassText = (EditText) getView().findViewById(R.id.newPasswordText);
                confirmNewPassText = (EditText) getView().findViewById(R.id.confirmNewPasswordText);

            }
        };
    }
    private View.OnClickListener passwordHandler(){
        return new View.OnClickListener() {
            public void onClick(View view) {
                Context context = getContext();
                int duration = Toast.LENGTH_SHORT;
                if(!confirmPassText.getText().toString().equals(profile.getPassword())){
                    CharSequence text = "Password didn't match old password";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if(!newPassText.getText().toString().equals(confirmNewPassText.getText().toString())){
                    CharSequence text = "New Passwords didn't match";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    String newPassword= String.valueOf(confirmNewPassText.getText().toString());
                    CharSequence text = "New password set";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    JSONObject newPasswordObj= jsonFile.changePassword(newPassword,profile);
                    serverComm.changePass(newPasswordObj,newPassword);
                    profile.setPassword(newPassword);
                    closeCreatePopUp1();
                }
                clearPasswords();
            }
        };

    }
    private View.OnClickListener cancelPopUp() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                clearPasswords();
                closeCreatePopUp1();

            }
        };
    }
    private void clearPasswords(){
        confirmPassText.setText("");
        newPassText.setText("");
        confirmNewPassText.setText("");
    }
    private void closeCreatePopUp1() {
        View createPopUp = (View) getView().findViewById(R.id.CreatePopUp1);
        createPopUp.setVisibility(View.INVISIBLE);
    }


    /**
     * Sets the server communication object for the fragment
     * @param serverComm
     */
    public void setServerComm(ServerCommunication serverComm) {
        this.serverComm = serverComm;
    }
}
