package com.utradia.catalogueappv2.module.recomendedItems

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.RecomendedResponse
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_recomended.*
import javax.inject.Inject

class RecomendedActivity :BaseActivity(),RecomendedView,OnRecomendedItemsClicked{
    override fun onItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(this,id,name,false))

    }

    override fun onOffersNotFound(error_message: String) {
        if (mProductList.isEmpty()) {
            txtNoProducts.visibility= View.VISIBLE
            rvProductList.visibility= View.GONE
        }
        else{
            mAppUtils.showSnackBar(window.decorView.rootView,"No more products")
            loading=false
        }

    }

    override fun onOffersFound(response: RecomendedResponse) {
        loading=true
        updateProductList(response.offers)
    }

    @Inject
    lateinit var mRecomendedPresenter: RecomendedPresenter
    @Inject
    lateinit var mPrefs: PreferenceManager
    private  var mAdapter: RecomendedAdapter?=null
    var page:Int=1

    private var loading = true
    var pastVisibleItems: Int = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    private  val mProductList: MutableList<RecomendedResponse.OffersBean> = ArrayList()
    private lateinit var  layoutManager: androidx.recyclerview.widget.LinearLayoutManager

    override val layout: Int
        get() = R.layout.activity_recomended

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
        mRecomendedPresenter.injectDependencies(this)
        mRecomendedPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        getProductList()
        rvProductList.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun getProductList() {
        if (mAppUtils.isInternetConnected)
            mRecomendedPresenter.getRecommendedItems(mPrefs.userId, page.toString())
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.recommended_for_you)

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
        mRecomendedPresenter.cancelAllAsync()
        mRecomendedPresenter.detachView()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RecomendedActivity::class.java)

        }
    }


    /*update product list*/
    private fun updateProductList(products: MutableList<RecomendedResponse.OffersBean>) {
        if (products.size>0) {
            txtNoProducts.visibility= View.GONE
            rvProductList.visibility= View.VISIBLE
            addDataToList(products)
        }
    }

    private fun addDataToList(products: MutableList<RecomendedResponse.OffersBean>) {

        mProductList.addAll(products)

        if (mAdapter==null){
            layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
            rvProductList.layoutManager = layoutManager
            mAdapter = RecomendedAdapter(this, mProductList,this)
            rvProductList.adapter = mAdapter
        }
        else{
            mAdapter?.notifyDataSetChanged()
        }
    }


    /*
    *
    * OnScroll Listener
    *
    * */

    private val recyclerViewOnScrollListener = object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(dy > 0) //check for scroll down
            {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (loading)
                {
                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                    {
                        loading = false;
                        //Log.v("...", "Last Item Wow !");
                        page+=1
                        getProductList()
                    }
                }
            }
        }
    }
}

