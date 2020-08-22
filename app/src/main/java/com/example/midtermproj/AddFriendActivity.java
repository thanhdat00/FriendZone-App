package com.example.midtermproj;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    private static int LOAD_IMAGE_RESULTS = 1;

    ImageButton avatar;
    ArrayList<UserContact> mContactList;
    EditText mNameTextView;
    EditText mPhoneTextView;
    EditText mAddressTextView;
    ImageView mImage;

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
        mImage = (ImageView) findViewById(R.id.button_avatar);
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
            mContactList.add(new UserContact(name, phone, address, R.drawable.logo_quan_con_lan));
            //send the contact list back to main activity
            Intent intent = new Intent();
            intent.putExtra("listcontactback", mContactList);
            setResult(RESULT_OK, intent);
            finish(); // calls on destroy
        }
    }

    public void onClickAddPhoto(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            mImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

}