package com.example.midtermproj;

public class UserContact {

    String mName;
    String mPhoneNumber;
    String mAddress;

    public UserContact(String mName, String phoneNumber, String address) {
        this.mName = mName;
        this.mPhoneNumber = phoneNumber;
        this.mAddress = address;
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
}
