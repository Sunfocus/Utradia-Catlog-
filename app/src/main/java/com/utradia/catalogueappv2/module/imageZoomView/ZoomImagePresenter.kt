package com.utradia.catalogueappv2.module.imageZoomView

import android.app.Activity
import com.utradia.catalogueappv2.api.RestService
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.model.NotificationResponse
import com.utradia.catalogueappv2.mvpbase.BasePresenter
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.PreferenceManager
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class ZoomImagePresenter @Inject constructor():BasePresenter<ZoomImageView>() {

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



    fun catalogueList(ctalgueId:String) {
        mvpView?.showProgress()
        mSubscription = mRestService.shopCatalogueItem(ctalgueId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ CatalogueItemDetailResponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                if (CatalogueItemDetailResponse.success == ApiConstants.STATUS_SUCCESS_1) {
                    mvpView?.onCtalogueItemResponse(CatalogueItemDetailResponse.catalogue_items)
                } else if (CatalogueItemDetailResponse.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onErrorOccur(CatalogueItemDetailResponse.error_message)
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