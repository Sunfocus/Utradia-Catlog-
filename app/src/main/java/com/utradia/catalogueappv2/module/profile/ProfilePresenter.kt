package com.utradia.catalogueappv2.module.profile

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

class ProfilePresenter @Inject constructor() :BasePresenter<ProfileView>(){

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

    internal fun getProfile(user_id:String) {
        mvpView?.showProgress()
        mSubscription = mRestService.getProfile(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                      mvpView?.onUserDetailsFound(CheckNumberReponse)
                } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onUserDetailsFailed(CheckNumberReponse.error_message)
                }
            }
        }, { throwable ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.onError(throwable)

            }
        })
    }


    internal fun updateUser(user_id: String,first_name: String,last_name: String
                            ,phone_number : String, email: String, dob: String , gender: String) {
        mvpView?.showProgress()

        mRestService.updateUser(user_id,first_name,last_name,phone_number,email,dob,gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onUserUpdated(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onUpdationFailed(response.error_message)
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