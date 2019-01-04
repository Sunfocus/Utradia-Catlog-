package com.utradia.catalogueappv2.module.events

import android.widget.ImageView

interface OnEventClicked {
    fun onAnEventClicked(event_id: String,title: String,imageUrl: String,imageView:ImageView)
}