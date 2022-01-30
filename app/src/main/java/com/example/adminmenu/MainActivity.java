package com.example.adminmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimation;
    private ImageView button2;


    public static ImageView button4;
    TextView dashboard,parcel,scanner;

    @Override
    public void onBackPressed() {
        openMain();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
//        controller.hide( WindowInsetsCompat.Type.systemBars());

        //dashboard
        lottieAnimation = findViewById( R.id.headerwaves1 );
        lottieAnimation = findViewById( R.id.headerwaves2 );

        parcel = findViewById( R.id.parcel );
        scanner = findViewById( R.id.scanner );
        dashboard = findViewById( R.id.dasboard );

        button2 = findViewById(R.id.imageView4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForm();
            }
        });


        button4 = findViewById(R.id.imageView6);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQR();
            }
        });

    }

        public void openMain() {
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        }


        //Open Parcel Form
        public void openForm() {
            Intent intent = new Intent (this, SecondPage.class);
            startActivity(intent);
        }

        //Open QR Scanner
        public void openQR() {
            Intent intent = new Intent (this, QRActivity.class);
            startActivity(intent);
        }



    }