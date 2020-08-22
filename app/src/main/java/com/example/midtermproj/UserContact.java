package com.example.midtermproj;

import android.net.Uri;

import java.io.Serializable;

public class UserContact implements Serializable {






    String mName;
    String mPhoneNumber;
    String mAddress;
    String mPhotoID;


    public UserContact(String mName, String phoneNumber, String address, String photoID) {
        this.mPhotoID= photoID;
        this.mName = mName;
        this.mPhoneNumber = phoneNumber;
        this.mAddress = address;
    }

    public String getPhotoID() {
        return mPhotoID;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setPhotoID(String mPhotoID) {
        this.mPhotoID = mPhotoID;
    }
}
