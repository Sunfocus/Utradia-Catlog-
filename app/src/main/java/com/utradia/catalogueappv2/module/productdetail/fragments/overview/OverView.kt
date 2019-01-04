package com.utradia.catalogueappv2.module.productdetail.fragments.overview

import com.utradia.catalogueappv2.model.FavouriteResponse
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface OverView :MvpView {

    fun onAddedToWishList(response: FavouriteResponse)
    fun onRemovedWishList(response: FavouriteResponse)
    fun onWishListError(error: String)

}