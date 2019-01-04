package com.utradia.catalogueappv2.model.custom

import android.os.Parcelable
import com.utradia.catalogueappv2.model.Category
import com.utradia.catalogueappv2.model.ProductsByCatResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterListModel(
        var brandList: List<ProductsByCatResponse.BrandsBean>,
        var productRating: RatingListModel?,
        var shopList: List<ProductsByCatResponse.ShopsBean>,
        var priceList: PriceListModel?,
        var catList: List<String>?
) : Parcelable