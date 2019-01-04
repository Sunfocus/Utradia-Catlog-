package com.utradia.catalogueappv2.module.categoryproductlist

import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface CatProView:MvpView {
    fun onOffersNotFound(error_message: String)
    fun onOffersFound(response: ProductsByCatResponse)
}