package com.utradia.catalogueappv2.model.custom

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceListModel(
        val name: String,
        var id: Int,
        var min:Int,
        var max:Int
) : Parcelable