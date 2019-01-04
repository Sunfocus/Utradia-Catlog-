package com.utradia.catalogueappv2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CategoryListModel(
        val success: Int,
        val success_message: String,
        val error_message: String,
        val categories: List<Category>,
        val error_type: String
)

@Parcelize
data class Category(
        val id: String,
        val name: String,
        val have_child: String,
        val image: String,
        val parent_id: String,
        val header:Boolean,
        var check_status:Boolean
) : Parcelable