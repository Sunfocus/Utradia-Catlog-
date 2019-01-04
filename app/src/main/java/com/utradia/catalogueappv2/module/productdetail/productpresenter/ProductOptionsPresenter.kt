package com.utradia.catalogueappv2.module.productdetail.productpresenter

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

class ProductOptionsPresenter @Inject constructor():BasePresenter<ProductOptionsView>(){
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


    internal fun addToCart(user_id : String,product_id:String,qty:String, variant_group_type:String,variant_group_id: String
                           ,color:String,size:String,price:String) {
        mvpView?.showProgress()
        mSubscription= mRestService.addToCart(user_id,product_id,qty,variant_group_type,variant_group_id,color,size,price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onAddedToCart(CheckNumberReponse)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onCartError(CheckNumberReponse.error_message)
                        }
                    }}, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }


    internal fun buyNowCart(user_id : String,product_id:String,qty:String, variant_group_type:String,variant_group_id: String
                            ,color:String,size:String,price:String) {
        mvpView?.showProgress()
        mSubscription= mRestService.buyNowCart(user_id,product_id,qty,variant_group_type,variant_group_id,color,size,price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onBuyNowCart(CheckNumberReponse)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onCartError(CheckNumberReponse.error_message)
                        }
                    }}, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }
}