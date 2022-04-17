package com.example.rockm.sqlstuff;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;
import java.util.List;

public class SendActivity extends Activity {
    public static final String EXTRA_DBASE = "com.example.rockm.DBASE";
    private DatabaseHandle db = new DatabaseHandle(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
//        int count = db.getContactsCount();
//        while(count>0){
//            db.deleteContact(db.getContact(count));
//            count--;
//        }
//
//        DatabaseHandle db = new DatabaseHandle(this);
//
//        /**
//         * CRUD Operations
//         * */
//        // Inserting Contacts
//        Log.d("Insert: ", "Inserting ..");
//        db.addContact(new Contact("Ravi", "9100000000"));
//        db.addContact(new Contact("Srinivas", "9199999999"));
//        db.addContact(new Contact("Tommy", "9522222222"));
//        db.addContact(new Contact("Karthik", "9533333333"));
//
//        // Reading all contacts
//        Log.d("Reading: ", "Reading all contacts..");
//        List<Contact> contacts = db.getAllContacts();
//
//        for (Contact cn : contacts) {
//            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
//            // Writing Contacts to log
//            Log.d("Name: ", log);
//        }
    }
    public void submit(View view){
//        Intent intent = new Intent(this, DisplayCont.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
        EditText userNm= (EditText) findViewById(R.id.nameEdit);
        String user = userNm.getText().toString();
        EditText password= (EditText) findViewById(R.id.PasswordEdit);
        String passwd = password.getText().toString();
        db.addContact(new Contact(user,passwd));
    }
    public void Disp(View view){
        Intent intent = new Intent(this, DisplayCont.class);
        List<Contact> contacts = db.getAllContacts();
        String listCont ="";
        for (Contact cn : contacts) {
            listCont += "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Password: " + cn.getPhoneNumber()+"\n";
        }
        intent.putExtra(EXTRA_DBASE, listCont);
        startActivity(intent);
    }
}
