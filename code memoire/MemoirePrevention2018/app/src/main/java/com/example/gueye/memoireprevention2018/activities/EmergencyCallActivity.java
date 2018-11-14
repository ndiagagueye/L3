package com.example.gueye.memoireprevention2018.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.gueye.memoireprevention2018.R;

public class EmergencyCallActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_emergency_call );

        mToolbar = findViewById(R.id.emergencency_call_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Urgences");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }
}
