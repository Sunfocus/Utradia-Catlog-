package com.utradia.catalogueappv2.module.shops

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.ShopListResponse
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_shops.*
import javax.inject.Inject

class ShopsActivity:BaseActivity(), ShopsView,OnShopClicked{

    lateinit var mAdapter:ShopsAdapter

    private lateinit var beanFilterArrayList: MutableList<ShopListResponse.ShopListsDataBean>


    override fun onShopClicked(id:String,brand_name:String,logo:String,pos:Int) {
        startActivity(StoreDetailActivity.createIntent(this,id,logo,brand_name))
    }

    override fun onShopsNotFound(error_message: String) {
        txtNoShopsFound.visibility=View.VISIBLE
        rvShops.visibility=View.GONE
    }

    override fun onShopsFound(response: ShopListResponse) {
        txtNoShopsFound.visibility=View.GONE
        rvShops.visibility=View.VISIBLE

        if (response.shop_lists_data.size>0) {

            beanFilterArrayList.addAll(response.shop_lists_data)
            //val animatorAdapter = ScaleInAnimatorAdapter<ShopsAdapter.ViewHolder>(mAdapter, rvShops)
            mAdapter.notifyDataSetChanged()
        }
        else
        {
            onShopsNotFound("")
        }
    }


    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    override
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mShopsPresenter: ShopsPresenter

    /**
     * get layout to inflate
     */
    override val layout: Int
        get() = R.layout.activity_shops

    override fun showToolbar(): Boolean {
        return true
    }

    /**
     * get layout to inflate
     */
    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)
        /*attaching view{@link LoginView} to our presenter*/
        mShopsPresenter.injectDependencies(this)
        mShopsPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        invalidateOptionsMenu()

        settingLayout()

        /*get Shop list*/
        getShopsList()

       /* filter Shop list*/
        edtSearch.afterTextChanged()
    }

    private fun settingLayout() {
        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvShops.layoutManager = layoutManager1

        beanFilterArrayList=ArrayList()

        mAdapter = ShopsAdapter(this, beanFilterArrayList, this)

        rvShops.itemAnimator= ScaleInAnimator()
        rvShops.adapter = mAdapter
    }





    private fun EditText.afterTextChanged() {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                mAdapter.getFilter().filter(editable.toString())
            }
        })
    }

    private fun getShopsList(){
        if (mAppUtils.isInternetConnected)
            mShopsPresenter.getShops()
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

    }
    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.shops)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_cart, menu)

        val search_item = menu.findItem(R.id.action_search)

        search_item.isVisible=false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(CartActivity.createIntent(this))
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val itemCart = menu?.findItem(R.id.action_cart)
        val icon = itemCart?.icon as LayerDrawable
        if (!mPrefs.cartCount.equals("",true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)


        return super.onPrepareOptionsMenu(menu)

    }

    override fun onDestroy() {
        super.onDestroy()
        mShopsPresenter.cancelAllAsync()
        mShopsPresenter.detachView()
    }


    companion object {
        fun createIntent(context: Context): Intent{
            return Intent(context,ShopsActivity::class.java)
        }
    }

}