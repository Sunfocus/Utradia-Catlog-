package com.utradia.catalogueappv2.module.storedetail

import com.utradia.catalogueappv2.model.StoreDetailResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface StoreDetailView :MvpView {
    fun onClientDataFound(response: StoreDetailResponse)
    fun onClientDataNotFound(error_message: String)

    fun callPermissionsGranted()
    fun getDialogType(dialogType: Int)
}