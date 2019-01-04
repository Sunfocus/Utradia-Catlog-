package com.utradia.catalogueappv2.module.home

interface OnHomeCatClicked {

    fun onCategoryClicked(id: String,name :String)
    fun onAllCategoryClicked()
    fun onRecommendedItemClicked(id :String,name : String)
    fun onFlashItemClicked(id :String,name : String)
    fun omMostPopularClicked(id :String,name : String,cat_id:String)
    fun onBannerClicked(id :String,name : String)

}