/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.utradia.catalogueappv2.notification;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.pushwoosh.PushwooshFcmHelper;
import com.utradia.catalogueappv2.base.UtradiaApplication;
import com.utradia.catalogueappv2.utils.PreferenceManager;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * To get device token from FireBase server for the purpose of push notifications
 */

public class SampleAppFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Inject
    PreferenceManager mPrefs;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        ((UtradiaApplication) getApplication()).getAppComponent().inject(this);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        PushwooshFcmHelper.onTokenRefresh(this, refreshedToken);
        Timber.e("Refreshed token: " + refreshedToken);
        try {
            mPrefs.setDeviceToken(refreshedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // [END refresh_token]
}