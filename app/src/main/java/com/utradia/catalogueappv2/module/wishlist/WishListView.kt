package com.utradia.catalogueappv2.module.wishlist

import com.utradia.catalogueappv2.model.FavouriteResponse
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.model.WishListResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface WishListView : MvpView{

    fun onWishListItemsFound(wishListResponse: WishListResponse)
    fun onWishListError(msg:String)
    fun onRemovedWishList(response: FavouriteResponse)
}