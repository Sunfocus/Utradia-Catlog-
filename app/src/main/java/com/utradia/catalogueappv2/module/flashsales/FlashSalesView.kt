package com.utradia.catalogueappv2.module.flashsales

import com.utradia.catalogueappv2.model.FlashSalesResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface FlashSalesView:MvpView {
    fun onFlashSalesFound(response: FlashSalesResponse)
    fun onFlashNotFound(error: String)
}