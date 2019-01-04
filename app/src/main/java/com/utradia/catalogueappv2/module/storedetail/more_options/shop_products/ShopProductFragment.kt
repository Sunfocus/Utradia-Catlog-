package com.utradia.catalogueappv2.module.storedetail.more_options.shop_products

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ClientProductResponse
import com.utradia.catalogueappv2.model.StoreDetailResponse
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity
import com.utradia.catalogueappv2.utils.*
import kotlinx.android.synthetic.main.fragment_catalogue.*
import javax.inject.Inject


class ShopProductFragment: Fragment() ,ShopProductView, TabLayout.OnTabSelectedListener,OnProductClicked{


    @Inject lateinit var mShopProductPresenter: ShopProductPresenter
    @Inject lateinit var mProgressBarHandler: ProgressBarHandler
    @Inject lateinit var mAppUtils: AppUtils
    @Inject lateinit var mImageUtility: ImageUtility
    @Inject lateinit var mPrefs: PreferenceManager

    private   val response= StoreDetailActivity.response
    private   val mClientId= StoreDetailActivity.response!!.client_data.id
    private  var mCatId:String=""
    private var page:Int=1
    private var loading = true
    var pastVisibleItems: Int = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0
    private  val mProductList: MutableList<ClientProductResponse.OffersBean> = ArrayList()
    private lateinit var  layoutManager: GridLayoutManager
    private  var  mAdapter: ShopProductAdapter?=null

    private var mShopId=mutableListOf<String>()


    /*View methods*/
    override fun showProgress() {
        mProgressBarHandler.showProgress()
    }

    override fun hideProgress() {
        mProgressBarHandler.hideProgress()
    }

    override fun onError(t: Throwable) {

    }

    override fun onException(message: String) {

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the view from fragmenttab1.xml
        val view = inflater.inflate(R.layout.fragment_catalogue, container, false)
        (activity?.application as UtradiaApplication).appComponent?.inject(this)
        mShopProductPresenter.injectDependencies(activity!!)
        mShopProductPresenter.attachView(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        initClicks()
        mAdapter=null
        tabCategories.addOnTabSelectedListener(this)
        rvClientProducts.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun initClicks() {
        txtFollow.setOnClickListener {
            if (mAppUtils.isInternetConnected)
                mShopProductPresenter.followClient(mPrefs.userId,mClientId)
            else
                mAppUtils.showSnackBar(activity!!.window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
        txtUnFollow.setOnClickListener {
            if (mAppUtils.isInternetConnected)
                mShopProductPresenter.unFollowClient(mPrefs.userId,mClientId)
            else
                mAppUtils.showSnackBar(activity!!.window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
    }

    private fun updateUI(){
        txtShopName.text=response!!.client_data.brand_name
        mImageUtility.loadImage(response.client_data.logo,imgShopLogo)
        if (response.client_data.is_following==1)
        {
            txtFollow.visibility=View.GONE
            txtUnFollow.visibility=View.VISIBLE
        }
        else{
            txtFollow.visibility=View.VISIBLE
            txtUnFollow.visibility=View.GONE
        }

        var pos=1

        tabCategories.addTab(tabCategories.newTab().setText("All"),0,true)
        mShopId.add("")
        for(item in response.categories){

            if(item.have_child.equals("1",true))
            {

                for (childItem in item.sub_categories)
                {
                    if(childItem.have_child.equals("1",true))
                    {
                        if(checkCategories(childItem.sub_categories))
                        {
                            mShopId.add(item.id)
                            tabCategories.addTab(tabCategories.newTab().setText(item.name),pos,false)
                            pos += 1
                            break
                        }
                    }
                }

            }
        }
        getProductList()
    }


    private fun checkCategories(sub_categories: MutableList<StoreDetailResponse.CategoriesBean.SubCategoriesBean>):Boolean
    {

        for (childcategory in sub_categories)
        {
            if(childcategory.have_child.equals("1",true))
            {
                checkCategories(childcategory.sub_categories)
            }
            else
            {
                if(childcategory.have_offers==1)
                {
                    return true
                }
            }
        }

        return false
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
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (loading)
                {
                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                    {
                        loading = false
                        page+=1
                        getProductList()
                    }
                }
            }
        }
    }

    override fun onProductsFound(response: ClientProductResponse) {
        loading=true
        updateProductList(response.offers)
    }

    override fun onProductsNotFound(error:String) {
        if (mProductList.isEmpty()) {
            txtNoProducts.visibility=View.VISIBLE
            rvClientProducts.visibility=View.GONE
        }
        else{
            mAppUtils.showSnackBar(activity!!.window.decorView.rootView,"No more products")
            loading=false
        }
    }

    override fun onClientFollowed() {
            txtFollow.visibility=View.GONE
            txtUnFollow.visibility=View.VISIBLE
    }

    override fun onClientUnFollowed() {
            txtFollow.visibility=View.VISIBLE
            txtUnFollow.visibility=View.GONE
    }

    override fun onFollowError(msg: String) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(activity!!,id,name,false))
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {


        rvClientProducts.recycledViewPool.clear()
        mProductList.clear()
        mAdapter!!.notifyDataSetChanged();
        mCatId = if (tab!!.position!=0)
            mShopId[tab.position]
        else
            ""
        page=1
        getProductList()
    }

    private fun getProductList() {
        if (mAppUtils.isInternetConnected)
            mShopProductPresenter.getClientOffers(mClientId
                    ,mCatId
                    ,page.toString())
        else
            mAppUtils.showSnackBar(activity!!.window.decorView.rootView, getString(R.string.toast_network_not_available))
    }



    /*update product list*/
    private fun updateProductList(products: MutableList<ClientProductResponse.OffersBean>) {
        if (products.size>0) {
            txtNoProducts.visibility=View.GONE
            rvClientProducts.visibility=View.VISIBLE
            addDataToList(products)
        }
    }

    private fun addDataToList(products: MutableList<ClientProductResponse.OffersBean>) {

        mProductList.addAll(products)

        if (mAdapter==null){
            layoutManager = UtradiaGridLayoutManager(activity, 2)
            rvClientProducts.layoutManager = layoutManager
            mAdapter = ShopProductAdapter(activity!!, mProductList,this)
            rvClientProducts.adapter = mAdapter
        }
        else{
            mAdapter?.notifyDataSetChanged()
        }
    }



}