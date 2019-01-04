package com.utradia.catalogueappv2.module.home

import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface HomeView:MvpView {
    fun onHomeDataReceived(response: HomeResponse)
    fun onHomeDataFailed(error_message: String?)
    fun onDeviceRegistered()

}