package com.utradia.catalogueappv2.module.recomendedItems

import com.utradia.catalogueappv2.model.RecomendedResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface RecomendedView :MvpView{
    fun onOffersNotFound(error_message: String)
    fun onOffersFound(response: RecomendedResponse)

}