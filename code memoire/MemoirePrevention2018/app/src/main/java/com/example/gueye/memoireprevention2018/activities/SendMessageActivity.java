package com.example.gueye.memoireprevention2018.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gueye.memoireprevention2018.ModeleService.Common;
import com.example.gueye.memoireprevention2018.ModeleService.MyResponse;
import com.example.gueye.memoireprevention2018.ModeleService.Notification;
import com.example.gueye.memoireprevention2018.ModeleService.Sender;
import com.example.gueye.memoireprevention2018.ProfilActivity;
import com.example.gueye.memoireprevention2018.R;
import com.example.gueye.memoireprevention2018.Remote.APIService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageActivity extends AppCompatActivity {

    private String mToken;
    private String mCurrentId;
    private String mUserName;
    private String mUserId;
    private String image;
    private EditText mMessageView;
    private ImageView mBtnSend;
    private Toolbar mToolbar;
    private CircleImageView imageUserSms;
    private TextView nameUserSms;

    APIService mService;


    //Firebase

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    private final String TAG = "SendActivty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_send_message );

        init();
        mBtnSend.setEnabled( false );

        mBtnSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = mMessageView.getText().toString();
                if (!TextUtils.isEmpty(message)){

                    Map<String ,Object > notificationMessage = new HashMap<>();
                    notificationMessage.put("message", message);
                    notificationMessage.put("from", mCurrentId);
                    notificationMessage.put("to", mUserName);
                    notificationMessage.put("imageUserToSendMessage", image);
                    mFirestore.collection("Users/" +mCurrentId+ "/Notifications").add(notificationMessage).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(SendMessageActivity.this, "Message a été envoyé avec success...", Toast.LENGTH_LONG).show();
                            Log.d( TAG, "token de "+mUserName+" "+mToken );
                            mMessageView.setText( "" );

                        }
                    }).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(SendMessageActivity.this, "Une erreur est survenue... "+e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    } );
                }
            }
        } );
    }

    public void init(){
        mFirestore = FirebaseFirestore.getInstance();
        mCurrentId = FirebaseAuth.getInstance().getUid();

        mMessageView = findViewById( R.id.message_filed );
        mBtnSend = findViewById( R.id.send_message_btn );

        imageUserSms = findViewById( R.id.user_image_messaging );
        nameUserSms = findViewById( R.id.user_name_messaging );

        mUserId = getIntent().getStringExtra("user_id");
        mUserName = getIntent().getStringExtra("user_name");
        mToken = getIntent().getStringExtra("token_id");
        image = getIntent().getStringExtra("image" );

        Common.currentToken = mToken;
        //Log.d( "Message Token", Common.currentToken );
        mService = Common.getFCMClient();

        mToolbar = findViewById( R.id.message_toolbar );
        setSupportActionBar( mToolbar );
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        nameUserSms.setText( mUserName );
        RequestOptions plasholderOption = new RequestOptions();
        plasholderOption.placeholder( R.drawable.profile_placeholder );

        Glide.with(SendMessageActivity.this ).setDefaultRequestOptions( plasholderOption ).load( image ).into( imageUserSms );

    }


    // Notification Push
    private void sendNotificationPush(String description,String typeAlerte) {

        Notification notification = new Notification(description,typeAlerte);

        Sender sender = new Sender( Common.currentToken,notification);
        mService.sendNotification(sender).enqueue( new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                if(response.body().success == 1)
                    Toast.makeText( SendMessageActivity.this, "Success", Toast.LENGTH_SHORT ).show();
                else
                    Toast.makeText( SendMessageActivity.this, "Failed", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

                Log.e( "Error ", t.getMessage());

            }
        } );

    }



}
