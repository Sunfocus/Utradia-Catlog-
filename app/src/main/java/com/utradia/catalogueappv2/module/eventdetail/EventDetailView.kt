package com.utradia.catalogueappv2.module.eventdetail

import com.utradia.catalogueappv2.model.EventDetailResponse
import com.utradia.catalogueappv2.model.EventsResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface EventDetailView :MvpView {
    fun onEventDetailNotFound(error_message: String)
    fun onEventDetailFound(response: EventDetailResponse)
    fun onEventGoing(success_message: String)
    fun onEventGoingFailure(error_message: String)
    fun onEventUnGoing(success_message: String)

     fun callPermissionsGranted()
     fun getDialogType(dialogType: Int)
}