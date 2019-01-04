package com.utradia.catalogueappv2.module.buynowcheckout

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View

import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.CheckoutData
import com.utradia.catalogueappv2.model.DiscountResponse
import com.utradia.catalogueappv2.model.ShipMethodsResponse
import com.utradia.catalogueappv2.module.addresses.alladdresses.AddressesActivity
import com.utradia.catalogueappv2.module.checkout.OnMethodClicked
import com.utradia.catalogueappv2.module.checkout.ShipMethodsAdapter
import com.utradia.catalogueappv2.module.payment.PaymentActivity
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_checkout.*
import javax.inject.Inject

class BuyCheckoutActivity:BaseActivity(),BuyCheckoutView,OnBuyItemClicked, OnMethodClicked {


    override fun onMethodClicked(pos: Int) {
        dialog.dismiss()
        response!!.product_data[mSelectedPosition].shipping_data.delivery_method=mShipResponse.shipping_price[pos].delivery_method
        response!!.product_data[mSelectedPosition].shipping_charges=mShipResponse.shipping_price[pos].price

        mAdapterBuy!!.notifyDataSetChanged()
        updatePrice(response!!)
    }


    @Inject lateinit var mBuyCheckoutPresenter: BuyCheckoutPresenter
    @Inject lateinit var mDialogsUtil: DialogsUtil
    @Inject lateinit var mPrefs:PreferenceManager
    private var mAdapterBuy: BuyCheckoutAdapter?=null
    private var mCartTotal:Double=0.00
    private var mShippingTotal:Double=0.00
    private var mGrandTotal:Double=0.00
    private var mSelectedPosition=0
    private lateinit var mShipResponse: ShipMethodsResponse
    private lateinit var dialog: Dialog

    private var mCodOption=false


    override val layout: Int
        get() = R.layout.activity_checkout

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
        mBuyCheckoutPresenter.injectDependencies(this)
        mBuyCheckoutPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()
        initClicks()

    }

    override fun onResume() {
        super.onResume()
        if (mAppUtils.isInternetConnected)
            mBuyCheckoutPresenter.getCartList(mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun initClicks() {
        txtChange.setOnClickListener { startActivity(AddressesActivity.createIntent(this)) }
        txtApplycode.setOnClickListener { applyPromoCode() }
        btnProceed.setOnClickListener {
            startActivity(PaymentActivity.createIntent(this,mGrandTotal,mCartTotal,mShippingTotal,"buy_now",mCodOption,"product")) }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBuyCheckoutPresenter.cancelAllAsync()
        mBuyCheckoutPresenter.detachView()
    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.checkout)
        toolbar.setNavigationOnClickListener { finish() }
    }

    companion object {
         var response :CheckoutData?=null
        fun createIntent(context: Context): Intent {
            return Intent(context, BuyCheckoutActivity::class.java)
        }
    }

    /*
    * view model methods...............................................................................
    * */
    override fun onCartItemsFound(response: CheckoutData) {
        BuyCheckoutActivity.response=response
        clParent.visibility=View.VISIBLE
        if (response.product_data.size>0) {
            rvCartItems.visibility=View.VISIBLE
            if (mAdapterBuy==null) {
                val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                rvCartItems.layoutManager = layoutManager

                mAdapterBuy = BuyCheckoutAdapter(this, response.product_data, this)
             //   val animatorAdapter = ScaleInAnimatorAdapter<BuyCheckoutAdapter.ViewHolder>(mAdapterBuy!!, rvCartItems)
                rvCartItems.adapter = mAdapterBuy
                updatePrice(response)
            }
            else{
                mAdapterBuy?.setItems(response.product_data)
                mAdapterBuy?.notifyDataSetChanged()
                updatePrice(response)
            }
        }
        checkItemsDelivery(response)
        updateShippingAddress(response.default_address)
    }

    private fun checkItemsDelivery(response: CheckoutData) {
        enableButton()
        for (item in response.product_data)
        {
            if (item.is_shipping_free==0 && item.shipping_data==null)
               disableButton()
        }
    }

    /*when cart Item Clicked*/
    override fun onShippingSelected(pos: Int) {
        if (mAppUtils.isInternetConnected)
            mBuyCheckoutPresenter.getShippingCharges(response!!.default_address.state
                    , response!!.default_address.city_name
                    , response!!.product_data[pos].product_weight,response!!.product_data[pos].client,response!!.product_data[pos].id)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    override fun onShipOptionFound(response: ShipMethodsResponse) {
        mShipResponse=response
        openShipMethodDialog(response)

    }

    private fun openShipMethodDialog(response: ShipMethodsResponse) {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dialog = mDialogsUtil.showDialog(this, R.layout.view_ship_methods)
        dialog.show()
        val rvShipMethods = dialog.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvShipMethods)
        rvShipMethods?.layoutManager = layoutManager
        val mShipAdapter = ShipMethodsAdapter(this,response.shipping_price, this)
        rvShipMethods?.adapter=mShipAdapter
    }


    private fun updateShippingAddress(default_address: CheckoutData.DefaultAddressBean?) {
        if (default_address!=null)
        {
            btnAddAddress.visibility=View.GONE
            txtName.text=default_address.full_name
            txtStreet.text=default_address.locality
            val text=default_address.city_name+", "+default_address.state_name
            txtRegion.text=text
            txtPhone.text=default_address.mobile_number
        }
        else
        {
            btnAddAddress.visibility=View.VISIBLE
        }
    }

    override fun onItemsNotFound(error: String) {
        mAppUtils.showErrorToast(error)
    }

    override fun onPromoCodeError(error: String) {
        mAppUtils.showErrorToast(error)
        grpDiscount.visibility=View.GONE
    }

    override fun onPromoCodeSuccess(response: DiscountResponse) {

        mAppUtils.showSuccessToast(response.success_message)
        grpDiscount.visibility=View.VISIBLE
        val discount=mPrefs.currencySymbol+" "+mAppUtils.round( response.discount,2)
        txtDiscount.text=discount
        if (response.discount_type.equals("Product",true))
        {
            mCartTotal -= response.discount
        }
        else if(response.discount_type.equals("Shipping",true))
        {
            mShippingTotal-=response.discount
        }
        updatePriceViews()
    }

    private fun updatePrice(response :CheckoutData){
        mCartTotal=0.0
        mShippingTotal=0.0

        for (item in response.product_data)
        {
            if (item.variant_group_type.equals("0", true)) {
                mCartTotal += if (!item.discounted_price.equals("", true))
                    item.cart_quantity.toInt() * item.discounted_price.toDouble()
                else
                    item.cart_quantity.toInt() * item.discount.toDouble()
            } else {
                if (item.prices[0].price != null && !item.prices[0].price.equals("", true))
                    mCartTotal += item.cart_quantity.toInt() * item.prices[0].price.toDouble()
                else {
                    if (!item.discounted_price.equals("", true))
                        mCartTotal += item.cart_quantity.toInt() * item.discounted_price.toDouble()
                    else
                        mCartTotal += item.cart_quantity.toInt() * item.discount.toDouble()
                }

            }

            if (item.shipping_charges!=null && !item.shipping_charges.equals("0.00",true))
                mShippingTotal+= item.shipping_charges.toDouble()
        }


        for (productItem in response.product_data)
        {

            if(productItem.cod=="1")
            {
                mCodOption=true
            }
            else
            {
                mCodOption=false
                break
            }
        }

        val shippingTotal=mPrefs.currencySymbol+" "+mAppUtils.round(mShippingTotal,2)
        txtShippingPrice.text=shippingTotal

        val total=mPrefs.currencySymbol+" "+mAppUtils.round(mCartTotal,2)
        txtItemPrice.text=total

        updatePriceViews()
    }

    private fun updatePriceViews(){
        mGrandTotal=mCartTotal+mShippingTotal
        val grandTotal=mPrefs.currencySymbol+" "+mAppUtils.round(mGrandTotal,2)
        txtTotal.text=grandTotal
        txtTotalPrice.text = grandTotal

    }
    /*
    apply promocode
    * */
    private fun applyPromoCode() {
        mAppUtils.hideSoftKeyboard(window.decorView.rootView)
        if(edtVoucherCode.text.toString().trim().isEmpty())
        {
            mAppUtils.showSnackBar(window.decorView.rootView,getString(R.string.please_enter_voucher_code))
        }
        else{
            if (mAppUtils.isInternetConnected)
                mBuyCheckoutPresenter.applyPromoCode(mPrefs.userId,edtVoucherCode.text.toString().trim()
                        ,mCartTotal.toString(),mShippingTotal.toString())
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }

    }

    private fun enableButton(){
        btnProceed.alpha=1f
        btnProceed.isEnabled=true
        txtApplycode.alpha=1f
        txtApplycode.isEnabled=true
    }

    private fun disableButton(){
        btnProceed.alpha=.3f
        btnProceed.isEnabled=false
        txtApplycode.alpha=.3f
        txtApplycode.isEnabled=false
    }
}