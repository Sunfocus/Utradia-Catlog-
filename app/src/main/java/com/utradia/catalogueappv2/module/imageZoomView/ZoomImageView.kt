package com.utradia.catalogueappv2.module.imageZoomView

import com.utradia.catalogueappv2.model.CatalogueItem
import com.utradia.catalogueappv2.mvpbase.MvpView

interface ZoomImageView : MvpView {
    fun onCtalogueItemResponse(catalogue_items: List<CatalogueItem>)
    fun onErrorOccur(error_message: String)
}