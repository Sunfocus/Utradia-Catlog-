package com.utradia.catalogueappv2.module.loginsignup.signup

import com.utradia.catalogueappv2.model.RegisterResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface RegisterView : MvpView {

    fun enableButton()
    fun disableButton()
    fun onUserRegistered(registerResponse: RegisterResponse)
    fun onUserRegisterationFailed(registerResponse: RegisterResponse)
}