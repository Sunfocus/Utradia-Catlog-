package com.utradia.catalogueappv2.module.notifications.settings

import com.utradia.catalogueappv2.model.SettingsResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface NotiSettingsView :MvpView{
    fun onSettingsReceived(response:SettingsResponse)
    fun onSettingsNotReceived(msg:String)
}