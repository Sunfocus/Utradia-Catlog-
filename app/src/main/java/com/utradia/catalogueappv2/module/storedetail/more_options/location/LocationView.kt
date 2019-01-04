package com.utradia.catalogueappv2.module.storedetail.more_options.location

import com.utradia.catalogueappv2.mvpbase.MvpView

interface LocationView :MvpView {
    fun callPermissionsGranted()
    fun getDialogType(dialogType: Int)
}