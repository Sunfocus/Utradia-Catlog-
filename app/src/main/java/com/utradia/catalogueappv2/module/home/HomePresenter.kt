package com.utradia.catalogueappv2.module.home

import android.app.Activity
import com.utradia.catalogueappv2.api.RestService
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.mvpbase.BasePresenter
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.PreferenceManager
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject
constructor() :BasePresenter<HomeView>() {


    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mPrefs: PreferenceManager


    @Inject
    lateinit var mRestService: RestService

    private  var mSubscription:Subscription?=null

    internal fun cancelAllAsync() {
        if ( mSubscription !=null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }
    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)

    }

    internal fun getHomeData(user_id:String) {
        mvpView?.showProgress()
        mSubscription = mRestService.getHomeData(user_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                    mvpView?.onHomeDataReceived(CheckNumberReponse)
                } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onHomeDataFailed(CheckNumberReponse.error_message)
                }
            }
        }, { throwable ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.onError(throwable)

            }
        })
    }


    internal fun registerToken(deviceToken:String) {
        mvpView?.showProgress()
        mSubscription = mRestService.addDevice(deviceToken,"android").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ SucessResponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                if (SucessResponse.success == ApiConstants.STATUS_SUCCESS_1) {
                    mvpView?.onDeviceRegistered()
                } else if (SucessResponse.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onHomeDataFailed(SucessResponse.error_message)
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