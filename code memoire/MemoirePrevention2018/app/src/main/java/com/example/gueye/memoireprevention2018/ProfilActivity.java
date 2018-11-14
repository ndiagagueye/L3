package com.example.gueye.memoireprevention2018;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ProfilActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mUsername, mTelephone, mTelephoneEmergency;
    private ImageView mProfileImage;
    private ImageView mBlurImage;
    private Switch mIsbenevol;
    private Switch mIsAlerted;
    private Button mBtnSaveInfos;

    //Firebase

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private String mUserId, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profil );

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user_id = getIntent().getStringExtra("user_id");
        if(!TextUtils.isEmpty(user_id))
            mUserId = user_id;
        else
            mUserId = mAuth.getCurrentUser().getUid();

        mToolbar = findViewById(R.id.profile_toolbar);
        //mBlurImage = findViewById(R.id.profile_user_image_blur);
        mBtnSaveInfos = findViewById(R.id.btn_edit_profile);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mToolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                finish();
            }
        } );


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Profile");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


        mUsername = findViewById(R.id.profile_username);
        mTelephone = findViewById(R.id.profile_telephone);
        mTelephoneEmergency = findViewById(R.id.profile_user_contact_here);
        mProfileImage = findViewById(R.id.profile_image_circle);
        mIsAlerted = findViewById(R.id.profile_alerte);
        mIsbenevol = findViewById(R.id.profile_benevol);

        //mIsbenevol.setOnCheckedChangeListener( (CompoundButton.OnCheckedChangeListener) this );




        mFirestore.collection("Users").document(mUserId).get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.getString( "name" );
                String telephone = documentSnapshot.getString( "telephone" );
                String telephoneEmergency = documentSnapshot.getString( "telephoneEmergency");
                String image = documentSnapshot.getString( "image" );

                mUsername.setText( name );
                mTelephone.setText(telephone);
                mTelephoneEmergency.setText(telephoneEmergency);

                RequestOptions plasholderOption = new RequestOptions();
                plasholderOption.placeholder( R.drawable.back );

                Glide.with(ProfilActivity.this ).setDefaultRequestOptions( plasholderOption ).load( image ).into( mProfileImage );

                //ImageView resultImage = (ImageView) findViewById(R.id.profile_user_image_blur);
                //Bitmap resultBmp = BlurBuilder.blur(ProfilActivity.this, BitmapFactory.decodeResource(getResources(), R.drawable.profile));
                //resultImage.setImageBitmap(resultBmp);


              /*  ImageView blurImage = (ImageView) findViewById(R.id.profile_user_image_blur);

                MultiTransformation multi = new MultiTransformation(
                        new BlurTransformation(25),
                        new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
                Glide.with(ProfilActivity.this).load(image)
                        .apply(bitmapTransform(multi))
                        .into((blurImage));
*/
                //Glide.with(ProfilActivity.this ).setDefaultRequestOptions( plasholderOption ).load().into( mProfileImage );


            }
        } );



    }

}
