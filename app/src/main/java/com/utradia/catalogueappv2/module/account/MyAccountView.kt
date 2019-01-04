package com.utradia.catalogueappv2.module.account

import com.utradia.catalogueappv2.mvpbase.MvpView

interface MyAccountView : MvpView{


    fun onImageUpload(message:String,image: String)

    fun onErrorOccur(error:String)

    fun onLogoutResponse(message: String)
}