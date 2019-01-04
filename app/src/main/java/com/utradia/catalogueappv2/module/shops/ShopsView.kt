package com.utradia.catalogueappv2.module.shops

import com.utradia.catalogueappv2.model.ShopListResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface ShopsView :MvpView{
    fun onShopsNotFound(error_message: String)
    fun onShopsFound(response: ShopListResponse)


}