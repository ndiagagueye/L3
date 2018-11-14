package com.example.gueye.memoireprevention2018.adaptaters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gueye.memoireprevention2018.R;
import com.example.gueye.memoireprevention2018.activities.MessageActivity;
import com.example.gueye.memoireprevention2018.modele.Chat;
import com.example.gueye.memoireprevention2018.modele.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gueye on 24/09/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static  final  int MSG_TYPE_LEFT = 0;
    public static  final  int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    public FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    String imageUrl;
    FirebaseUser fUser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageUrl){

        this.mContext = mContext ;
        this.mChat = mChat ;
        this.imageUrl = imageUrl ;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT){

            View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.chats_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);

        }else{

            View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.chats_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);

        }


    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.ViewHolder holder, int position) {

         Chat chat = mChat.get(position) ;
         holder.show_message.setText(chat.getMessage());

        Glide.with(mContext).load(imageUrl).into(holder.profileImage);


    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;

        private TextView show_message;
        private CircleImageView profileImage;

        public ViewHolder(View itemView) {
            super( itemView );

            mView = itemView;

            show_message = mView.findViewById( R.id.show_message );
            profileImage = mView.findViewById( R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
