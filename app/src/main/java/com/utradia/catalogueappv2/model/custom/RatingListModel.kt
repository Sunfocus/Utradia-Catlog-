package com.utradia.catalogueappv2.model.custom

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RatingListModel(
        val name: String,
        val id:Int,
        val number:String
) : Parcelable