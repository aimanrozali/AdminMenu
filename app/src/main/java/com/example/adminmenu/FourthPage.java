package com.example.adminmenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

// implements onClickListener for the onclick behaviour of button
public class FourthPage extends AppCompatActivity implements View.OnClickListener {
    Button scanBtn;
    final DatabaseReference dbParcel = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Parcel.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_page);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //back button
        Button btn_back = (Button) findViewById(R.id.pageMain);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        // adding listener to the button
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Onclick, start QR scanner
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.setPrompt(" Scan a QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        Query que = dbParcel.orderByChild("cusId").equalTo(String.valueOf(intentResult.getContents()));
                        que.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    data.getRef().removeValue();
                                }
                                Toast.makeText(FourthPage.this, "Confirmed! Parcel data has been cleared" , Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(FourthPage.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(FourthPage.this, "Parcel data are not cleared", Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        };

        // If cancel scan, prompt cancelled message
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {

                //Get number of parcel for customer
                Query que = dbParcel.orderByChild("cusId").equalTo(intentResult.getContents());
                que.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int parcel_count = 0;
                        for (DataSnapshot data : snapshot.getChildren()) {
                            parcel_count++;
                        }

                        if (parcel_count != 0)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FourthPage.this);
                            builder.setMessage("Customer ID: " + String.valueOf(intentResult.getContents()) +"\nNumber of parcel available for pickup: " + parcel_count +"\n\nDo you want to confirm pickup?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Customer ID: " + String.valueOf(intentResult.getContents()) + "\nDon't have any parcel available",  Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
                else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}