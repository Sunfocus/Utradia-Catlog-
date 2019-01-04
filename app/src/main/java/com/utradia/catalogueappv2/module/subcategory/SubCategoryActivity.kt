package com.utradia.catalogueappv2.module.subcategory

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.SubCategoryData
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.productlist.ProductListActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_categorylevel.*
import javax.inject.Inject

class  SubCategoryActivity :BaseActivity(),SubCategoryView,OnSubCatClicked{

    override fun onSubCategoryClicked(id: String, name: String) {
        startActivity(ProductListActivity.createIntent(this,id,name,"","",""))
    }


    override fun onSubCategoryNotFound(error: String) {
        txtNoItems.visibility=View.VISIBLE
        rvSubCategories.visibility= View.GONE
    }

    override fun onSubCategoryFound(subCategoryData: SubCategoryData) {
        txtNoItems.visibility=View.GONE
        rvSubCategories.visibility= View.VISIBLE
        val layoutManager1 = GridLayoutManager(this, 3)
        rvSubCategories.layoutManager = layoutManager1

        val mSubCatAdapter = SubCategoriesAdapter(this, subCategoryData.sub_categories,this)
        rvSubCategories.itemAnimator= ScaleInAnimator()
        //val animatorAdapter = ScaleInAnimatorAdapter<SubCategoriesAdapter.ViewHolder>(mSubCatAdapter, rvSubCategories)
        rvSubCategories.adapter = mSubCatAdapter
    }

    @Inject lateinit var mSubCategoryPresenter: SubCategoryPresenter
    @Inject lateinit var mPrefs: PreferenceManager

    override val layout: Int
        get() = R.layout.activity_categorylevel

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
        mSubCategoryPresenter.injectDependencies(this)
        mSubCategoryPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        getSubCategoryData()
    }

    private fun getSubCategoryData(){
        if (mAppUtils.isInternetConnected)
            mSubCategoryPresenter.getSubCategories(intent.getStringExtra("id_extra"))
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("name_extra")

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
        mSubCategoryPresenter.cancelAllAsync()
        mSubCategoryPresenter.detachView()
    }


    companion object {
        fun createIntent(context: Context,name:String,id: String): Intent {
            return Intent(context, SubCategoryActivity::class.java)
                    .putExtra("name_extra",name)
                    .putExtra("id_extra",id)
        }
    }
}