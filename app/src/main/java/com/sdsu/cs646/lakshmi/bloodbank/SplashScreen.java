package com.sdsu.cs646.lakshmi.bloodbank;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Splash screen timer
        int SPLASH_TIME_OUT = 2000;

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
               Intent go = new Intent(SplashScreen.this, LoginActivity.class);

             //   Intent go = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(go);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
