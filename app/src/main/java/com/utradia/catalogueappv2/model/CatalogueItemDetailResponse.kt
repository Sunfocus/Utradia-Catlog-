package com.utradia.catalogueappv2.model

data class CatalogueItemDetailResponse(
    val catalogue_items: List<CatalogueItem>,
    val catalogue_name: String,
    val error_type: String,
    val success: Int,
    val error_message:String,
    val success_message: String
)

data class CatalogueItem(
    val catalogue_id: String,
    val created: String,
    val description: String,
    val discounted_price: String,
    val end_date: String,
    val id: String,
    val image: String,
    val order_data: String,
    val price: String,
    val show_on_app: String,
    val status: String,
    val title: String
)