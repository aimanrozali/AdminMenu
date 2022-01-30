package com.example.adminmenu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SecondPage extends AppCompatActivity {

    private Button button;
    private long maxid = 0;
    public static EditText edit_trackNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Submit details
        edit_trackNum = findViewById(R.id.edit_trackNum);
        final EditText edit_cusPhone = findViewById(R.id.edit_cusPhone);
        Button btn_submit = findViewById(R.id.btn_submit);

        final DatabaseReference dbParcel = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Parcel.class.getSimpleName());
        final DatabaseReference dbCustomer = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Users.class.getSimpleName());

        //back button
        button = (Button) findViewById(R.id.pageMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        edit_trackNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(SecondPage.this,ScanActivity.class);
                startActivity(it);
            }
        });

        //Add button
        btn_submit.setOnClickListener(v ->
        {
            if (edit_trackNum.getText().toString().isEmpty() || edit_cusPhone.getText().toString().isEmpty()) {
                Toast.makeText(SecondPage.this, "Please enter details", Toast.LENGTH_SHORT).show();
            }
            else {
                dbCustomer.orderByChild("phone").equalTo(edit_cusPhone.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Users user = new Users();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                user = dataSnapshot.getValue(Users.class);
                            }

                            //Save customer id according to phone number
                            String cus_id = user.getcusId();

                            Query que = dbParcel.orderByChild("trackNum").equalTo(edit_trackNum.getText().toString().trim());
                            que.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Toast.makeText(SecondPage.this, "Same tracking number detected. The parcel has been inserted before", Toast.LENGTH_SHORT).show();
                                    } else {
                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        LocalDate currDate = LocalDate.now();

                                        Parcel parcel = new Parcel(edit_trackNum.getText().toString().trim(), cus_id, dtf.format(currDate));
                                        dbParcel.child(edit_trackNum.getText().toString().trim()).setValue(parcel).addOnSuccessListener(suc -> {
                                            Toast.makeText(SecondPage.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                        }).addOnFailureListener(er -> {
                                            Toast.makeText(SecondPage.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                                        openMain();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(SecondPage.this, "There is no user with this phone number", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    public void openMain() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}