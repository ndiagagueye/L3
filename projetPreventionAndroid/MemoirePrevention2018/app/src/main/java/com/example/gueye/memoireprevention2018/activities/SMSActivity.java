package com.example.gueye.memoireprevention2018.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gueye.memoireprevention2018.R;

public class SMSActivity extends AppCompatActivity {

    private EditText messageT, numberphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
    }

    public void btnSend(View v) {
        numberphone = (EditText) findViewById(R.id.editText);
        messageT = (EditText) findViewById(R.id.editText2);
    }



}
