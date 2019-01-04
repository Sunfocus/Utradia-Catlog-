package com.utradia.catalogueappv2.module.search

import com.utradia.catalogueappv2.model.Offer
import com.utradia.catalogueappv2.mvpbase.MvpView

interface SearchProductView : MvpView {

    fun productList(data: List<Offer>)

    fun onError(error: String)
}