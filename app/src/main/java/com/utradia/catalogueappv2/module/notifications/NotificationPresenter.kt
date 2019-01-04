package com.utradia.catalogueappv2.module.notifications

import android.app.Activity
import com.utradia.catalogueappv2.api.RestService
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.model.NotificationResponse
import com.utradia.catalogueappv2.mvpbase.BasePresenter
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.PreferenceManager
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class NotificationPresenter @Inject constructor():BasePresenter<NotificationView>() {

    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mPrefs: PreferenceManager


    @Inject
    lateinit var mRestService: RestService

    private  var mSubscription: Subscription?=null

    internal fun cancelAllAsync() {
        if ( mSubscription !=null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)

    }



    fun notificationList() {
        mvpView?.showProgress()
        mSubscription = mRestService.notificationList(mPrefs.userId!!).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ NotificationResponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                if (NotificationResponse.success == ApiConstants.STATUS_SUCCESS_1) {
                    mvpView?.onNotificationResponse(NotificationResponse.data)
                } else if (NotificationResponse.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onErrorOccur(NotificationResponse.error_message)
                }
            }
        }, { throwable ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.onError(throwable)

            }
        })
    }


}