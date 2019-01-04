package com.utradia.catalogueappv2.module.discovery.tabListScreen

import com.utradia.catalogueappv2.model.Catalogue
import com.utradia.catalogueappv2.model.NotificationData
import com.utradia.catalogueappv2.mvpbase.MvpView

interface TabView:MvpView {
    fun onErrorOccur(errorMsg:String)
    fun onCtalogueListResponse(data: List<Catalogue>)
}