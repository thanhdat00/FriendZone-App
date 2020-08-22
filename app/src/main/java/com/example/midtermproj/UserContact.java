package com.example.midtermproj;

import java.io.Serializable;

public class UserContact implements Serializable {



    int mPhotoID;



    String mName;
    String mPhoneNumber;
    String mAddress;

    public UserContact(String mName, String phoneNumber, String address, int photoID) {
        this.mPhotoID= photoID;
        this.mName = mName;
        this.mPhoneNumber = phoneNumber;
        this.mAddress = address;
    }

    public int getPhotoID() {
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

    public void setPhotoID(int mPhotoID) {
        this.mPhotoID = mPhotoID;
    }
}
