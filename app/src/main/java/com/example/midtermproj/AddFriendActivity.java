package com.example.midtermproj;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    ImageButton avatar;
    ArrayList<UserContact> mContactList;
    EditText mNameTextView;
    EditText mPhoneTextView;
    EditText mAddressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        loadData();
        initDataForNewContact();
    }

    private void initDataForNewContact() {
        mNameTextView = (EditText) findViewById(R.id.button_name_input);
        mPhoneTextView = (EditText) findViewById(R.id.button_phone_input);
        mAddressTextView = (EditText) findViewById(R.id.button_address_input);
    }

    //get data from main
    private void loadData() {
        Intent intent = getIntent();
        mContactList =(ArrayList<UserContact>) intent.getSerializableExtra("contactlist");
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void onClickSave(View view) {
        String name = mNameTextView.getText().toString();
        String phone = mPhoneTextView.getText().toString();
        String address = mAddressTextView.getText().toString();

        // Make alert when user do not enter enough info
        if (name.length() == 0 || phone.length() == 0 || address.length() == 0)
        {
            Toast.makeText(this, "Please enter enough info", Toast.LENGTH_LONG).show();
        }
        else {
            mContactList.add(new UserContact(name, phone, address, 0));
            //send the contact list back to main activity
            Intent intent = new Intent();
            intent.putExtra("listcontactback", mContactList);
            setResult(RESULT_OK, intent);
            finish(); // calls on destroy
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
//            uri=data.getData();
//            avatar = (ImageButton)findViewById(R.id.button_avatar);
//            avatar.setImageURI(uri);
//        }


}