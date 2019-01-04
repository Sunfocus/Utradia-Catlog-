package com.utradia.catalogueappv2.module.addresses.alladdresses

interface onAddressInteracted {
    fun onEditClicked(id:String,pos:Int)
    fun onDeleteClicked(id:String,pos:Int)
    fun onDefaultSelected(id:String,pos:Int)
}