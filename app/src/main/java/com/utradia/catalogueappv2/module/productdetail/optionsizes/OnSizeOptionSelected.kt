package com.utradia.catalogueappv2.module.productdetail.optionsizes

import com.utradia.catalogueappv2.model.ProductDetailResponse

interface OnSizeOptionSelected {
    fun onSizeSelected(sizes: List<ProductDetailResponse.OfferDetailsBean.PricesBean>,pos :Int)
}