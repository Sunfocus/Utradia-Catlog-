package com.utradia.catalogueappv2.module.subcategory

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

class SubCategoryPresenter @Inject constructor():BasePresenter<SubCategoryView>() {
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


    internal fun getSubCategories(parent_id:String) {
        mvpView?.showProgress()
        mSubscription = mRestService.getSubCategories(parent_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                    mvpView?.onSubCategoryFound(CheckNumberReponse)
                } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onSubCategoryNotFound(CheckNumberReponse.error_message)
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