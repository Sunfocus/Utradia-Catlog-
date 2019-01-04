package com.utradia.catalogueappv2.module.shops

interface OnShopClicked {
    fun onShopClicked(id:String,brand_name:String,logo:String,pos:Int)
}