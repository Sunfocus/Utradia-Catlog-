package com.utradia.catalogueappv2.module.checkout

import com.utradia.catalogueappv2.model.CartResponse
import com.utradia.catalogueappv2.model.CheckoutData
import com.utradia.catalogueappv2.model.DiscountResponse
import com.utradia.catalogueappv2.model.ShipMethodsResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface CheckoutView:MvpView {
    fun onCartItemsFound(response: CheckoutData)
    fun onItemsNotFound(error: String)
    fun onPromoCodeError(error: String)
    fun onPromoCodeSuccess(response: DiscountResponse)
    fun onShipOptionFound(response: ShipMethodsResponse)
}