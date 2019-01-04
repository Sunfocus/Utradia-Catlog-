package com.utradia.catalogueappv2.module.orders

interface OnOrderClicked {
    fun onItemClicked(id :String ,orderTotal:Double,position:Int,viewType:String)

}