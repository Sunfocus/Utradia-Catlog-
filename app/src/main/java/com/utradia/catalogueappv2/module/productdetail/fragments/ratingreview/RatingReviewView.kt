package com.utradia.catalogueappv2.module.productdetail.fragments.ratingreview

import com.utradia.catalogueappv2.model.ProductReviewListResponse
import com.utradia.catalogueappv2.mvpbase.MvpView

interface RatingReviewView :MvpView {
    fun onReviewFound(reviewData: ProductReviewListResponse)
    fun onErrorOccur(error: String)
}