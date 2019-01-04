package com.utradia.catalogueappv2.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pushwoosh.PushwooshFcmHelper;
import com.utradia.catalogueappv2.R;
import com.utradia.catalogueappv2.constants.ValueConstants;
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity;

import timber.log.Timber;

/**
 * To display push notifications to user
 */
public class PushMessagingService extends FirebaseMessagingService {
    private static final String TAG = PushMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Timber.e(TAG, "FCM Data Message: " + remoteMessage.getData());


        if (PushwooshFcmHelper.isPushwooshMessage(remoteMessage)) {
            //this is a Pushwoosh push, SDK will handle it automatically
            PushwooshFcmHelper.onMessageReceived(this, remoteMessage);
        } else {
            //this is not a Pushwoosh push, you should handle it by yourself
          //  sendNotification(remoteMessage);
        }


    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(RemoteMessage messageBody) {
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

        String message = messageBody.getData().get("a");
       // String zzahb=messageBody.getData().get("a");
        //redirect to desired activity

        Intent intent = LoginActivity.Companion.createIntent(this, ValueConstants.DEFAULT,ValueConstants.UNHIDE_SKIP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, iUniqueId, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(iUniqueId, notificationBuilder.build());
    }

}
