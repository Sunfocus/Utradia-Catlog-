package com.utradia.catalogueappv2.module.events

import com.utradia.catalogueappv2.model.EventsResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface EventsView: MvpView {
    fun onEventsNotFound(error_message: String)
    fun onEventsFound(response: EventsResponse)
}