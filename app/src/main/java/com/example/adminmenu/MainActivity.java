package com.example.adminmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

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

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    }