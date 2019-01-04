package com.utradia.catalogueappv2.module.orders

import com.utradia.catalogueappv2.model.OrderListResponse
import com.utradia.catalogueappv2.model.OrdersResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface OrdersView :MvpView{
    fun onOrdersNotFound(error_message: String)

    fun onOrdersFound(response: OrderListResponse)

    fun onCancelOrder(message:String)

    fun onErrorOccur(message: String)
}