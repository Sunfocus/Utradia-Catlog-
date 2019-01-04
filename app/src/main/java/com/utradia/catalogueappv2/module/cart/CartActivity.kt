package com.utradia.catalogueappv2.module.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.CartResponse
import com.utradia.catalogueappv2.module.checkout.CheckoutActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.view_confirm.*
import kotlinx.android.synthetic.main.view_wishlist_confirm.*
import javax.inject.Inject

class CartActivity : BaseActivity(), CartView, OnCartClicked {
    override fun onItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(this,id,name,false))
    }

    override fun onItemsNotFound(error: String) {
        cartFailure()
    }

    override fun onWishListClicked(id: String, pos: Int) {
        this.pos = pos

        val dialog = mDialogsUtil.showDialog(this, R.layout.view_wishlist_confirm)
        dialog.show()

        dialog.txtAddToWish.setOnClickListener {
            dialog.dismiss()
            if (mAppUtils.isInternetConnected)
                mCartPresenter.moveToWishList(id, mPrefs.userId)
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
        dialog.txtNo.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onMovedToWishList(msg: String) {
        mAppUtils.showSuccessToast(msg)
        mResponse.product_data.removeAt(pos)
        mAdapter?.notifyDataSetChanged()
        updatePrice()
    }

    /*
    * View model method callbacks
    * */
    override fun onDeleteClicked(id: String, pos: Int) {

        this.pos = pos

        val dialog = mDialogsUtil.showDialog(this, R.layout.view_confirm)
        dialog.show()

        dialog.txtRemove.setOnClickListener {
            dialog.dismiss()
            if (mAppUtils.isInternetConnected)
                mCartPresenter.removeItem(id, mPrefs.userId)
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
        dialog.txtCancel.setOnClickListener {
            dialog.dismiss()
        }

    }

    /*called when item removed from cart
    * */
    override fun onItemRemoved() {
        mResponse.product_data.removeAt(pos)
        mAdapter?.notifyDataSetChanged()
        updatePrice()
    }


    override fun onQuantityChanged(id: String, pos: Int, qty: Int) {
        this.pos = pos
        this.qty = qty.toString()

        /*checking for quantity of variant ,if not available then check the quantity of main product*/
        val variantQty: String = if (mResponse.product_data[pos].prices.size>0&&mResponse.product_data[pos].prices[0].quantity.isNotEmpty())
            mResponse.product_data[pos].prices[0].quantity
        else
            mResponse.product_data[pos].default_quantity

        if (qty<=variantQty.toInt())
        {
            if (mAppUtils.isInternetConnected)
                mCartPresenter.updateQantity(id, qty.toString())
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
        else{
            mAppUtils.showToast(getString(R.string.requested_quantiy_not_avalbl))
        }
    }

    /*called when quantity updated in cart
       * */
    override fun onQuantityUpdated() {

        mResponse.product_data[pos].cart_quantity = qty
        mAdapter?.notifyDataSetChanged()
        updatePrice()
    }


    @Inject
    lateinit var mCartPresenter: CartPresenter
    @Inject
    lateinit var mDialogsUtil: DialogsUtil

    private lateinit var mResponse: CartResponse
    @Inject
    lateinit var mPrefs: PreferenceManager
    private var mAdapter: CartAdapter? = null
    private var pos: Int = 0
    private lateinit var qty: String
    private var mCartTotal: Double = 0.00

    override val layout: Int
        get() = R.layout.activity_cart

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)

        mCartPresenter.injectDependencies(this)
        mCartPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        /*setting toolbar*/
        setToolbar()
        initClicks()
    }

    private fun initClicks() {
        btnProceed.setOnClickListener {
            startActivity(CheckoutActivity.createIntent(this))
            //mAppUtils.showToast("In Progress")
        }
    }

    override fun onResume() {
        super.onResume()
        if (mAppUtils.isInternetConnected)
            mCartPresenter.getCartList(mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.cart)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCartPresenter.cancelAllAsync()
        mCartPresenter.detachView()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }

    }

    /*
    * View Call backs
    *
    * */

    override fun onCartItemsFound(response: CartResponse) {
        mResponse = response
        cartSuccess()
        if (response.product_data.size > 0) {
            if (mAdapter == null) {
                val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                rvCartItems.layoutManager = layoutManager

                mAdapter = CartAdapter(this, response.product_data, this)
                rvCartItems.itemAnimator= ScaleInAnimator()
               // val animatorAdapter = ScaleInAnimatorAdapter<CartAdapter.ViewHolder>(mAdapter!!, rvCartItems)
                rvCartItems.adapter = mAdapter
                updatePrice()
            } else {
                mAdapter?.setItems(response.product_data)
                mAdapter?.notifyDataSetChanged()
                updatePrice()
            }
        }
    }

    override fun onCartItemsError(error: String) {
        mAppUtils.showErrorToast(error)
    }

    private fun updatePrice() {
        mCartTotal = 0.0
        for (item in mResponse.product_data) {
            if (item.variant_group_type.equals("0", true)) {
                mCartTotal += if (!item.discounted_price.equals("", true))
                    item.cart_quantity.toInt() * item.discounted_price.toDouble()
                else
                    item.cart_quantity.toInt() * item.discount.toDouble()
            } else {
                mCartTotal += if (item.prices.size>0 &&item.prices[0].price != null && !item.prices[0].price.equals("", true))
                    item.cart_quantity.toInt() * item.prices[0].price.toDouble()
                else {
                    if (!item.discounted_price.equals("", true))
                        item.cart_quantity.toInt() * item.discounted_price.toDouble()
                    else
                        item.cart_quantity.toInt() * item.discount.toDouble()
                }
            }
        }
        val total = mPrefs.currencySymbol + " " + mAppUtils.round(mCartTotal, 2)
        txtEstimatedTotal.text = total
        txtTotal.text = total
        if(mResponse.product_data.size==0)
            cartFailure()
    }


    private fun cartSuccess() {
        txtNoItems.visibility = View.GONE
        rvCartItems.visibility = View.VISIBLE
        btnProceed.isEnabled = true
        btnProceed.alpha = 1f
    }

    private fun cartFailure() {
        rvCartItems.visibility = View.GONE
        txtNoItems.visibility = View.VISIBLE
        btnProceed.isEnabled = false
        btnProceed.alpha = .5f
    }
}