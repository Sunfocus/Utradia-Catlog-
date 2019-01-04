package com.utradia.catalogueappv2.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.utradia.catalogueappv2.R;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * <p/>
 * Contains commonly used methods in an Android App
 */
public class AppUtils {

    private Context mContext;

    public AppUtils(Context context) {
        this.mContext = context;
    }

    /**
     * Description : Check if user is online or not
     *
     * @return true if online else false
     */
    public boolean isOnline(View v) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        showSnackBar(v, mContext.getString(R.string.toast_network_not_available));
        return false;
    }

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            EMAIL_PATTERN
    );

    public  boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email.trim()).matches();
    }
    /**
     * Description : Check if user is online or not
     *
     * @return true if online else false
     */
    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public  boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * Description : Hide the soft keyboard
     *
     * @param view : Pass the current view
     */
    public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public  void hideSoftKeyboardOut(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
/*
*getting name from int
* */
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
    /**
     * Show snackbar
     *
     * @param view view clicked
     * @param text text to be displayed on snackbar
     */
    public void showSnackBar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }



    /**
     * Show snackbar
     *
     * @param text text to be displayed on Toast
     */
    public void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }


    public void showSuccessToast(String text) {
        Toasty.success(mContext, text, Toast.LENGTH_SHORT, true).show();
    }
    public void showErrorToast(String text) {
        Toasty.error(mContext, text, Toast.LENGTH_LONG, true).show();
    }
    /**
     * send email via. intent
     *
     * @param email to whom you want to send email
     */
    public void sendEmail(Context context, String email) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, email);
        try {
            context.startActivity(Intent.createChooser(i, context.getResources().getString(R.string.send_mail)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, context.getResources().getString(R.string.no_email_client), Toast.LENGTH_LONG).show();
        }
    }
    /**
     * show related error message to user on api failure
     */
    public void showErrorMessage(View view, Throwable t) {
     //   showSnackBar(view, getErrorMessage(t));
        Toasty.error(mContext,getErrorMessage(t), Toast.LENGTH_LONG, true).show();
    }
    //return error message from webservice error code
    private String getErrorMessage(Throwable throwable) {
        String errorMessage;
        if (throwable instanceof HttpException || throwable instanceof UnknownHostException
                || throwable instanceof ConnectException) {
            errorMessage = "Something went wrong";
        } else {
            errorMessage = "Unfortunately an error has occurred!";
        }
        return errorMessage;
    }
    /**
     * To open a website in phone browser
     *
     * @param address valid email link
     */
    public void openBrowser(String address) {
        try {
            if (!address.startsWith("http://") && !address.startsWith("https://")) {
                address = "http://" + address;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
            mContext.startActivity(browserIntent);
        } catch (Exception e) {
            showToast(mContext.getResources().getString(R.string.warning_invalid_link));
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(mContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog((Activity) mContext, resultCode, 9000)
                        .show();
            } else {
                showToast(mContext.getResources().getString(R.string.warning_play_services));
            }
            return false;
        }
        return true;
    }

    /**
     * redirect user to your application settings in device
     */
    public void redirectToAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        mContext.startActivity(intent);
    }

    /**
     * check if user has enabled Gps of device
     *
     * @return true or false depending upon device Gps status
     */
    public boolean isGpsEnabled() {
        final LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Redirect user to enable GPS
     */
    public void goToGpsSettings() {
        Intent callGPSSettingIntent = new Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mContext.startActivity(callGPSSettingIntent);
    }
    /**
     * check if user has permissions for the asked permissions
     */
    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    //enable / disable views using Butterknife
    public final ButterKnife.Setter<View, Boolean> ENABLED = (view, value, index) -> view.setEnabled(value);




    public RequestBody getRequestBody(String data)
    {
        return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"),data);
    }

    /*get mnth param*/
    /*
    Split text from string
     */
    public String getMeNthParamInString(String p_text, String p_seperator, int nThParam) { // / "TOTRPIDS=101=104" returns
        // "101" If nThParam ==
        // 2.
        String retStrThirdParam = new String("");
        int index = -1;
        int prevIdx = 0;
        int loopNM = 1;
        boolean loopBool = true;
        while (loopBool) {
            try {
                index = p_text.indexOf(p_seperator, prevIdx);
                if (loopNM >= nThParam) {
                    if (index >= 0) {
                        retStrThirdParam = p_text.substring(prevIdx, index);
                    } else // /-1
                    {
                        retStrThirdParam = p_text.substring(prevIdx);
                    }
                    loopBool = false;
                    break;
                } else {
                    if (index < 0) // /-1
                    {
                        loopBool = false;
                        retStrThirdParam = "";
                        break;
                    }
                }
                loopNM++;
                prevIdx = index + 1;
            } catch (Exception ex) {
                loopBool = false;
                retStrThirdParam = "";
                break;
            }
        } // /while
        if (retStrThirdParam.trim().length() <= 0) {
            retStrThirdParam = "";
        }
        return retStrThirdParam;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public void copyToClipboard(Context context,String textToCopy,String toast){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", textToCopy);
        clipboard.setPrimaryClip(clip);
        showToast(toast);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeStatusBarColor(Activity activity) {
        Window window = activity.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeStatusBarTranparent(Activity activity) {
        Window window = activity.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.transparent));
    }
    public String formatPrice(String price){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return  formatter.format(Double.valueOf(price));
    }

    public  void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);

        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
}