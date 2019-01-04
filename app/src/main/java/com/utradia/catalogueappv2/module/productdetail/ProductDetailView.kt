package com.utradia.catalogueappv2.module.productdetail

import com.utradia.catalogueappv2.model.AddToCartResponse
import com.utradia.catalogueappv2.model.ProductDetailResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface ProductDetailView : MvpView {
    fun onProductDetailFound(response: ProductDetailResponse)
    fun onProductDetailNotFound(error_message: String)
    fun onAddedToCart(checkNumberReponse: AddToCartResponse)
    fun onBuyNowCart(checkNumberReponse: AddToCartResponse)
    fun onCartError(error_message: String)

}