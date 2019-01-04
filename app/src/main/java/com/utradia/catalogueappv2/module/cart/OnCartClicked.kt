package com.utradia.catalogueappv2.module.cart

interface OnCartClicked {
    fun onDeleteClicked(id : String , pos :Int)
    fun onWishListClicked(id : String , pos :Int)
    fun onQuantityChanged(id : String , pos :Int,qty:Int)
    fun onItemClicked(id : String , name:String)
}