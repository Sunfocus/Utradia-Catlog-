package com.utradia.catalogueappv2.module.allcategories

import android.app.Activity
import com.utradia.catalogueappv2.api.RestService
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.mvpbase.BasePresenter
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class AllCategoriesPresenter @Inject constructor():BasePresenter<AllCategoriesView>() {

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }


    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    lateinit var mDialogUtils: DialogsUtil

    @Inject
    lateinit var mRestService: RestService

    private  var mSubscription:Subscription?=null

    internal fun cancelAllAsync() {
        if ( mSubscription !=null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }


    internal fun getTwoLevelCategories() {
         mvpView?.showProgress()

        mSubscription = mRestService.getTwoLevelCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                          mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onEventDetailFound(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onEventDetailNotFound(response.error_message)
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