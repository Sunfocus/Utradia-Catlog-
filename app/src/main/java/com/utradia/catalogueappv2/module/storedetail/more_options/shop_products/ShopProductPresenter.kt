package com.utradia.catalogueappv2.module.storedetail.more_options.shop_products

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

class ShopProductPresenter @Inject constructor(): BasePresenter<ShopProductView>() {

    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mDialogUtils: DialogsUtil
    @Inject
    lateinit var mPrefs: PreferenceManager


    @Inject
    lateinit var mRestService: RestService
    private var mSubscription: Subscription? = null

    internal fun cancelAllAsync() {
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }

    lateinit var activity: Activity

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
        this.activity = context
    }

    internal fun getClientOffers(id:String,cat_id:String,page:String) {
        mvpView?.showProgress()
        mSubscription= mRestService.getClientOffers(id,cat_id,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onProductsFound(CheckNumberReponse)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                           mvpView?.onProductsNotFound(CheckNumberReponse.error_message)
                        }
                    }}, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }

    internal fun followClient(user_id:String,shop_id:String) {
        mvpView?.showProgress()
        mSubscription= mRestService.followClient(user_id,shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onClientFollowed()
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onFollowError(CheckNumberReponse.error_message)
                        }
                    }}, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }

    internal fun unFollowClient(user_id:String,shop_id:String) {
        mvpView?.showProgress()
        mSubscription= mRestService.unFollowClient(user_id,shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onClientUnFollowed()
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onFollowError(CheckNumberReponse.error_message)
                        }
                    }}, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }
}