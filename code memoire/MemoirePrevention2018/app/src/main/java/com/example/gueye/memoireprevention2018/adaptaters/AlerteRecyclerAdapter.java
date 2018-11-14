package com.example.gueye.memoireprevention2018.adaptaters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gueye.memoireprevention2018.R;
import com.example.gueye.memoireprevention2018.activities.CommentsActivity;
import com.example.gueye.memoireprevention2018.activities.PostAlerteDetails;
import com.example.gueye.memoireprevention2018.modele.BlogPost;
import com.example.gueye.memoireprevention2018.modele.Likes;
import com.example.gueye.memoireprevention2018.modele.Users;
import com.example.gueye.memoireprevention2018.utils.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gueye on 13/08/18.
 */

public class AlerteRecyclerAdapter extends RecyclerView.Adapter<AlerteRecyclerAdapter.ViewHolder> {


    public List<BlogPost> blog_list;
    public List<Users> user_list;
    public List<Likes> user_like;
    public Context context;
    public FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    public AlerteRecyclerAdapter(List<BlogPost> blog_list, List<Users> user_list, List<Likes> user_like , FirebaseFirestore firebaseFirestore,FirebaseAuth firebaseAuth){
        this.blog_list = blog_list;
        this.user_list = user_list;
        this.user_like = user_like;
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth ;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    // load data
    @Override
    public void onBindViewHolder(@NonNull final AlerteRecyclerAdapter.ViewHolder holder, final int position) {


        holder.setIsRecyclable(false);
        // Get Id for target post
        final String blogPostId =  blog_list.get(position).blogPostId;
        //get Current User id who is login
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();
        // Put description
        String desc_data = blog_list.get(position).getDescription();
        holder.setDescText(desc_data);

        // Put image post
        // Put type
        int i = blog_list.get(position).getType();
        holder.setTitleText(Const.DEFAULT_TYPES[i]);

        //Toast.makeText( context, " "+userImageLike, Toast.LENGTH_SHORT ).show();

        int postionType = blog_list.get(position).getType();
        //holder.setTypeOfAlert(Const.DEFAULT_TYPES[postionType]);

        final String image_url = blog_list.get(position).getImage_url();
        String thumbUri = blog_list.get(position).getImage_thumb();

        if (image_url.equals("") && thumbUri.equals("")){

            holder.blogImageView.setImageResource(Const.DEFAULT_RESOURCE_IMAGES[postionType]);

        }else{
            holder.setBlogImage(image_url, thumbUri);
        }

        // get id username for post
        String blog_user_id = blog_list.get(position).getUser_id();

        if (blog_user_id.equals(currentUserId)){

         //   holder.deletePostBtn.setEnabled(true);
           // holder.deletePostBtn.setVisibility(View.VISIBLE);
        }


        // get username and profile image
        String userName = user_list.get(position).getName();
        String userImage = user_list.get(position).getImage();
        holder.setuserData(userName,userImage);


        // Put date post

        String date = blog_list.get( position ).getTimestamp();
        holder.setDate(date);
        // LIKES COUNT

        firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (documentSnapshots == null) return;

                if(!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();

                    holder.updateLikesCount(count);


                } else {

                    holder.updateLikesCount(0);

                }

            }
        });


        //Get Likes
        firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (documentSnapshot == null) return;
                if(documentSnapshot.exists()){

                    holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.drawable.action_like_accent));

                }  else {

                    holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.drawable.ic_like_holo_light));

                }

            }
        });


        //Likes Feature
        holder.blogLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(!task.getResult().exists()){

                            Map<String, Object> likesMap = new HashMap<>();
                            likesMap.put( "user_id",currentUserId );
                            likesMap.put("timestamp", FieldValue.serverTimestamp());
                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).set(likesMap);

                        } else {

                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).delete();

                        }

                    }
                });
            }
        });

        //Get Image Users Likes



        // Clike on comment btn
        holder.blogCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentIntent = new Intent(context, CommentsActivity.class);
                commentIntent.putExtra("blog_post_id", blogPostId);
                context.startActivity(commentIntent);
            }
        });

        holder.tvMoreAboutPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double latitude = blog_list.get(position).getLatitude();
                Log.d("AlerteRecycleAdapter", "onClick: latitude "+ latitude);
                double longitude = blog_list.get(position).getLongitude();

                Toast.makeText(context, "On a recupéré la position du post  post ", Toast.LENGTH_SHORT).show();
                
                Intent postAlertDetailIntent = new Intent(context, PostAlerteDetails.class);
                postAlertDetailIntent.putExtra(Const.LONG,longitude);
                postAlertDetailIntent.putExtra( Const.LAT,latitude);
                
                context.startActivity(postAlertDetailIntent);
            }
        });

        // DELETE POST

       /* holder.deletePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Posts").document(blogPostId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        blog_list.remove(position);
                        user_list.remove(position);
                    }
                });
            }
        }); */



    }

    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // ATTRIBUTES
        private View mView;
        private TextView descView;
        private TextView post_tv_title;
        private ImageView blogImageView;
        private TextView blogDate;
        private TextView blogUserName;
        private CircleImageView blogUserImage;
        private TextView tvMoreAboutPost ;

        private ImageView blogLikeBtn;
        private TextView blogLikeCount;
        private ImageView blogCommentBtn;

        private CircleImageView blogFirstUserLikeImage;
        private CircleImageView blogSecondUserLikeImage;
       // private Button deletePostBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView ;
            blogLikeBtn = mView.findViewById(R.id.blog_like_btn);
            blogCommentBtn = mView.findViewById(R.id.blog_comment_btn);
            //deletePostBtn = mView.findViewById(R.id.blog_delete_post_btn);
            blogImageView = mView.findViewById(R.id.blog_image);
            tvMoreAboutPost = mView.findViewById(R.id.post_tv_more);


            tvMoreAboutPost.setOnClickListener(this);
            
        }

        // Set description
        public void setDescText(String descText){

            descView = mView.findViewById(R.id.blog_desc);
            descView.setText(descText);
        }

        // Set title
        public void setTitleText(String titleText){

            post_tv_title = mView.findViewById(R.id.post_tv_title);
            post_tv_title.setText(titleText);
        }
        // set image blog
        public void setBlogImage(String downloadUri, String thumbUri){

            blogImageView = mView.findViewById(R.id.blog_image);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);

            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri).thumbnail(
                    Glide.with(context).load(thumbUri)
            ).into(blogImageView);

        }


        // set date
        public void setDate(String date){
            blogDate = mView.findViewById(R.id.blog_date);

            blogDate.setText(date);
        }

        // set usermane and image profile

        public  void setuserData(String name, String image){
            blogUserName = mView.findViewById(R.id.blog_user_name);
            blogUserImage = mView.findViewById(R.id.blog_user_image);
            blogUserName.setText(name);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.profile_placeholder);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(image).into(blogUserImage);
        }

        public void updateLikesCount(int count){
            blogLikeCount = mView.findViewById(R.id.blog_lke_count);
            blogLikeCount.setText(count + " Likes");
        }


        @Override
        public void onClick(View view) {

            Toast.makeText(context, "On m'a clické hahahahah ", Toast.LENGTH_SHORT).show();
            
        }

    }
}
