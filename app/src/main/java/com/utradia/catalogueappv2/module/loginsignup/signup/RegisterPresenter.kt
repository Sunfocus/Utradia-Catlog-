package com.utradia.catalogueappv2.module.loginsignup.signup

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

class RegisterPresenter @Inject
constructor() : BasePresenter<RegisterView>() {
    lateinit var mActivity: Activity
    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mPrefs: PreferenceManager


    @Inject
    lateinit var mRestService: RestService
    private  var mSubscription:Subscription?=null

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
        mActivity = context

    }


    internal fun signUpUser( signup_type: String,first_name: String,last_name: String,phone_number : String, email: String, password: String, device_type: String,device_token: String ) {
        mvpView?.showProgress()

       mSubscription= mRestService.signUpUser(signup_type,first_name,last_name,phone_number,email, password,device_type,device_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.enableButton()
                if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                    mvpView?.onUserRegistered(response)
                } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onUserRegisterationFailed(response)
                }
            }

        }, { throwable ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.onError(throwable)
                mvpView?.enableButton()
            }
        })

    }

    internal fun cancelAllAsync() {
        if (mSubscription !=null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }
}