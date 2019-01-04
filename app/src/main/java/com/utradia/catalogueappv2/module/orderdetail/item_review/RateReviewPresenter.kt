package com.utradia.catalogueappv2.module.orderdetail.item_review

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

class RateReviewPresenter @Inject constructor() : BasePresenter<RateReviewView>() {

    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mPrefs: PreferenceManager


    @Inject
    lateinit var mRestService: RestService

    private var mSubscription: Subscription? = null

    internal fun cancelAllAsync() {
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }


    //user_id, product_id, order_item_id, rating, review
    internal fun addProductReview(user_id:String,product_id:String,order_item_id:String,rating:String,review:String) {
        mvpView?.showProgress()

        mSubscription= mRestService.addProductReview(user_id,product_id,order_item_id,rating, review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onReviewAdded(response.success_message)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onErrorOccur(response.success_message)
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