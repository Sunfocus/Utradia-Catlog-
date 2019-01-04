package com.utradia.catalogueappv2.module.filter

import com.utradia.catalogueappv2.model.BrandListModel
import com.utradia.catalogueappv2.model.CategoryListModel
import com.utradia.catalogueappv2.model.ShopListModel
import com.utradia.catalogueappv2.mvpbase.MvpView

interface FilterView :MvpView {

    fun onErrorOccur(error:String)
    fun onCategoryList(response:CategoryListModel)

}