package com.estrelladelsur.estrelladelsur.navegador.usuario;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estrelladelsur.estrelladelsur.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by LEO on 22/10/2016.
 */
public class GCMIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private static final String TAG = "GcmIntentService";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context context = this;


    private static final int NOTIF_ALERTA_ID = 1;

    public GCMIntentService() {
        super("GCMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        Bundle extras = intent.getExtras();

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) { // has effect of unparcelling Bundle

            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString(),"");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString(),"");
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                // This loop represents the service doing some work.
                for (int i = 0; i < 5; i++) {
                    Log.i(TAG,
                            "Working... " + (i + 1) + "/5 @ "
                                    + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.

                if(!extras.getString("Notice").equals("REG_ID") && !extras.getString("Text").equals("GCM"))
                sendNotification(extras.getString("Notice"),extras.getString("Text"));

                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg, String txt) {

        boolean isNotificacion = true;

        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Uri soundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        Intent notificationIntent = new Intent(getApplicationContext(),
                SplashUsuario.class);

        notificationIntent.putExtra("Notificacion", isNotificacion);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this)

        .setContentTitle(msg)
                .setContentText(txt)
                .setSmallIcon(R.drawable.ic_icon_notif).setTicker(getApplicationContext().getResources().getString(R.string.app_name))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_icon_notification))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(txt))
                .setAutoCancel(true).setSound(soundUri).setContentText(txt);

        mBuilder.setContentIntent(contentIntent);
        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(0, mBuilder.build());

    }

//    private int getNotificationIcon() {
//        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
//        return useWhiteIcon ? R.drawable.icon_silhouette : R.drawable.ic_launcher;
//    }

}
