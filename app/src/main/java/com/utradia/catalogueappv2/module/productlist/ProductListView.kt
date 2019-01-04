package com.utradia.catalogueappv2.module.productlist

import com.utradia.catalogueappv2.model.FavouriteResponse
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface ProductListView: MvpView {
    fun onProductsNotFound(error_message: String)
    fun onProductsFound(response: ProductsByCatResponse)

    fun onAddedToWishList(response: FavouriteResponse)
    fun onRemovedWishList(response: FavouriteResponse)
    fun onWishListError(error: String)
}