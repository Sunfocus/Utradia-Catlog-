package com.utradia.catalogueappv2.module.storedetail.more_options

import android.os.Bundle
import android.text.Html
import android.view.View
import com.utradia.catalogueappv2.R

import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import kotlinx.android.synthetic.main.activity_info.*
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity

class InfoActivity : BaseActivity() {

    override val layout: Int
        get() = R.layout.activity_info

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        updateUI()
        setToolbar()
        mAppUtils.changeStatusBarColor(this)


    }


    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.shop_info)

        toolbar.setNavigationOnClickListener { finish() }
    }


    fun updateUI() {
        val response = StoreDetailActivity.response
        infoDescription.text = Html.fromHtml(response!!.client_data.policies + "\n\n" + response.client_data.shipping_policy)

    }
}

