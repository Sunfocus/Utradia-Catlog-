package com.utradia.catalogueappv2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.utradia.catalogueappv2.constants.PreferenceConstants;


/**
 * Contains method to store and retrieve SharedPreferences data
 */
public class PreferenceManager {

    private Context mContext;

    public PreferenceManager(Context context) {
        this.mContext = context;
    }


    //clear user shared preferences
    public void clearPrefrences() {
        getPreferences().edit().clear().apply();
    }


    //get shared pref
    private SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(PreferenceConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }
    //get shared pref
    private SharedPreferences getOtherPreferences() {
        return mContext.getSharedPreferences(PreferenceConstants.PREFERENCE_NAME_2, Context.MODE_PRIVATE);
    }

    //save data of current logged in user
    public void setDeviceToken(String device_token) {
        getPreferences().edit().putString(PreferenceConstants.DEVICE_TOKEN, device_token).apply();
    }

    //get user data as string
    public String getDeviceToken() {
        return getPreferences().getString(PreferenceConstants.DEVICE_TOKEN, "");
    }

    //save CartCount
    public void setCartCount(String count) {
        getPreferences().edit().putString(PreferenceConstants.CART_COUNT, count).apply();
    }

    //get cart Count
    public String getCartCount() {
        return getPreferences().getString(PreferenceConstants.CART_COUNT, "");
    }

    //get ref data as string
    public String getProfilePic() {
        return getPreferences().getString(PreferenceConstants.PROFILE_PIC, "");
    }

    //save data of current logged in user
    public void setProfilePic(String ref_id) {
        getPreferences().edit().putString(PreferenceConstants.PROFILE_PIC, ref_id).apply();
    }

    // Set User Id
    public void setUserID(String userID) {
        getPreferences().edit().putString(PreferenceConstants.USER_ID, userID).apply();
    }
    //Get user id
    public String getUserId() {
        return getPreferences().getString(PreferenceConstants.USER_ID, "");
    }


    // Set NAME
    public void setName(String name) {
        getPreferences().edit().putString(PreferenceConstants.NAME, name).apply();
    }
    //Get NAME
    public String getName() {
        return getPreferences().getString(PreferenceConstants.NAME, "");
    }


    // Set Email
    public void setEmail(String email) {
        getPreferences().edit().putString(PreferenceConstants.EMAIL, email).apply();
    }
    //Get Email
    public String getEmail() {
        return getPreferences().getString(PreferenceConstants.EMAIL, "");
    }


    //save data of current logged in user
    public void setUserLoggedIn(boolean loggedin) {
        getPreferences().edit().putBoolean(PreferenceConstants.IS_USER_LOGGED_IN, loggedin).apply();
    }

    //get user data as string
    public boolean isUserLoggedIn() {
        return getPreferences().getBoolean(PreferenceConstants.IS_USER_LOGGED_IN, false);
    }


    //save data of default user token
    public void setDeviceRegisterIn(boolean loggedin) {
        getPreferences().edit().putBoolean(PreferenceConstants.IS_DEVICE_REGISTERED, loggedin).apply();
    }

    //get default user token as string
    public boolean isDeviceRegisterIn() {
        return getPreferences().getBoolean(PreferenceConstants.IS_DEVICE_REGISTERED, false);
    }

    //save data of current logged in user
    public void setCurrencySymbol(String currency) {
        getPreferences().edit().putString(PreferenceConstants.CURRENCY_SYMBOL, currency).apply();
    }

    //get user data as string
    public String getCurrencySymbol() {
        return getPreferences().getString(PreferenceConstants.CURRENCY_SYMBOL, "₵");
    }

}