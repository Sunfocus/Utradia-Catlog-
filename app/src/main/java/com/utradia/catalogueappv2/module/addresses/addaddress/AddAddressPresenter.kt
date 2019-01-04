package com.utradia.catalogueappv2.module.addresses.addaddress

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
import rx.schedulers.Schedulers.*
import javax.inject.Inject

class AddAddressPresenter  @Inject constructor(): BasePresenter<AddAddressView>() {

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


    internal fun getRegions() {
        mvpView?.showProgress()
        mSubscription = mRestService.getRegions()
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onRegionsFound(CheckNumberReponse)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onRegionsNotFound(CheckNumberReponse.error_message)
                        }
                    }
                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }

    internal fun getCities(region_id:String,city:String,city_id:String) {
        mvpView?.showProgress()
        mSubscription = mRestService.getCities(region_id)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onCitiesFound(CheckNumberReponse,city,city_id)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onRegionsNotFound(CheckNumberReponse.error_message)
                        }
                    }
                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }

    //user_id, full_name, mobile_number, pincode, houseno, locality, city, state, type('billing', 'shipping')
    internal fun addAddress(user_id :String, full_name :String
                            , mobile_number :String
                            , houseno :String, locality :String
                            , city :String, state :String, type :String) {
        mvpView?.showProgress()
        mSubscription = mRestService.addAddress(user_id, full_name, mobile_number, houseno, locality, city, state, type)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onAddressAdded(CheckNumberReponse.success_message)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onRegionsNotFound(CheckNumberReponse.error_message)
                        }
                    }
                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }

    //user_id, full_name, mobile_number, houseno, locality, city, state, type('billing', 'shipping')
    internal fun updateAddress(id :String, full_name :String
                            , mobile_number :String
                            , houseno :String, locality :String
                            , city :String, state :String, type :String) {
        mvpView?.showProgress()
        mSubscription = mRestService.updateAddress(id, full_name, mobile_number, houseno, locality, city, state, type)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onAddressAdded(CheckNumberReponse.success_message)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onRegionsNotFound(CheckNumberReponse.error_message)
                        }
                    }
                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }

    //user_id, full_name, mobile_number, pincode, houseno, locality, city, state, type('billing', 'shipping')
    internal fun getAddress(id:String) {
        mvpView?.showProgress()
        mSubscription = mRestService.getAddressDetails(id)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onAddressDetailFound(CheckNumberReponse)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onRegionsNotFound(CheckNumberReponse.error_message)
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