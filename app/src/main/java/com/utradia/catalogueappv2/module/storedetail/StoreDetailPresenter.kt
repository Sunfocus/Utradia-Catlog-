package com.utradia.catalogueappv2.module.storedetail

import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.utradia.catalogueappv2.api.RestService
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.interfaces.OnDialogButtonClickListener
import com.utradia.catalogueappv2.mvpbase.BasePresenter
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class StoreDetailPresenter @Inject constructor():BasePresenter<StoreDetailView>() {

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
        this.activity=context
    }

    @Inject
    lateinit var mDialogUtils: DialogsUtil

    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mPrefs: PreferenceManager
    lateinit var activity:Activity

    @Inject
    lateinit var mRestService: RestService

    private  var mSubscription: Subscription?=null

    internal fun cancelAllAsync() {
        if ( mSubscription !=null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }


    internal fun getClientData(id:String,user_id:String) {
        mvpView?.showProgress()
        mSubscription= mRestService.getClientData(id,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onClientDataFound(CheckNumberReponse)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onClientDataNotFound(CheckNumberReponse.error_message)
                        }
                    }}, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }

    /**
     * Check whether user has give camera and storage permissions*/

    internal fun checkCallPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkMultiplePermissions(ValueConstants.REQUEST_CODE_ASK_CAMERA_MULTIPLE_PERMISSIONS)
        } else {
            mvpView?.callPermissionsGranted()
        }
    }

    /**
     * Check if user has allowed application to use Location Permissions else ask for permissions*/

    @TargetApi(Build.VERSION_CODES.M)
    internal fun checkMultiplePermissions(permissionCode: Int) {
        val PERMISSIONS = arrayOf(ValueConstants.CALL_PERMISSION)
        if (!mAppUtils.hasPermissions(activity, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, permissionCode)
        } else {
            mvpView?.callPermissionsGranted()
        }
    }

    /**
     * Handle result for permission grant or denial*/


    @TargetApi(Build.VERSION_CODES.M)
    internal fun takeActionOnPermissionChanges(grantResults: IntArray, onDialogButtonClickListener: OnDialogButtonClickListener, mRequestPermissions: String, mRequsetSettings: String, mGrantPermissions: String, mCancel: String, mGoToSettings: String) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mvpView?.callPermissionsGranted()
        } else {
            val showRationale1 = activity.shouldShowRequestPermissionRationale(ValueConstants.CALL_PERMISSION)

            if (showRationale1) {
                //explain to user why we need the permissions
                mvpView?.getDialogType(ValueConstants.DIALOG_DENY)
                mDialogUtils.openAlertDialog(activity, mRequestPermissions, mGrantPermissions, mCancel, onDialogButtonClickListener)
            } else {
                //explain to user why we need the permissions and ask him to go to settings to enable it
                mvpView?.getDialogType(ValueConstants.DIALOG_NEVER_ASK)
                mDialogUtils.openAlertDialog(activity, mRequsetSettings, mGoToSettings, mCancel, onDialogButtonClickListener)
            }
        }
    }
}