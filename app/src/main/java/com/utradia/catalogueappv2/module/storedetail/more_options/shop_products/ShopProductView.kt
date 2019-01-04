package com.utradia.catalogueappv2.module.storedetail.more_options.shop_products

import com.utradia.catalogueappv2.model.ClientProductResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface ShopProductView:MvpView {
    fun onProductsFound(response:ClientProductResponse)
    fun onProductsNotFound(error:String)
    fun onClientFollowed()
    fun onClientUnFollowed()
    fun onFollowError(msg:String)
}