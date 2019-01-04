package com.utradia.catalogueappv2.pushwoosh;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.pushwoosh.notification.NotificationServiceExtension;
import com.pushwoosh.notification.PushMessage;
import com.utradia.catalogueappv2.R;
import com.utradia.catalogueappv2.module.deeplink.DeepLinkActivity;
import com.utradia.catalogueappv2.module.home.HomeActivity;
import com.utradia.catalogueappv2.module.orderdetail.OrderDetailActivty;
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity;
import com.utradia.catalogueappv2.module.splash.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.MainThread;
import androidx.core.app.NotificationCompat;

public class NotificationServiceSample extends NotificationServiceExtension {
	@Override
	public boolean onMessageReceived(final PushMessage message) {
		//Log.d(PushwooshSampleApp.LTAG, "NotificationService.onMessageReceived: " + message.toJson().toString());



		// automatic foreground push handling
	/*	if (isAppOnForeground()) {*/
			Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
			mainHandler.post(() -> handlePush(message));

			// this indicates that notification should not be displayed
		/*	return true;
		}*/

		return true;
	}

	@Override
	protected void startActivityForPushMessage(PushMessage message) {
		super.startActivityForPushMessage(message);

		// TODO: start custom activity if necessary

	//	handlePush(message);
	}

	@MainThread
	private void handlePush(PushMessage message) {
		try {
			sendNotification(message.toJson());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}




    private void sendNotification(JSONObject data) throws JSONException {

		int messageCount=0;

		final Bitmap[] imageBitmap = {null};

		String message = "", title = "",type="",image_url="",deeplink_url="",id="";
		JSONObject userData=null;



		try {
			 userData=data.getJSONObject("userdata");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if(userData!=null)
		{
			if (userData.has("message")) {
				message = userData.getString("message");
			}

			if (userData.has("type")) {
				type = userData.getString("type");
			}

			if (userData.has("title")) {
				title = userData.getString("title");
			}

			if (userData.has("image_url")) {
				image_url = userData.getString("image_url");
			}

			if (userData.has("deeplink")) {
				deeplink_url = userData.getString("deeplink");
			}

			if(userData.has("id"))
			{
				id=userData.getString("id");
			}


			Glide.with(getApplicationContext())
					.asBitmap()
					.load(image_url)
					.into(new SimpleTarget<Bitmap>() {
						@Override
						public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

							imageBitmap[0] =resource;
						}
					});
		}






		Intent mainIntent = new Intent(getApplicationContext(), HomeActivity.class);
		mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent;

		switch (type) {
			case "AdminNotification":
				Intent intent = new Intent(getApplicationContext(), DeepLinkActivity.class);

				if(!deeplink_url.isEmpty())
					intent.putExtra("branch",deeplink_url);

				pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
						intent, PendingIntent.FLAG_ONE_SHOT);

				intent.putExtra("branch_force_new_session", true);

				break;

			case "orderNotification":
				intent = new Intent(getApplicationContext(), OrderDetailActivty.class);
				intent.putExtra("orderId",id);
				pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
						intent, PendingIntent.FLAG_ONE_SHOT);
				break;

			case "ratingNotification":
				intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
				intent.putExtra("product_id_extra",id);
				intent.putExtra("product_name_extra",title);
				intent.putExtra("review_extra",true);
				pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
						intent, PendingIntent.FLAG_ONE_SHOT);
				break;

			default:
				pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, mainIntent,
						PendingIntent.FLAG_ONE_SHOT);
				break;

		}



		String channelId = getApplicationContext().getString(R.string.default_notification_channel_id);
		Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(getApplicationContext(), channelId)
						.setSmallIcon(getNotificationIcon())
						.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.app_icon))
						.setContentTitle(title)
						.setContentText(message)
						.setAutoCancel(true)
						.setSound(defaultSoundUri)
						.setStyle(new NotificationCompat.BigTextStyle()
								.bigText(message))
						.setContentIntent(pendingIntent);

		if(imageBitmap[0]!=null)
		{
			notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
				.bigPicture(imageBitmap[0]));
		}

		NotificationManager notificationManager =
				(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

		// Since android Oreo notification channel is needed.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(channelId,
					getApplicationContext().getString(R.string.app_name),
					NotificationManager.IMPORTANCE_HIGH);
			channel.enableLights(true);
			// Sets the notification light color for notifications posted to this
			// channel, if the device supports this feature.
			channel.setLightColor(Color.BLACK);

			channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

			channel.enableVibration(true);
			channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
			notificationManager.createNotificationChannel(channel);
		}
			notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

	}

	private int getNotificationIcon() {
		boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
		return useWhiteIcon ? R.mipmap.app_icon : R.mipmap.app_icon;
	}
}
