package com.utradia.catalogueappv2.module.loginsignup.login

import com.utradia.catalogueappv2.model.RegisterResponse
import com.utradia.catalogueappv2.model.LoginResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface LoginView : MvpView{
     fun enableButton()
     fun disableButton()
     fun onSocialUserAlreadyExist(response: RegisterResponse)
     fun onUserLoggedIn(response: LoginResponse)
     fun onLoginFailed(response: LoginResponse)

}