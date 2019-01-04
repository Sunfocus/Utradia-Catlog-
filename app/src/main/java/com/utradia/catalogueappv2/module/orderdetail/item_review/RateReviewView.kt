package com.utradia.catalogueappv2.module.orderdetail.item_review

import com.utradia.catalogueappv2.mvpbase.MvpView

interface RateReviewView : MvpView {
    fun onReviewAdded(sucessMessage: String)
    fun onErrorOccur(errorMessage: String)
}