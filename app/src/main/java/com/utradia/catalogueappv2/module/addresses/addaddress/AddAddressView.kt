package com.utradia.catalogueappv2.module.addresses.addaddress

import com.utradia.catalogueappv2.model.AddressDetailResponse
import com.utradia.catalogueappv2.model.AddressResponse
import com.utradia.catalogueappv2.model.CitiesResponse
import com.utradia.catalogueappv2.model.RegionsResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface AddAddressView :MvpView {
    fun onRegionsFound(response: RegionsResponse)
    fun onCitiesFound(response: CitiesResponse,city:String,city_id:String)
    fun onRegionsNotFound(error_message: String)
    fun onAddressAdded(message: String)
    fun onAddressDetailFound(addressResponse: AddressDetailResponse)
}