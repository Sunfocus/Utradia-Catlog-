package com.utradia.catalogueappv2.module.storedetail.more_options.catalogue

import com.utradia.catalogueappv2.model.Catalogue
import com.utradia.catalogueappv2.model.NotificationData
import com.utradia.catalogueappv2.mvpbase.MvpView

interface CatalogueView:MvpView {
    fun onErrorOccur(errorMsg:String)
    fun onCtalogueListResponse(data: List<Catalogue>)
}