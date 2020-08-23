package com.example.midtermproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class EditContactActivity extends AppCompatActivity {

    private static int LOAD_IMAGE_RESULTS = 1;

    ImageButton avatar;
    ArrayList<UserContact> mContactList;
    int mPosition;
    EditText mNameTextView;
    EditText mPhoneTextView;
    EditText mAddressTextView;
    ImageView mImage;
    Uri pickedImage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_friend);

        loadData();
        initDataForContact();
    }

    private void initDataForContact() {
        mNameTextView = (EditText) findViewById(R.id.button_name_input);
        mPhoneTextView = (EditText) findViewById(R.id.button_phone_input);
        mAddressTextView = (EditText) findViewById(R.id.button_address_input);
        mImage = (ImageView) findViewById(R.id.button_avatar);

        mNameTextView.setText(mContactList.get(mPosition).getName());
        mPhoneTextView.setText(mContactList.get(mPosition).getPhoneNumber());
        mAddressTextView.setText(mContactList.get(mPosition).getAddress());
    }

    private void loadData() {
        Intent intent = getIntent();
        mContactList = (ArrayList<UserContact>) intent.getSerializableExtra("contactlist");
        mPosition = intent.getIntExtra("pos", -1);
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
        String photo;
        if (pickedImage!=null) {
            photo = pickedImage.toString();
        }
        else
        {
            photo = null;
        }
//        Toast.makeText(this,photo,Toast.LENGTH_LONG).show();

        // Make alert when user do not enter enough info
        if (name.length() == 0 || phone.length() == 0 || address.length() == 0 )
        {
            Toast.makeText(this, "Please enter enough info", Toast.LENGTH_LONG).show();
        }
        else {
            mContactList.get(mPosition).setName(name);
            mContactList.get(mPosition).setPhoneNumber(phone);
            mContactList.get(mPosition).setAddress(address);
            if (photo != null) mContactList.get(mPosition).setPhotoID(photo);
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
        Bitmap bmp = null;
        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();


            // Now we need to set the GUI ImageView data with data read from the picked file.
            //            //change to bitmap
            try {
                bmp = android.provider.MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //set imageView
            mImage.setImageBitmap(bmp);

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }
}