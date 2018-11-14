package com.example.gueye.memoireprevention2018.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gueye.memoireprevention2018.MainActivity;
import com.example.gueye.memoireprevention2018.utils.Const;
import com.example.gueye.memoireprevention2018.utils.ImageUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.example.gueye.memoireprevention2018.R;
import com.example.gueye.memoireprevention2018.activities.SendAlerteActivity;

/**
 * Created by gueye on 15/09/18.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification());
    }

    private void showNotification(RemoteMessage.Notification notification) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this,getString( R.string.default_notification_channel_id))
                .setSmallIcon( R.drawable.geo)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                //.setLargeIcon( BitmapFactory.decodeResource(getResources(), R.drawable.ic_help))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());


    }

   /* @Nullable
    public Bitmap getBitmapFromUrl(String imageUrl) {
        return ImageUtil.loadBitmap(GlideApp.with(this), imageUrl, Const.LARGE_ICONE_SIZE, Const.LARGE_ICONE_SIZE);
    }*/



}