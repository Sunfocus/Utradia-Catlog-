package com.utradia.catalogueappv2.module.productlist

import java.text.FieldPosition

interface OnProductClick {
    fun onItemClick(position: Int,id:String,name: String)

    fun addToFavourite(id: String,position: Int)

    fun removeFavouroite(id: String,position: Int)
}