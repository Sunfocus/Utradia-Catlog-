package com.utradia.catalogueappv2.module.cart

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

class CartPresenter @Inject constructor() : BasePresenter<CartView>() {


    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
        this.activity = context
    }


    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    lateinit var mDialogUtils: DialogsUtil

    @Inject
    lateinit var mRestService: RestService

    private var mSubscription: Subscription? = null

    internal fun cancelAllAsync() {
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }

    lateinit var activity: Activity


    internal fun getCartList(user_id: String) {
        mvpView?.showProgress()

        mSubscription = mRestService.getCartItems(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onCartItemsFound(response)
                        } else if (response.success == ApiConstants.STATUS_SUCCESS_0) {
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

    internal fun removeItem(id: String, user_id: String) {
        mvpView?.showProgress()
        mSubscription = mRestService.removeItem(id, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {
                            mPrefs.cartCount = response.count
                            mvpView?.onItemRemoved()
                        } else if (response.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onCartItemsError(response.error_message)
                        }
                    }

                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)
                    }
                })
    }

    internal fun updateQantity(id: String, quantity: String) {
        mvpView?.showProgress()

        mSubscription = mRestService.updateQuantity(id, quantity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onQuantityUpdated()
                        } else if (response.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onCartItemsError(response.error_message)
                        }
                    }

                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)
                    }
                })

    }

    internal fun moveToWishList(id: String, user_id: String) {
        mvpView?.showProgress()

        mSubscription = mRestService.moveToWishList(id, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            if (!mPrefs.cartCount.isNullOrEmpty())
                                mPrefs.cartCount = (Integer.parseInt(mPrefs.cartCount) - 1).toString()

                            mvpView?.onMovedToWishList(response.success_message)
                        } else if (response.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onCartItemsError(response.error_message)
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