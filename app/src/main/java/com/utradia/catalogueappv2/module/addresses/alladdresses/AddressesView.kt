package com.utradia.catalogueappv2.module.addresses.alladdresses

import com.utradia.catalogueappv2.model.AddressResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface AddressesView :MvpView{
    fun onAddressFound(response : AddressResponse)
    fun onAddressNotFound(error: String)
    fun onAddressDeleted(msg:String)
    fun onAddressDefault(msg:String)
    fun onAddressError(error_message: String)
}