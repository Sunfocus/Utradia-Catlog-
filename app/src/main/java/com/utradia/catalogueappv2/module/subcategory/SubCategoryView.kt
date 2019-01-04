package com.utradia.catalogueappv2.module.subcategory

import com.utradia.catalogueappv2.model.SubCategoryData
import com.utradia.catalogueappv2.mvpbase.MvpView

interface SubCategoryView :MvpView {
    fun onSubCategoryFound(subCategoryData: SubCategoryData)
    fun onSubCategoryNotFound(error: String)
}