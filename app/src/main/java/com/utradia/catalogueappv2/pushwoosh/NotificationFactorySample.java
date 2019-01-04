package com.utradia.catalogueappv2.pushwoosh;

import android.app.Notification;
import android.graphics.Bitmap;

import android.util.Log;

import com.pushwoosh.notification.PushMessage;
import com.pushwoosh.notification.PushwooshNotificationFactory;

import androidx.annotation.NonNull;

public class NotificationFactorySample extends PushwooshNotificationFactory {
	@Override
	public Notification onGenerateNotification(@NonNull PushMessage pushMessage) {
		//Log.d(PushwooshSampleApp.LTAG, "onGenerateNotification: " + pushMessage.toJson().toString());

		Notification notification = super.onGenerateNotification(pushMessage);
		// TODO: customise notification content

		return notification;
	}

	@Override
	protected Bitmap getLargeIcon(PushMessage pushMessage) {


		Log.e("sas","asas");

		return super.getLargeIcon(pushMessage);
	}
}
