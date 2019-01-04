package com.utradia.catalogueappv2.module.notifications

import com.utradia.catalogueappv2.model.NotificationData
import com.utradia.catalogueappv2.mvpbase.MvpView

interface NotificationView:MvpView {
    fun onErrorOccur(errorMsg:String)
    fun onNotificationResponse(data: List<NotificationData>)
}