package com.utradia.catalogueappv2.mvpbase

interface Presenter<V : MvpView> {

    fun attachView(mvpView: V)

    fun detachView()

}