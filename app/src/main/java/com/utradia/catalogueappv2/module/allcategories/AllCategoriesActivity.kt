package com.utradia.catalogueappv2.module.allcategories

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.CategoriesResponse
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.productlist.ProductListActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator

import kotlinx.android.synthetic.main.activity_allcategories.*
import javax.inject.Inject

class AllCategoriesActivity :BaseActivity() ,AllCategoriesView,OnItemInteracted,OnSubCatClicked{

    lateinit var response: CategoriesResponse
    @Inject lateinit var mPrefs:PreferenceManager
    @Inject lateinit var mAllCategoriesPresenter:AllCategoriesPresenter
    lateinit var mAdapter: CategoriesAdapter
    lateinit var mSubCatAdapter: SubCategoryAdapter

    override val layout: Int
        get() = R.layout.activity_allcategories

    override fun showToolbar(): Boolean {
       return true
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)
        /*attaching view{@link LoginView} to our presenter*/
        mAllCategoriesPresenter.injectDependencies(this)
        mAllCategoriesPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        getCategoriesData()
    }

    private fun getCategoriesData() {
        if (mAppUtils.isInternetConnected)
            mAllCategoriesPresenter.getTwoLevelCategories()
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        //supportActionBar!!.setTitle(R.string.categories)

        toolbar.setNavigationOnClickListener {
            finish()
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val itemCart = menu?.findItem(R.id.action_cart)
        val icon = itemCart?.icon as LayerDrawable
        if (!mPrefs.cartCount.equals("",true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)


        return super.onPrepareOptionsMenu(menu)

    }

    override fun onDestroy() {
        super.onDestroy()
        mAllCategoriesPresenter.cancelAllAsync()
        mAllCategoriesPresenter.detachView()

    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, AllCategoriesActivity::class.java)
        }
    }


    /*
    *
    *
    *
    * View callbacks
    *
    *
    *
    */
    override fun onMainItemClicked(id: String,pos:Int) {

        for (item: CategoriesResponse.CategoriesBean in response.categories) {
            item.isSelected=false
        }
        response.categories[pos].isSelected=true

        mAdapter.setItems(response.categories)
        mAdapter.notifyDataSetChanged()

        updateSubCategories(response.sub_categories[pos])
    }

    /*
    called when sub category clicked
    * */
    override fun onSubCategoryClicked(id: String,name:String) {
        startActivity(ProductListActivity.createIntent(this,id,name,"","",""))
    }

    /*
    * updating categories*/
    override fun onEventDetailFound(response: CategoriesResponse) {
        this.response=response
        if(response.categories!=null && response.categories.size>0)
        updateCategories(response.categories)

        updateSubCategories(response.sub_categories[0])
    }


    override fun onEventDetailNotFound(error_message: String) {
    }


    /*
    * updating home page categories
    * */
    private fun updateCategories(categories: List<CategoriesResponse.CategoriesBean>) {
        /*setting first item selected*/
        categories[0].isSelected=true

        rvAllCats.visibility=View.VISIBLE
        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvAllCats.layoutManager = layoutManager1

        mAdapter = CategoriesAdapter(this, categories,this)
        rvAllCats.itemAnimator= ScaleInAnimator()
     //   val animatorAdapter = ScaleInAnimatorAdapter<CategoriesAdapter.ViewHolder>(mAdapter, rvAllCats)
        rvAllCats.adapter = mAdapter

    }

    private fun updateSubCategories(subCategoriesBean: MutableList<CategoriesResponse.SubCategoriesBean>) {
        if (subCategoriesBean.size>0) {
            txtNoItems.visibility=View.GONE
            rvSubCategories.visibility=View.VISIBLE
            val layoutManager1 = GridLayoutManager(this, 3)
            rvSubCategories.layoutManager = layoutManager1

            mSubCatAdapter = SubCategoryAdapter(this, subCategoriesBean,this)
            rvSubCategories.itemAnimator=ScaleInAnimator()
           // val animatorAdapter = ScaleInAnimatorAdapter<SubCategoryAdapter.ViewHolder>(mSubCatAdapter, rvSubCategories)
            rvSubCategories.adapter = mSubCatAdapter
        }
        else
        {
            txtNoItems.visibility=View.VISIBLE
            rvSubCategories.visibility=View.GONE
        }
    }

}