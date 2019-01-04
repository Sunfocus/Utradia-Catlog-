package com.utradia.catalogueappv2.module.wishlist

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.FavouriteResponse
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.model.WishListResponse
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_wishlist.*
import javax.inject.Inject

class WishListActivity :BaseActivity(),WishListView,OnWishListClicked {


    override fun onDeleteClicked(id: String,pos:Int) {
        position=pos
        if (mAppUtils.isInternetConnected)
            mWishListPresenter.removeFromWishList(id,mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    override fun onRemovedWishList(response: FavouriteResponse) {
        mAppUtils.showSuccessToast(response.success_message)

        mWishList.removeAt(position)
        mAdapter.setItems(mWishList)
        mAdapter.notifyDataSetChanged()

        if (mWishList.size==0)
        {
            txtNoItems.visibility=View.VISIBLE
            rvWishList.visibility=View.GONE
        }

    }


    override fun onWishListItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(this,id,name,false))
    }


    override fun onWishListItemsFound(wishListResponse: WishListResponse) {
            mWishList=wishListResponse.favourite_data
            updateList(mWishList)
    }

    override fun onWishListError(msg:String) {
        txtNoItems.visibility=View.VISIBLE
        rvWishList.visibility=View.GONE
    }

    @Inject lateinit var mWishListPresenter: WishListPresenter

    @Inject lateinit var mPrefs: PreferenceManager
    private var position:Int=0
    private lateinit var mAdapter: WishListAdapter
    private lateinit var  mWishList : MutableList<WishListResponse.FavouriteDataBean>

    override val layout: Int
        get() = R.layout.activity_wishlist
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
        mWishListPresenter.injectDependencies(this)
        mWishListPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        /*get Wish list*/
        getWishList()
    }

    private fun getWishList(){
        if (mAppUtils.isInternetConnected)
            mWishListPresenter.getWishList(mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.my_wishlist)

        toolbar.setNavigationOnClickListener({
            finish()
        })
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
        mWishListPresenter.cancelAllAsync()
        mWishListPresenter.detachView()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, WishListActivity::class.java)
        }
    }

    /*
    *
    * updating wishList items
    *
    * */
    private fun updateList(wishListResponse: List< WishListResponse.FavouriteDataBean>) {

        if (wishListResponse.isNotEmpty()) {
            txtNoItems.visibility=View.GONE
            rvWishList.visibility=View.VISIBLE
            val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
            rvWishList.layoutManager = layoutManager1

            mAdapter = WishListAdapter(this, wishListResponse,this)
           // val animatorAdapter = ScaleInAnimatorAdapter<WishListAdapter.ViewHolder>(mAdapter, rvWishList)
            rvWishList.itemAnimator= ScaleInAnimator()
            rvWishList.adapter = mAdapter
        }
        else
        {
            txtNoItems.visibility=View.VISIBLE
            rvWishList.visibility=View.GONE
        }
    }
}
