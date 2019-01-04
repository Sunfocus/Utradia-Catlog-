package com.utradia.catalogueappv2.module.allcategories

import com.utradia.catalogueappv2.model.CategoriesResponse
import com.utradia.catalogueappv2.model.EventDetailResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface AllCategoriesView:MvpView {
    fun onEventDetailFound(response: CategoriesResponse)
    fun onEventDetailNotFound(error_message: String)
}