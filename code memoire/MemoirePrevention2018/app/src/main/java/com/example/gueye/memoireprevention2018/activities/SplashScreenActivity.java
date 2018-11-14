package com.example.gueye.memoireprevention2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gueye.memoireprevention2018.MainActivity;
import com.example.gueye.memoireprevention2018.R;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {


    private static int SPLASH_TIME = 1000; //This is 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );

        /*new Timer().schedule( new TimerTask() {
            @Override
            public void run() {
                 startActivity(new Intent(getApplicationContext(), MainActivity.class) );
                 finish();
                 overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        } , SPLASH_TIME );*/

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(mySuperIntent);
                /* This 'finish()' is for exiting the app when back button pressed
                *  from Home page which is ActivityHome
                */
                finish();
            }
        }, SPLASH_TIME);
    }
}
