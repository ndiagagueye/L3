package com.example.gueye.memoireprevention2018.services;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.gueye.memoireprevention2018.LoginActivity;
import com.example.gueye.memoireprevention2018.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gueye on 23/09/18.
 */

public class NetworkStatus extends AppCompatActivity {



    public boolean checkNetworkConnectionStatus() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()){ //connected with either mobile or wifi

            return true;
        }
        else { //no internet connection

            return false;
        }
    }

    public void tryAgain() {

        setContentView( R.layout.status_network_connexion );
        ImageView mConStatusIv = findViewById(R.id.conStatusIv);
        TextView mConStatusTv = findViewById(R.id.conStatusTv);
        Button mConStatusBtn = findViewById(R.id.conStatusBtn);
        mConStatusIv.setImageResource(R.drawable.no_connection);
        mConStatusTv.setText("Veuillez vérifier votre connection internet, puis reéssayer SVP!");

        mConStatusBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext(), LoginActivity.class ) );
            }
        } );
    }
}
