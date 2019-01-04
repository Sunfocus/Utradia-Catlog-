package com.utradia.catalogueappv2.model.custom

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterDataModel(
        val name:String,
        var count:String
) : Parcelable