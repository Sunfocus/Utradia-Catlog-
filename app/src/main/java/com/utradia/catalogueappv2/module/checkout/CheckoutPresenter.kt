package com.utradia.catalogueappv2.module.checkout

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

class CheckoutPresenter @Inject constructor():BasePresenter<CheckoutView>() {
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

    internal fun getCartList(user_id:String) {
        mvpView?.showProgress()

        mSubscription= mRestService.checkoutData(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onCartItemsFound(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onItemsNotFound(response.success_message)
                        }
                    }

                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)
                    }
                })

    }

    internal fun getShippingCharges(region_id:String,city:String,weight:String,client:String,product_id:String) {
        mvpView?.showProgress()

        mSubscription= mRestService.getShipmentCharges(region_id,city,weight,client,product_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onShipOptionFound(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onItemsNotFound(response.error_message)
                        }
                    }

                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)
                    }
                })

    }

    internal fun applyPromoCode(user_id:String,promo:String
                                ,itemTotal:String,shippingTotal:String) {
        mvpView?.showProgress()

        mSubscription= mRestService.applyPromocode(user_id,promo,itemTotal,shippingTotal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onPromoCodeSuccess(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onPromoCodeError(response.error_message)
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