package com.utradia.catalogueappv2.module.search

import android.app.Activity
import com.utradia.catalogueappv2.api.RestService
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.module.addresses.addaddress.AddAddressView
import com.utradia.catalogueappv2.mvpbase.BasePresenter
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class SearchProductPresenter  @Inject constructor(): BasePresenter<SearchProductView>(){

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
        this.activity = context
    }

    @Inject
    lateinit var mDialogUtils: DialogsUtil

    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mPrefs: PreferenceManager
    lateinit var activity: Activity

    @Inject
    lateinit var mRestService: RestService

    private var mSubscription: Subscription? = null

    internal fun cancelAllAsync() {
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }


    internal fun getSearchProduct(keyword:String) {
        //mvpView?.showProgress()
        mSubscription = mRestService.getSearchProduct(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ SucessResponse ->
                    if (isViewAttached) {
                      //  mvpView?.hideProgress()
                        if (SucessResponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView!!.productList(SucessResponse.offers)
                        } else if (SucessResponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView!!.onError(SucessResponse.error_message)
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