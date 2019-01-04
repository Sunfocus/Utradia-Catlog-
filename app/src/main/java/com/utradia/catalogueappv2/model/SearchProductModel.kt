package com.utradia.catalogueappv2.model

data class SearchProductModel(
        val success: Int,
        val success_message: String,
        val error_message: String,
        val offers: List<Offer>,
        val error_type: String
)

data class Offer(
        val id: String,
        val offer_id: String,
        val keyword: String,
        val category_id: String,
        val category_name: String
)