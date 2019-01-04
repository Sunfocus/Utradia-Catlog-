package com.utradia.catalogueappv2.module.productdetail.productoptions

interface OnColorSelected {
    fun onColorSelected(id: String, pos:Int,name:String,grp_id:String,price:String,image:String)
}