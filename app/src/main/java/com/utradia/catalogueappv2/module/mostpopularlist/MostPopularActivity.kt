package com.utradia.catalogueappv2.module.mostpopularlist

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import com.utradia.catalogueappv2.utils.bottomSheetDialog.BaseBottomSheetDialog
import com.utradia.catalogueappv2.utils.bottomSheetDialog.SortBottomSheet
import kotlinx.android.synthetic.main.activity_mostpopular.*
import kotlinx.android.synthetic.main.layout_category_filter.*
import javax.inject.Inject


class MostPopularActivity:BaseActivity(),MostPopularView,OnMostPopularClicked {


    override fun onItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(this,id,name,false))

    }

    override fun onOffersNotFound(error_message: String) {
        if (mProductList.isEmpty()) {
            txtNoProducts.visibility=View.VISIBLE
            rvProductList.visibility=View.GONE
        }
        else{
            mAppUtils.showSnackBar(window.decorView.rootView,"No more products")
            loading=false
        }

    }

    override fun onOffersFound(response: ProductsByCatResponse) {
        loading=true
        updateProductList(response.offers)
    }

    @Inject
    lateinit var mMostPopularPresenter: MostPopularPresenter
    @Inject lateinit var mPrefs: PreferenceManager
    private  var mAdapter: MostPopularAdapterList?=null
    var page:Int=1
    private lateinit var mCatId:String
    private lateinit var mPopularId:String
    private var loading = true
    var pastVisibleItems: Int = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    private  val mProductList: MutableList<ProductsByCatResponse.OffersBean> = ArrayList()
    private lateinit var  layoutManager: androidx.recyclerview.widget.LinearLayoutManager

    override val layout: Int
        get() = R.layout.activity_mostpopular

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
        mMostPopularPresenter.injectDependencies(this)
        mMostPopularPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        mCatId=intent.getStringExtra("cat_id_extra")
        mPopularId=intent.getStringExtra("popular_id_extra")
        getProductList()
        rvProductList.addOnScrollListener(recyclerViewOnScrollListener)


        changeRecylerView()

        sortItem()
    }

    private fun sortItem() {


        tv_sort.setOnClickListener {
            val menuFragment = SortBottomSheet()
            menuFragment.show(supportFragmentManager, menuFragment.tag)

        }
    }


    private fun changeRecylerView() {
        iv_grid_view.setOnClickListener {

            inflateRecylerview(false)

            iv_grid_view.visibility=View.GONE
            iv_list_view.visibility=View.VISIBLE
        }

        iv_list_view.setOnClickListener{
            inflateRecylerview(true)

            iv_grid_view.visibility=View.VISIBLE
            iv_list_view.visibility=View.GONE
        }
    }

    private fun inflateRecylerview(viewStatus: Boolean) {

        //loading = false;

        layoutManager = if (viewStatus) LinearLayoutManager(this) else GridLayoutManager(this,2)

        rvProductList.layoutManager = layoutManager
        mAdapter?.setView(viewStatus)
        rvProductList.setAdapter(mAdapter)

    }

    private fun getProductList() {
        if (mAppUtils.isInternetConnected)
            mMostPopularPresenter.getMostPopularItems(mPopularId,mCatId, page.toString())
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("cat_name_extra")

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
        mMostPopularPresenter.cancelAllAsync()
        mMostPopularPresenter.detachView()
    }

    companion object {
        fun createIntent(context: Context, cat_id: String, name:String,popular_id:String): Intent {
            return Intent(context, MostPopularActivity::class.java)
                    .putExtra("cat_id_extra",cat_id)
                    .putExtra("cat_name_extra",name)
                    .putExtra("popular_id_extra",popular_id)
        }
    }


    /*update product list*/
    private fun updateProductList(products: MutableList<ProductsByCatResponse.OffersBean>) {
        if (products.size>0) {
            txtNoProducts.visibility=View.GONE
            rvProductList.visibility=View.VISIBLE
            addDataToList(products)
        }
    }

    private fun addDataToList(products: MutableList<ProductsByCatResponse.OffersBean>) {

        mProductList.addAll(products)

        if (mAdapter==null){
            mAdapter = MostPopularAdapterList(this, mProductList,this)
            inflateRecylerview(true)
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

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
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