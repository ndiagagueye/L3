package com.example.gueye.memoireprevention2018.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gueye.memoireprevention2018.MainActivity;
import com.example.gueye.memoireprevention2018.R;
import com.example.gueye.memoireprevention2018.SetupActivity;
import com.example.gueye.memoireprevention2018.adaptaters.MessageAdapter;
import com.example.gueye.memoireprevention2018.modele.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    String imageTo, usernameTo, userIdTo;
    private CircleImageView profile_image;
    private TextView username;
    private Toolbar mToolbar;
    private ImageView btnSendMessage;
    private EditText messageText;
    private String current_user_id;
    private FirebaseUser fUser;

    private  DatabaseReference reference;

    private MessageAdapter messageAdapter;
    private List<Chat> mChat;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_message );

        init();

        btnSendMessage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageText.getText().toString();
                if(!message.equals("")){
                    senMessage(current_user_id, userIdTo, message);
                }else{
                    Toast.makeText( MessageActivity.this, "Saisir un message", Toast.LENGTH_SHORT ).show();
                }
                messageText.setText( "" );
            }
        } );
    }

    private void init() {

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        userIdTo = getIntent().getStringExtra( "user_id" );
        usernameTo = getIntent().getStringExtra( "name" );
        imageTo = getIntent().getStringExtra( "image" );

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        current_user_id = mAuth.getCurrentUser().getUid();

        profile_image = findViewById( R.id.profile_image_to);
        username = findViewById( R.id.username_to);
        mToolbar = findViewById( R.id.user_to__toobar);

        btnSendMessage = findViewById( R.id.send_message_to );
        messageText = findViewById( R.id.message_text );

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        recyclerView = findViewById(R.id.recycler_message);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        username.setText(usernameTo );
        Glide.with(MessageActivity.this).load(imageTo).into(profile_image);

        readMessage(current_user_id, userIdTo, imageTo);



        Toast.makeText( this, "name "+usernameTo+" user_id "+userIdTo+" image "+imageTo, Toast.LENGTH_SHORT ).show();
    }

    private  void senMessage(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> messageMap = new HashMap<>();
        messageMap.put("sender" , sender);
        messageMap.put("receiver", receiver);
        messageMap.put("message", message);

        mFirestore.collection("Chats").document().set(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(MessageActivity.this, "message send ", Toast.LENGTH_SHORT).show();

                }else{
                    String errorMsg = task.getException().getMessage();
                    Toast.makeText(MessageActivity.this, "Une erreur s'est produite. "+errorMsg, Toast.LENGTH_SHORT).show();

                }
            }
        });

        reference.child("Chats").push().setValue(messageMap);

        // add user to the chat fragment

        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fUser.getUid())
                .child(userIdTo);

        chatRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){

                    chatRef.child("id").setValue(userIdTo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );



    }

    private void readMessage(final String myId, final String userId, final String imageUrl){
        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mChat.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Chat chat = snapshot.getValue(Chat.class);

                    if(chat.getReceiver() !=null && chat.getSender() != null) {

                        if (chat.getReceiver().equals( myId ) && chat.getSender().equals( userId ) || chat.getReceiver().equals( userId ) && chat.getSender().equals( myId )) {
                            mChat.add( chat );

                        }
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat, imageUrl);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }

    private void status(String status){

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());

        HashMap<String, Object> statusMap = new HashMap<>();
        statusMap.put("status", status);
        reference.updateChildren(statusMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
