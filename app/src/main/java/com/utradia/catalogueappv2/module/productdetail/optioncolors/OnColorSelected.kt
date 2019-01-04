package com.utradia.catalogueappv2.module.productdetail.optioncolors

interface OnColorSelected {
    fun onColorSelected(id: String, pos:Int,name:String,grp_id:String,qty:String,price:String,image:String)
}