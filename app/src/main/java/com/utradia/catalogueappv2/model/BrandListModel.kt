package com.utradia.catalogueappv2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class BrandListModel(
        val success: Int,
        val success_message: String,
        val brands: MutableList<BrandList>,
        val error_type: String
)

@Parcelize
data class BrandList(
        val id: String,
        val name: String,
        val description: String,
        val image: String,
        val created: String,
        val status: String,
        val sub_cat: String,
        val order_data: String,
        var header:Boolean,
        var check_status:Boolean
) : Parcelable

