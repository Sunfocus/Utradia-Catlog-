package com.utradia.catalogueappv2.model

data class CatalogueListModel(
        val catalogues: List<Catalogue>,
        val error_type: String,
        val success: Int,
        val success_message: String,
        val error_message: String
)

data class Catalogue(
        val category_id: String,
        val center: String,
        val created: String,
        val description: String,
        val id: String,
        val main_image: String,
        val order_data: String,
        val show_on_app: String,
        val status: String,
        val title: String,
        val user_id: String
)