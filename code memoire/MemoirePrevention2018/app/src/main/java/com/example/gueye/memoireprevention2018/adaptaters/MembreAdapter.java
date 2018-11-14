package com.example.gueye.memoireprevention2018.adaptaters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gueye.memoireprevention2018.R;
import com.example.gueye.memoireprevention2018.activities.MessageActivity;
import com.example.gueye.memoireprevention2018.modele.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gueye on 24/09/18.
 */

public class MembreAdapter extends RecyclerView.Adapter<MembreAdapter.ViewHolder> {

    private Context mContext;
    private List<Users> mUsers;
    public FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    boolean isChat;

    public MembreAdapter(List<Users> mUsers, Context mContext, boolean isChat){
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.isChat = isChat;

    }

    @NonNull
    @Override
    public MembreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.membres_item_layout, parent, false);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        return new MembreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MembreAdapter.ViewHolder holder, int position) {

        final String name = mUsers.get( position ).getName();
        holder.username.setText(name);
        String telephone = mUsers.get( position ).getTelephone();
        holder.telephone.setText(telephone);
        final String image = mUsers.get( position ).getImage();
        Glide.with(mContext).load(image).into(holder.profileImage);

        String status = mUsers.get(position).getStatus();

        if(isChat){
            if(status.equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            }else{
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        }else{
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        final String user_id_to = mUsers.get( position ).usersId;

        holder.mView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messageIntent = new Intent(mContext, MessageActivity.class);
                messageIntent.putExtra("image", image);
                messageIntent.putExtra("user_id", user_id_to);
                messageIntent.putExtra("name", name);
                mContext.startActivity( messageIntent );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;

        private TextView username;
        private TextView telephone;
        private CircleImageView profileImage;

        private CircleImageView img_on, img_off ;

        public ViewHolder(View itemView) {
            super( itemView );

            mView = itemView;

            username = mView.findViewById( R.id.membre_list_name );
            telephone = mView.findViewById( R.id.membre_list_call );
            profileImage = mView.findViewById( R.id.membre_list_image);
            img_on = mView.findViewById( R.id.img_on);
            img_off = mView.findViewById( R.id.img_off);
        }
    }
}
