package com.utradia.catalogueappv2.module.account

import android.app.Activity
import com.utradia.catalogueappv2.api.RestService
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.mvpbase.BasePresenter
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.PreferenceManager
import okhttp3.MediaType
import okhttp3.RequestBody
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class MyAccountPresenter @Inject constructor() : BasePresenter<MyAccountView>() {

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)

    }


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


    internal fun uploadImage(image:String) {
        val imageToUpload = RequestBody.create(MediaType.parse("image/*"), File(image))
        val userId = RequestBody.create(MediaType.parse("text/plain"), mPrefs.userId)

        mvpView?.showProgress()
        mSubscription = mRestService.uploadImage(imageToUpload,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ SucessResponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (SucessResponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onImageUpload(SucessResponse.success_message,SucessResponse.image)
                        } else if (SucessResponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onErrorOccur(SucessResponse.error_type)
                        }
                    }
                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }

    fun logout(userId: String?) {
        mvpView?.showProgress()
        mSubscription = mRestService.logout(userId!!).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ SucessResponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                if (SucessResponse.success == ApiConstants.STATUS_SUCCESS_1) {
                    mvpView?.onLogoutResponse(SucessResponse.success_message)
                } else if (SucessResponse.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onErrorOccur(SucessResponse.error_message)
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