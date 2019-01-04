package com.utradia.catalogueappv2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ShopListModel(
        val success: Int,
        val success_message: String,
        val shop_lists_data: ArrayList<ShopListsData>,
        val error_type: String
)
@Parcelize
data class ShopListsData(
        val logo: String,
        val id: String,
        val name: String,
        val brand_name: String,
        val header:Boolean,
        var check_status:Boolean
) : Parcelable