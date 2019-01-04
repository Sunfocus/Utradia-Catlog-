package com.utradia.catalogueappv2.module.productdetail.productpresenter

import com.utradia.catalogueappv2.model.AddToCartResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface ProductOptionsView :MvpView {

    fun onAddedToCart(checkNumberReponse: AddToCartResponse)
    fun onBuyNowCart(checkNumberReponse: AddToCartResponse)

    fun onCartError(error_message: String)
}

