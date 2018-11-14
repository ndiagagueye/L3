package com.example.gueye.memoireprevention2018.modele;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

/**
 * Created by gueye on 21/09/18.
 */

class UsersId {

    @Exclude
    public String usersId;

    public <T extends UsersId> T withId(@NonNull final String id) {
        this.usersId = id;
        return (T) this;
    }
}
