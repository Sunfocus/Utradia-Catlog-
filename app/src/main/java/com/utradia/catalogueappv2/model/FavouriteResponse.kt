package com.utradia.catalogueappv2.model

data class FavouriteResponse(
        val error_type: String,
        val favourite_data: FavouriteData,
        val success: Int,
        val success_message: String,
        val error_message:String
)

data class FavouriteData(
        val created: String,
        val id: String,
        val offer_data: OfferData,
        val offer_id: String,
        val status: String,
        val user_id: String
)

data class OfferData(
        val brandname: String,
        val category_id: String,
        val center_id: String,
        val cities: String,
        val client: String,
        val cod: String,
        val created: String,
        val default_quantity: String,
        val delivery_options: String,
        val description: String,
        val discount: String,
        val discounted_price: String,
        val end_date: String,
        val id: String,
        val image: String,
        val image2: String,
        val image3: String,
        val image4: String,
        val model: String,
        val pay_and_send: String,
        val pay_online: String,
        val pickup_store: String,
        val prices: List<Price>,
        val product_weight: String,
        val purchase_link: String,
        val rating: String,
        val region_id: String,
        val shipment_charges: String,
        val shop_enabel: String,
        val show_on_app: String,
        val size_category: String,
        val size_type: String,
        val status: String,
        val title: String,
        val variant_group_type: String,
        val views: String
)

data class Price(
        val color: String,
        val created: String,
        val grp_id: String,
        val id: String,
        val offer_id: String,
        val price: String,
        val quantity: String,
        val size_category: String,
        val size_type: String,
        val sizes: String,
        val status: String,
        val used: String,
        val var_image: String,
        val variant_title: String
)