package com.example.gueye.memoireprevention2018.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gueye.memoireprevention2018.R;
import com.example.gueye.memoireprevention2018.adaptaters.AlerteRecyclerAdapter;
import com.example.gueye.memoireprevention2018.adaptaters.MembreAdapter;
import com.example.gueye.memoireprevention2018.modele.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MembresFragment extends Fragment {

    private RecyclerView recyclerView;
    private MembreAdapter membreAdapter;
    private List<Users> mUsers;
    private FirebaseFirestore mFirestore;


    public MembresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_membres, container, false );

        mFirestore = FirebaseFirestore.getInstance();
        mUsers = new ArrayList<>();

        recyclerView = view.findViewById(R.id.membres_recycler_view);
        recyclerView.setHasFixedSize( true );

        mFirestore = FirebaseFirestore.getInstance();
        membreAdapter = new MembreAdapter(mUsers, container.getContext(), false);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(membreAdapter);

        readUsres();

        return view ;
    }

    private void readUsres() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                //mUsers.clear();
                if(documentSnapshots == null){
                    return;
                }
                if (!documentSnapshots.isEmpty()) {
                    mUsers.clear();
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String user_id = doc.getDocument().getId();

                            Users users = doc.getDocument().toObject( Users.class ).withId( user_id );
                            assert users != null ;
                            assert firebaseUser != null ;
                            if(users.getUser_id() != null) {
                                if (!users.getUser_id().equals( firebaseUser.getUid() )) {

                                    mUsers.add( users );
                                    membreAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    }

                }else Toast.makeText(getContext(), "List vide", Toast.LENGTH_SHORT ).show();

            }


        });
    }

}
