package com.utradia.catalogueappv2.module.productlist

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

class ProductListPresenter @Inject constructor():BasePresenter<ProductListView>() {

    @Inject
    lateinit var mAppUtils: AppUtils

    @Inject
    lateinit var mPrefs: PreferenceManager

    @Inject
    lateinit var mRestService: RestService

    private  var mSubscription:Subscription?=null

    internal fun cancelAllAsync() {
        if ( mSubscription !=null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
    }

    internal fun injectDependencies(context: Activity) {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)

    }


    internal fun getProducts(cat_id:String,offset : String,hashMap: HashMap<String,String>) {
        mvpView?.showProgress()
        //@Field("category_id") category_id: String, @Field("page_number") page_number: String,@Field("user_id") user_id: String
        hashMap.put("category_id",cat_id)
        hashMap.put("page_number",offset)
        hashMap.put("user_id",mPrefs.userId)


        mSubscription = mRestService.getOffersByCategoryId(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                    mvpView?.onProductsFound(CheckNumberReponse)
                } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                    mvpView?.onProductsNotFound(CheckNumberReponse.error_message)
                }
            }
        }, { throwable ->
            if (isViewAttached) {
                mvpView?.hideProgress()
                mvpView?.onError(throwable)

            }
        })
    }


    internal fun getMostPopularItems(mostpopular_id:String,cat_id : String,page:String,param:HashMap<String,String>) {
        mvpView?.showProgress()

  /*      most_popular_id, category_id, page_number*/

        param["most_popular_id"] = mostpopular_id
        param["category_id"] = cat_id
        param["page_number"] = page
        param["user_id"] = mPrefs.userId

        mSubscription=  mRestService.getMostPopularItems(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onProductsFound(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onProductsNotFound(response.error_message)
                        }
                    }

                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)
                    }
                })

    }



    internal fun getSearchOfferList(clientId:String,page:String,keyword:String,param:HashMap<String,String>) {
        mvpView?.showProgress()
        param["page_number"] = page
        param["user_id"] = mPrefs.userId
        param["keyword"] = keyword
      //  param.put("client_id",clientId)

        mSubscription=  mRestService.searchOfferList(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onProductsFound(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onProductsNotFound(response.error_message)
                        }
                    }

                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)
                    }
                })

    }


    internal fun getSearchOfferId(clientId:String,catId:String,page:String,keyword:String,param:HashMap<String,String>) {
        mvpView?.showProgress()
        param["page_number"] = page
        param["user_id"] = mPrefs.userId
        param["keyword"] = keyword
        param["category_id"] = catId
       // param.put("client_id",clientId)

        mSubscription=  mRestService.searchOfferID(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (response.success == ApiConstants.STATUS_SUCCESS_1) {

                            mvpView?.onProductsFound(response)
                        } else if (response.success== ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onProductsNotFound(response.error_message)
                        }
                    }

                }, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)
                    }
                })

    }


    internal fun addToWishList(product_id:String,user_id : String) {
        mvpView?.showProgress()
        mSubscription= mRestService.addToWishList(user_id,product_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onAddedToWishList(CheckNumberReponse)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onWishListError(CheckNumberReponse.error_message)
                        }
                    }}, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }


    internal fun removeFromWishList(product_id:String,user_id : String) {
        mvpView?.showProgress()
        mSubscription= mRestService.removeFromWishList(user_id,product_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ CheckNumberReponse ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_1) {
                            mvpView?.onRemovedWishList(CheckNumberReponse)
                        } else if (CheckNumberReponse.success == ApiConstants.STATUS_SUCCESS_0) {
                            mvpView?.onWishListError(CheckNumberReponse.error_message)
                        }
                    }}, { throwable ->
                    if (isViewAttached) {
                        mvpView?.hideProgress()
                        mvpView?.onError(throwable)

                    }
                })
    }



}