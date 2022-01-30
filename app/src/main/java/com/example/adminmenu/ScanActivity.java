package com.example.adminmenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class ScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    //camera permission is needed.
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;



    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);


        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

    }
    @Override
    public void onResume() {
        super.onResume();
        checkPermission(Manifest.permission.CAMERA,MY_CAMERA_PERMISSION_CODE);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    private void checkPermission(String camera, int myCameraPermissionCode) {
        if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            //Take Permission
            ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, myCameraPermissionCode);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {

        Log.v("Result 1", result.getContents()); // Prints scan results
        Log.v("Result 2", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        SecondPage.edit_trackNum.setText(result.getContents());
        ParcelData.setTrackNum(result.getContents());
        onBackPressed();
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (!(grantResults[0] == PackageManager.PERMISSION_GRANTED));
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
        }
    }
}