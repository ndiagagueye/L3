package com.example.gueye.memoireprevention2018.activities;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.gueye.memoireprevention2018.R;
import com.example.gueye.memoireprevention2018.adaptaters.UsersRecyclerAdapter;
import com.example.gueye.memoireprevention2018.fragments.ChatsFragment;
import com.example.gueye.memoireprevention2018.fragments.MembresFragment;
import com.example.gueye.memoireprevention2018.modele.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MembreActivity extends AppCompatActivity {

    private RecyclerView mUsersListView;
    private List<Users> usersList;
    private UsersRecyclerAdapter mUserRecyclerAdapter;

    private FirebaseFirestore mFirestore;


    private Toolbar mToolbar;
    private FirebaseUser fUser;
    private DatabaseReference reference;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private CircleImageView u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_membre );

        tableLayout = findViewById( R.id.tab_layout_member );
        viewPager = findViewById( R.id.view_pager_member );
        fUser = FirebaseAuth.getInstance().getCurrentUser();


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager() );
        viewPagerAdapter.addFragment( new MembresFragment(), "Membres" );
        viewPagerAdapter.addFragment( new ChatsFragment(), "Discussion" );

        viewPager.setAdapter(viewPagerAdapter);

        tableLayout.setupWithViewPager(viewPager);


        //init();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get( position );
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public  void addFragment(Fragment fragment, String title){
            fragments.add( fragment );
            titles.add( title );

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get( position );
        }
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

   /* private void init() {

        mToolbar = findViewById( R.id.membre_toolbar );
        setSupportActionBar( mToolbar );
        getSupportActionBar().setTitle( "Membres" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );



        mFirestore =FirebaseFirestore.getInstance();
        mUsersListView = findViewById(R.id.result_recycle_view);

        usersList = new ArrayList<>();
        mUserRecyclerAdapter = new UsersRecyclerAdapter(this, usersList);

        mUsersListView.setHasFixedSize(true);
        mUsersListView.setLayoutManager(new LinearLayoutManager(this));
        mUsersListView.setAdapter(mUserRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        usersList.clear();

        mFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(documentSnapshots == null){
                    return;
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String user_id = doc.getDocument().getId();

                        Users users = doc.getDocument().toObject(Users.class).withId(user_id);
                        usersList.add(users);
                        mUserRecyclerAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }*/
}
