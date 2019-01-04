package com.utradia.catalogueappv2.module.payment

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

class PaymentPresenter @Inject constructor():BasePresenter<PaymentView>() {

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

    internal fun placeOrder(user_id: String, product_id: String, shipment_charges: String
                            , shipment_id: String, billing_address_id: String
                            , shipping_type: String, price: String, total_shipping_charges: String
                            , total_price: String, payment_mode: String, payment_token: String
                            , payment_common_id: String, promocode_id: String, discount: String
                            , discount_type: String, payment_method: String,order_type:String) {
        mvpView?.showProgress()

        mSubscription= mRestService.placeOrder(user_id, product_id, shipment_charges
                , shipment_id, billing_address_id, shipping_type, price, total_shipping_charges
                , total_price, payment_mode, payment_token, payment_common_id
                , promocode_id, discount, discount_type, payment_method,order_type,"Processing")

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onOrderPlaced(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onOrderNotPlaced(response.error_message)
                        }
                    }

                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)
                    }
                })

    }

    internal fun orderPaymnet(mOrderId: String,mPaymentMode:String,mPaymentMethod:String, mPaymentStatus: String) {
        mvpView?.showProgress()

        val hashMap=HashMap<String,String>()

        hashMap["order_id"]=mOrderId
        hashMap["payment_status"]=mPaymentStatus
        hashMap["user_id"]=mPrefs.userId
        hashMap["payment_mode"]=mPaymentMode
        hashMap["payment_method"]=mPaymentMethod

/*        Required: payment_mode, payment_method, order_id, payment_status('Completed', 'pending')
        Optional: payment_token, payment_common_id*/


        mSubscription= mRestService.orderPayment(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onSuccess(response.success_message)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onOrderNotPlaced(response.error_message)
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