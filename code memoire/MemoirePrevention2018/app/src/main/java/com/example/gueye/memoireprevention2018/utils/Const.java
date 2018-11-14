package com.example.gueye.memoireprevention2018.utils;

//cette classe va contenir toutes nos constantes


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gueye.memoireprevention2018.LoginActivity;
import com.example.gueye.memoireprevention2018.R;
import com.mikepenz.materialize.util.UIUtils;

public class Const {

    public static final int[] DEFAULT_RESOURCE_ICONES = {

            R.drawable.icon_aggression_couteau,
            R.drawable.icon_vol_cambriolage,
            R.drawable.icon_viol,
            R.drawable.icon_panne_de_null_part,
            R.drawable.icon_car_accident,
            R.drawable.icon_besoin_medcin,
            R.drawable.autre_image
    };

    public static final int[] DEFAULT_RESOURCE_IMAGES = {

            R.drawable.agresion_couteau,
            R.drawable.vol_image,
            R.drawable.viol,
            R.drawable.panne_de_null_part,
            R.drawable.accident_image,
            R.drawable.besoin_medcin_image,
            R.drawable.autre_image
    };

    public static  final String [] DEFAULT_TYPES = {
            "Agression avec arme",
            "Vol",
            "Viol",
            "Panne de null part",
            "Accident",
            "Besoin d'un medcin",
            "Autre"
    };
    public static final float DEFAULT_ZOOM_MAP = 15f;
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final int RESQUEST_CODE_PLACE_PICKER =2 ;

    public static class Notification{
        public static final int LARGE_ICONE_SIZE = 256; //px
    }


    public static final String AVATAR = "https://www.gravatar.com/avatar/00000000000000000000000000000000?d=https%3A%2F%2Fexample.com%2Fimages%2Favatar.jpg";


    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String [] PERMISSIONS = {FINE_LOCATION,COARSE_LOCATION};




    // Time ago

    public static String getTimeAgo(long time, Context ctx) {

         final int SECOND_MILLIS = 1000;
         final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
         final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
         final int DAY_MILLIS = 24 * HOUR_MILLIS;

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

}
