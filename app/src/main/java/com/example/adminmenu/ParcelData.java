package com.example.adminmenu;

public class ParcelData {
    private static String trackCode;
    private static String phoneNumber;

    public ParcelData() {
        trackCode = null;
        phoneNumber = "";
    }

    static void setTrackNum(String trackCodes)
    {
        trackCode = trackCodes;
    }

    static void setPhoneNumber(String phoneNumbers){ phoneNumber = phoneNumbers; }

    static String getTrackCode() { return trackCode; }

    static String getPhoneNumber() { return phoneNumber; }

}