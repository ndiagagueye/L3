package com.example.gueye.memoireprevention2018.modele;

/**
 * Created by gueye on 04/10/18.
 */

public class Benevol extends Users {

    public String image, name, telephone,token_id, user_id, status;
    private int profession ;

    public  Benevol(int profession){

        super();

        this.profession = profession;
    }
    public  Benevol(){}

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }
}
