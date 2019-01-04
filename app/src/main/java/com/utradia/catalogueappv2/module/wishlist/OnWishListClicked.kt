package com.utradia.catalogueappv2.module.wishlist

interface OnWishListClicked {
    fun onWishListItemClicked(id:String,name:String)
    fun onDeleteClicked(id:String,pos:Int)
}