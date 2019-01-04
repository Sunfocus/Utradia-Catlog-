package com.utradia.catalogueappv2.mvpbase

interface MvpView {

    fun showProgress()

    fun hideProgress()

    fun onError(t: Throwable)

    fun onException(message: String)


}