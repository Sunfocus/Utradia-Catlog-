package com.utradia.catalogueappv2.module.profile
import com.utradia.catalogueappv2.model.ProfileResponse
import com.utradia.catalogueappv2.model.RegisterResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface ProfileView : MvpView {
    fun onUserDetailsFound(response: ProfileResponse)
    fun onUserDetailsFailed(error_message: String)
    fun onUserUpdated(response: RegisterResponse)
    fun onUpdationFailed(error_message: String)
}