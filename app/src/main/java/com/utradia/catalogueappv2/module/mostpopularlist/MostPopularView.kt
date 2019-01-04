package com.utradia.catalogueappv2.module.mostpopularlist

import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface MostPopularView : MvpView{

    fun onOffersNotFound(error_message: String)
    fun onOffersFound(response: ProductsByCatResponse)

}