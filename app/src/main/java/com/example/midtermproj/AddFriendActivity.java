package com.example.midtermproj;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    ImageButton avatar;
    Button SaveButton ;
    Button CancelButton ;
    EditText NameEditText ;
    EditText PhoneEditText ;
    EditText AddressEditText;
    private ArrayList<UserContact> mContactArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);
        SaveButton = findViewById(R.id.saveButton);
        CancelButton = findViewById(R.id.cancelButton);
        NameEditText = findViewById(R.id.button_name_input);
        PhoneEditText = findViewById(R.id.button_phone_input);
        AddressEditText = findViewById(R.id.button_address_input);
    }

    public void onClickAddPhoto(View view) {

    }

    public void onClickSave(View view) {
        Intent intent = new Intent(AddFriendActivity.this, MainActivity.class);

        //String name = NameEditText.getText().toString();
//        UserContact userContact = new UserContact(NameEditText.getText().toString(), PhoneEditText.getText().toString(), AddressEditText.getText().toString(), R.drawable.logo_quan_con_lan);
//        mContactArrayList.add(userContact);
        intent.putExtra("Name",NameEditText.getText().toString());
        intent.putExtra("Phone",PhoneEditText.getText().toString());
        intent.putExtra("Address",AddressEditText.getText().toString());
//        intent.putExtra("Photo",R.drawable.logo_quan_con_lan);

        startActivity(intent);


    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(AddFriendActivity.this, MainActivity.class);
        startActivity(intent);

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent    data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
//            uri=data.getData();
//            avatar = (ImageButton)findViewById(R.id.button_avatar);
//            avatar.setImageURI(uri);
//        }


}