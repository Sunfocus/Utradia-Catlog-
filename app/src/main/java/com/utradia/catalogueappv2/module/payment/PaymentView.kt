package com.utradia.catalogueappv2.module.payment

import com.utradia.catalogueappv2.model.OrderPlacedResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface PaymentView:MvpView {
    fun onOrderPlaced(response: OrderPlacedResponse)

    fun onSuccess(success:String)
    fun onOrderNotPlaced(error_message: String)
}