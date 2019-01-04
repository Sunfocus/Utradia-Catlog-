package com.utradia.catalogueappv2.module.cart

import com.utradia.catalogueappv2.model.CartResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface CartView:MvpView {
    fun onCartItemsFound(response: CartResponse)
    fun onCartItemsError(error: String)
    fun onItemsNotFound(error: String)
    fun onItemRemoved()
    fun onQuantityUpdated()
    fun onMovedToWishList(msg:String)
}