package com.utradia.catalogueappv2.module.loginsignup.login

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

class LoginPresenter @Inject
constructor() : BasePresenter<LoginView>() {
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


    internal fun loginUserSocially(signup_type: String, fb_id: String, google_id: String, email: String, first_name: String, device_token: String, device_type: String) {
        mvpView?.showProgress()

      mSubscription=  mRestService.loginUserSocially(signup_type, fb_id, google_id,email,first_name,device_token,device_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ CheckNumberReponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.enableButton()
                if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {

                    mvpView?.onSocialUserAlreadyExist(CheckNumberReponse)
                } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {

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

    internal fun loginUser(email: String, password: String, device_token: String, device_type: String , login_type: String) {
        mvpView?.showProgress()

       mSubscription= mRestService.loginUser(email, password, device_token,device_type,login_type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.enableButton()
                if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                    mvpView?.onUserLoggedIn(response)
                } else if (response.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onLoginFailed(response)
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


    internal fun updateEmail(email: String, user_id: String ) {
        mvpView?.showProgress()

       mSubscription= mRestService.updateEmail(email, user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.enableButton()
                if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                    mvpView?.onUserLoggedIn(response)
                } else if (response.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onLoginFailed(response)
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
