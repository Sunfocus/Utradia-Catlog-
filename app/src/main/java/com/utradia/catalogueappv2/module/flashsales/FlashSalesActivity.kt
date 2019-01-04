package com.utradia.catalogueappv2.module.flashsales

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.FlashSalesResponse
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.flashsales.current.CurrentSaleFragment
import com.utradia.catalogueappv2.module.flashsales.next.NextSaleFragment
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_flashsales.*
import javax.inject.Inject

class FlashSalesActivity:BaseActivity(),FlashSalesView {
    override fun onFlashSalesFound(response: FlashSalesResponse) {
        mFlashResponse=response


        tabLayoutSales.visibility=View.VISIBLE
        val tabsName = arrayOf(getString(R.string.ongoing), getString(R.string.upcoming))

        val adapter = FragmentAdapterSales(supportFragmentManager)

        val overViewFragment = CurrentSaleFragment()
        val descriptionFragment = NextSaleFragment()


        adapter.addFragment(overViewFragment,tabsName[0])
        adapter.addFragment(descriptionFragment,tabsName[1])


        viewPagerSales.adapter=adapter
        tabLayoutSales.setupWithViewPager(viewPagerSales)
    }

    override fun onFlashNotFound(error: String) {

    }

    @Inject lateinit var mFlashSalesPresenter: FlashSalesPresenter
    @Inject lateinit var mPrefs: PreferenceManager


    override val layout: Int
        get() = R.layout.activity_flashsales

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)
        mFlashSalesPresenter.injectDependencies(this)
        mFlashSalesPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        getEventsList()
    }

    companion object {
         var mFlashResponse: FlashSalesResponse?=null
        fun createIntent(context: Context): Intent {
            return Intent (context, FlashSalesActivity::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_cart, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(CartActivity.createIntent(this))
                true
            }
            R.id.action_search -> {
                startActivity(SearchProduct.createIntent(this,""))
                true
            }

            R.id.action_more -> {
                val popup = PopupMenu(this, findViewById(R.id.action_more))
                popup.menuInflater.inflate(R.menu.menu_product_option, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_home -> {
                            finishAffinity()
                            startActivity(HomeActivity.createIntent(this))
                            true
                        }

                        R.id.menu_account -> {
                            if (mPrefs.isUserLoggedIn)
                                startActivity(MyAccountActivity.createIntent(this))
                            else
                                startActivity(LoginActivity.createIntent(this, ValueConstants.HOME_SCREEN, ValueConstants.HIDE_SKIP))
                            true
                        }
                        else ->
                            false
                    }
                }
                popup.show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mFlashResponse=null
        mFlashSalesPresenter.cancelAllAsync()
        mFlashSalesPresenter.detachView()
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val itemCart = menu?.findItem(R.id.action_cart)
        val icon = itemCart?.icon as LayerDrawable
        if (!mPrefs.cartCount.equals("",true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)


        return super.onPrepareOptionsMenu(menu)

    }

    /*method calls api to get event list*/
    private fun getEventsList(){
        if (mAppUtils.isInternetConnected)
            mFlashSalesPresenter.getFlashSales()
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.flash_sales)

        toolbar.setNavigationOnClickListener({
            finish()
        })
    }

    private fun handleViewClicks(){

    }
}