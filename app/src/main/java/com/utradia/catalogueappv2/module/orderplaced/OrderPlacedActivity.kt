package com.utradia.catalogueappv2.module.orderplaced

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.view.View
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_order_placed.*
import javax.inject.Inject

class OrderPlacedActivity :BaseActivity(){

    @Inject lateinit var mPrefs: PreferenceManager

    override val layout: Int
        get() = R.layout.activity_order_placed

    override fun showToolbar(): Boolean {
        return false
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)

        mAppUtils.changeStatusBarColor(this)

        updateUI()
        initClicks()
    }

    private fun initClicks() {
        btnContinueShopping.setOnClickListener {
            redirect()
        }
    }

    /*updating UI
    * */
    private fun updateUI() {
        txtOrderId.text=intent.getStringExtra("order_id_extra")
        if (intent.getStringExtra("order_type_extra").equals("cart",true))
        mPrefs.cartCount=""
    }

    companion object {
        fun createIntent(context: Context, order_id: String, main_order_id: String,isBuyNow: String): Intent {
            return Intent(context, OrderPlacedActivity::class.java)
                    .putExtra("order_id_extra",order_id)
                    .putExtra("main_id_extra",main_order_id)
                    .putExtra("order_type_extra",isBuyNow)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        redirect()
    }

    /*
    * Redirect user to home
    * */
    private fun redirect(){
        finishAffinity()
        startActivity(HomeActivity.createIntent(this))
    }
}