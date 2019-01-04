package com.utradia.catalogueappv2.module.productdetail.productoptions

import com.utradia.catalogueappv2.model.ProductDetailResponse

interface OnSizeSelected {
    fun onSizeSelected(sizes: List<ProductDetailResponse.OfferDetailsBean.SizesBean>,pos :Int)
}