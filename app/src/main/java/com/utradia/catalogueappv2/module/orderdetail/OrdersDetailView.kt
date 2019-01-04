package com.utradia.catalogueappv2.module.orderdetail

import com.utradia.catalogueappv2.model.OrderDetailResponse
import com.utradia.catalogueappv2.model.OrderListResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface OrdersDetailView : MvpView {
    fun orderDetail(response: OrderDetailResponse)

    fun onErrorOccur(message: String)
}
