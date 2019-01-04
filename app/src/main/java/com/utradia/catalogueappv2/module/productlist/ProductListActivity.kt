package com.utradia.catalogueappv2.module.productlist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.FavouriteResponse
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.model.custom.FilterListModel
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.filter.FilterActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity

import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.PreferenceManager
import com.utradia.catalogueappv2.utils.bottomSheetDialog.SortBottomSheet
import kotlinx.android.synthetic.main.acitivity_productlist.*
import kotlinx.android.synthetic.main.layout_category_filter.*
import javax.inject.Inject

class ProductListActivity:BaseActivity(),ProductListView,OnProductClick,SortBottomSheet.OnSortInteraction {



    @Inject lateinit var mProductListPresenter: ProductListPresenter
    @Inject lateinit var mPrefs: PreferenceManager
    private lateinit var mCatId:String
    private lateinit var mpopularId:String
    private lateinit var msearchType:String
    private lateinit var mProductName:String
    private var mClientId=""



    var page:Int=1
    var prodPosition=0

    private var mAdapter:ProductListAdapter?=null
    private lateinit var layoutManager: LinearLayoutManager
    private  val mProductList=mutableListOf<ProductsByCatResponse.OffersBean>()

    private var loading = true
    var pastVisibleItems: Int = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    private val FILTER=457
    private val FAV_REQUEST=785

    private var sortTypeId:Int?=null


    val param=HashMap<String,String>()

    private var filterListModel:FilterListModel?=null

    private var bundle=Bundle()

    override val layout: Int
        get() = R.layout.acitivity_productlist

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
        mProductListPresenter.injectDependencies(this)
        mProductListPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)


        mCatId=intent.getStringExtra("cat_id_extra")
        mpopularId=intent.getStringExtra("popular_id_extra")
        msearchType=intent.getStringExtra("search_type")
        mProductName=intent.getStringExtra("cat_name_extra")
        mClientId=intent.getStringExtra("mClientId")
        setToolbar()


        mAdapter = ProductListAdapter(this, mProductList,this)
        inflateRecylerview(true)

        rvProductList.addOnScrollListener(recyclerViewOnScrollListener)
        getProductList()

        changeRecylerView()

        sortItem()
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode==FILTER)
        {
            filterListModel=  data!!.extras!!.getParcelable("resultData")!!

            applyFilter(filterListModel!!)
        }
        if(resultCode==Activity.RESULT_OK && requestCode==FAV_REQUEST)
        {
            if(data!!.hasExtra("position"))
            {
                val pos=data.getIntExtra("position",0)
                val offerData:ProductsByCatResponse.OffersBean= mProductList[pos]
                offerData.favourite=data.getIntExtra("favStatus",0)
                mProductList.set(pos,offerData)
                mAdapter!!.notifyItemChanged(pos)
            }
        }
    }

    private fun applyFilter(filterListModel: FilterListModel?) {

        if (filterListModel!!.brandList.isNotEmpty()) {
            param["brand_id"] = getBrandId(filterListModel.brandList)
        } else
        {
            param.remove("brand_id")
        }

        if (filterListModel.shopList.isNotEmpty()) {
            param["client_id"] = getShopId(filterListModel.shopList)
        } else
        {
            param.remove("client_id")
        }

        if (filterListModel.productRating != null) {
            param["rating"] = filterListModel.productRating!!.number
        } else
        {
            param.remove("rating")
        }

        if (filterListModel.catList!!.isNotEmpty()) {
            param["sub_cats"] = getCategoryId(filterListModel.catList!!)
        } else
        {
            param.remove("sub_cats")
        }

        if(filterListModel.priceList!=null)
        {
            param["min_price"]=filterListModel.priceList!!.min.toString()
            param["max_price"]=filterListModel.priceList!!.max.toString()
        } else
        {
            param.remove("min_price")
            param.remove("max_price")
        }

        mProductList.clear()
        page=1
        getProductList()


    }

    private fun getShopId(shopList: List<ProductsByCatResponse.ShopsBean>): String {
        val sb = StringBuilder()
        if (shopList.isNotEmpty())
            run {
                var prefix = ""
                for (serverId in shopList) {
                    if(serverId.isShopStatus)
                    {
                        sb.append(prefix)
                        prefix = ","
                        sb.append(serverId.id)
                    }
                }
            }
        return sb.toString()
    }

    private fun getBrandId(brandList: List<ProductsByCatResponse.BrandsBean>): String {

        val sb = StringBuilder()
        if (brandList.isNotEmpty())
            run {
                var prefix = ""
                for (serverId in brandList) {
                    if(serverId.isBrandStatus)
                    {
                        sb.append(prefix)
                        prefix = ","
                        sb.append(serverId.id)
                    }
                }
            }
        return sb.toString()
    }

    private fun sortItem() {


        tv_sort.setOnClickListener {

            if (sortTypeId != null)
            {
                bundle.putInt("sortType",sortTypeId!!)
            }
            val menuFragment = SortBottomSheet()
            menuFragment.arguments=bundle
            menuFragment.show(supportFragmentManager, menuFragment.tag)

        }

        tv_filter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("resultData", filterListModel)
            if (mCatId != "0")
            {
                bundle.putString("mCatId",mCatId)
            }
            intent.putExtras(bundle)

            startActivityForResult(intent,FILTER)
        }
    }


    private fun inflateRecylerview(viewStatus: Boolean) {
        //loading = false;

        layoutManager = if (viewStatus) LinearLayoutManager(this) else GridLayoutManager(this,2)

        rvProductList.layoutManager = layoutManager
        mAdapter?.setView(viewStatus)
        rvProductList.adapter = mAdapter

    }

    private fun getProductList() {
        if (mAppUtils.isInternetConnected)
        {
            when {
                msearchType.isNotEmpty() -> when(msearchType) {
                    "offerSearch"-> {
                        param.clear()
                        mProductListPresenter.getSearchOfferList(mClientId,page.toString(),mProductName,param)
                    }
                    "productSearch"-> {
                        param.clear()
                        mProductListPresenter.getSearchOfferId(mClientId,mCatId,page.toString(),mProductName,param)
                    }
                }
                mpopularId.isEmpty() -> mProductListPresenter.getProducts(mCatId, page.toString(),param)
                else -> mProductListPresenter.getMostPopularItems(mpopularId,mCatId, page.toString(),param)
            }
        }
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = mProductName

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
            }R.id.action_search -> {
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if(intent!!.extras!=null &&!intent.extras!!.isEmpty)
        {
            mCatId= intent.getStringExtra("cat_id_extra")
            mpopularId=intent.getStringExtra("popular_id_extra")
            msearchType=intent.getStringExtra("search_type")
            mProductName=intent.getStringExtra("cat_name_extra")
            mClientId=intent.getStringExtra("mClientId")

            supportActionBar!!.title = mProductName

            mProductList.clear()
            page=1
            getProductList()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mProductListPresenter.cancelAllAsync()
        mProductListPresenter.detachView()

    }

    companion object {
        fun createIntent(context: Context,cat_id: String,name:String,popular_id:String,searchType:String,client_id:String): Intent {
            return Intent(context, ProductListActivity::class.java)
                    .putExtra("cat_id_extra",cat_id)
                    .putExtra("cat_name_extra",name)
                    .putExtra("popular_id_extra",popular_id)
                    .putExtra("search_type",searchType)
                    .putExtra("mClientId",client_id)
        }

        fun createIntent(context: Context): Intent {
            return Intent(context, ProductListActivity::class.java)
        }
    }


    /*
    *
    * View Call backs
    *
    * */

    override fun onItemClick(position:Int,id: String,name: String) {
        startActivityForResult(ProductDetailActivity.createIntent(this,id,name,false,position),FAV_REQUEST)
    }

    override fun onProductsNotFound(error_message: String) {
        if (mProductList.isEmpty()) {
            txtNoProducts.visibility=View.VISIBLE
            rvProductList.visibility=View.GONE
        }
        else{
            mAppUtils.showSnackBar(window.decorView.rootView,"No more products")
            loading=false
        }
    }

    override fun onProductsFound(response: ProductsByCatResponse) {
        loading = true
        updateProductList(response.offers)

        if (filterListModel == null)
        {
            filterListModel= FilterListModel(response.brands,null,response.shops,null,null)
        }


    }

    private fun updateProductList(products: MutableList<ProductsByCatResponse.OffersBean>) {
        if (products.size>0) {
            txtNoProducts.visibility=View.GONE
            rvProductList.visibility=View.VISIBLE

            addDataToList(products)
        }
    }

    override fun onAddedToWishList(response: FavouriteResponse) {

        mProductList[prodPosition].favourite=1

        mAdapter!!.notifyItemChanged(prodPosition)
    }

    override fun onRemovedWishList(response: FavouriteResponse) {
        mProductList[prodPosition].favourite=0

        mAdapter!!.notifyItemChanged(prodPosition)
    }

    override fun onWishListError(error: String) {
        mAppUtils.showErrorToast(error)
    }

    override fun addToFavourite(id: String, position: Int) {

        if (mPrefs.isUserLoggedIn) {
            prodPosition=position
            mProductListPresenter.addToWishList(id,mPrefs.userId)
        }
        else
            startActivity(LoginActivity.createIntent(this, ValueConstants.PRODUCT_LIST, ValueConstants.HIDE_SKIP))

    }

    override fun removeFavouroite(id: String, position: Int) {
        if (mPrefs.isUserLoggedIn) {
            prodPosition = position
            mProductListPresenter.removeFromWishList(id, mPrefs.userId)
        }
        else
            startActivity(LoginActivity.createIntent(this, ValueConstants.PRODUCT_LIST, ValueConstants.HIDE_SKIP))
    }



    private fun addDataToList(products: MutableList<ProductsByCatResponse.OffersBean>) {

        mProductList.addAll(products)

        //mAdapter?.notifyItemRangeInserted(mProductList.size-products.size,mProductList.size)

        mAdapter?.notifyDataSetChanged()
    }



    /*
   *
   * OnScroll Listener
   *
   * */

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(dy > 0) //check for scroll down
            {
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (loading)
                {
                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                    {
                        loading = false
                        //Log.v("...", "Last Item Wow !");
                        page+=1
                        getProductList()
                    }
                }
            }
        }
    }


    override fun onfilterSort(sort: String) {

        when(sort)
        {
            getString(R.string.popularity)->
            {
                param["sort_by"] = "views"
                param["sort_type"] = "desc"
                sortTypeId=R.id.radioButton
            }
            getString(R.string.price_LtoH)->
            {
                param["sort_by"] = "discount"
                param["sort_type"] = "asc"
                sortTypeId=R.id.radioButton2
            }
            getString(R.string.price_HtoL)->
            {
                param["sort_by"] = "discount"
                param["sort_type"] = "desc"
                sortTypeId=R.id.radioButton3
            }
        }

        mProductList.clear()
        page=1
        getProductList()

    }


   private fun getCategoryId(stringArray:List<String>):String
    {
        val sb = StringBuilder()
        if (stringArray.isNotEmpty())
            run {
                var prefix = ""
                for (serverId in stringArray) {
                    sb.append(prefix)
                    prefix = ","
                    sb.append(serverId)
                }
            }
        return sb.toString()
    }





}