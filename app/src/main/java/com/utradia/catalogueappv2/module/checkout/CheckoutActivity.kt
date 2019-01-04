package com.utradia.catalogueappv2.module.checkout

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.CheckoutData
import com.utradia.catalogueappv2.model.DiscountResponse
import com.utradia.catalogueappv2.model.ShipMethodsResponse
import com.utradia.catalogueappv2.module.addresses.addaddress.AddAddressActivity
import com.utradia.catalogueappv2.module.addresses.alladdresses.AddressesActivity
import com.utradia.catalogueappv2.module.payment.PaymentActivity
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_checkout.*
import javax.inject.Inject

class CheckoutActivity : BaseActivity(), CheckoutView, OnItemClicked, OnMethodClicked {

    override fun onMethodClicked(pos: Int) {
        dialog.dismiss()
        response!!.product_data[mSelectedPosition].shipping_data.delivery_method = mShipResponse.shipping_price[pos].delivery_method
        response!!.product_data[mSelectedPosition].shipping_charges = mShipResponse.shipping_price[pos].price

        mAdapter!!.notifyDataSetChanged()
        updatePrice(response!!)
    }

    @Inject
    lateinit var mCheckoutPresenter: CheckoutPresenter
    @Inject
    lateinit var mDialogsUtil: DialogsUtil
    @Inject
    lateinit var mPrefs: PreferenceManager
    private var mAdapter: CheckoutAdapter? = null
    private var mCartTotal: Double = 0.00
    private var mShippingTotal: Double = 0.00
    private var mGrandTotal: Double = 0.00
    private var mCodOption=false
    private var mSelectedPosition = 0
    private lateinit var mShipResponse: ShipMethodsResponse
    private lateinit var dialog: Dialog

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
        mCheckoutPresenter.injectDependencies(this)
        mCheckoutPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()
        initClicks()
        txtApplycode.isEnabled = false
        txtApplycode.alpha = .2f
    }

    override fun onResume() {
        super.onResume()
        if (mAppUtils.isInternetConnected)
            mCheckoutPresenter.getCartList(mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun initClicks() {
        txtChange.setOnClickListener { startActivity(AddressesActivity.createIntent(this)) }
        txtApplycode.setOnClickListener { applyPromoCode() }
        btnAddAddress.setOnClickListener { startActivity(AddAddressActivity.createIntent(this, "", "0")) }
        btnProceed.setOnClickListener {
            startActivity(PaymentActivity.createIntent(this, mGrandTotal
                    , mCartTotal, mShippingTotal, "cart",mCodOption,"product"))
        }


        edtVoucherCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (edtVoucherCode.text.trim().isNotEmpty()) {
                    txtApplycode.isEnabled = true
                    txtApplycode.alpha = 1f
                } else {
                    txtApplycode.isEnabled = false
                    txtApplycode.alpha = .2f
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mCheckoutPresenter.cancelAllAsync()
        mCheckoutPresenter.detachView()
    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.checkout)
        toolbar.setNavigationOnClickListener { finish() }
    }

    companion object {
        var response: CheckoutData? = null
        fun createIntent(context: Context): Intent {
            return Intent(context, CheckoutActivity::class.java)
        }
    }


    /*
    * view model methods...............................................................................
    * */
    override fun onCartItemsFound(response: CheckoutData) {
        CheckoutActivity.response = response
        clParent.visibility = View.VISIBLE
        if (response.product_data.size > 0) {
            rvCartItems.visibility = View.VISIBLE
            if (mAdapter == null) {
                val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                rvCartItems.layoutManager = layoutManager

                mAdapter = CheckoutAdapter(this, response.product_data, this)
                //   val animatorAdapter = ScaleInAnimatorAdapter<BuyCheckoutAdapter.ViewHolder>(mAdapter!!, rvCartItems)
                rvCartItems.adapter = mAdapter
                updatePrice(response)


            } else {
                mAdapter?.setItems(response.product_data)
                mAdapter?.notifyDataSetChanged()
                updatePrice(response)
            }
        }
        checkItemsDelivery(response)
        updateShippingAddress(response.default_address)
    }

    private fun checkItemsDelivery(response: CheckoutData) {
        enableButton()
        for (item in response.product_data) {
            if (item.is_shipping_free == 0 && item.shipping_data.delivery_method == null)
                disableButton()
        }
    }

    /*when cart Item Clicked*/
    override fun onShippingSelected(pos: Int) {
        mSelectedPosition = pos
        if (mAppUtils.isInternetConnected) {
            if (response!!.default_address.id != null) {
                mCheckoutPresenter.getShippingCharges(response!!.default_address.state
                        , response!!.default_address.city_name
                        , response!!.product_data[pos].product_weight
                        , response!!.product_data[pos].client, response!!.product_data[pos].id)
            }
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.select_address))
        } else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    override fun onShipOptionFound(response: ShipMethodsResponse) {
        mShipResponse = response
        openShipMethodDialog(response)

    }

    private fun openShipMethodDialog(response: ShipMethodsResponse) {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dialog = mDialogsUtil.showDialog(this, R.layout.view_ship_methods)
        dialog.show()
        val rvShipMethods = dialog.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvShipMethods)
        rvShipMethods?.layoutManager = layoutManager
        val mShipAdapter = ShipMethodsAdapter(this, response.shipping_price, this)
        rvShipMethods?.adapter = mShipAdapter
    }


    private fun updateShippingAddress(default_address: CheckoutData.DefaultAddressBean?) {
        if (default_address != null && !default_address.id.isNullOrBlank()) {
            rlShippingAddress.visibility = View.VISIBLE
            btnAddAddress.visibility = View.GONE

            txtName.text = default_address.full_name
            txtStreet.text = default_address.locality
            val text = default_address.city_name + ", " + default_address.state_name
            txtRegion.text = text
            txtPhone.text = default_address.mobile_number
        } else {
            rlShippingAddress.visibility = View.GONE
            btnAddAddress.visibility = View.VISIBLE
        }
    }

    override fun onItemsNotFound(error: String) {
        mAppUtils.showErrorToast(error)
    }

    override fun onPromoCodeError(error: String) {
        mAppUtils.showErrorToast(error)
        grpDiscount.visibility = View.GONE
    }

    override fun onPromoCodeSuccess(response: DiscountResponse) {

        mAppUtils.showSuccessToast(response.success_message)
        grpDiscount.visibility = View.VISIBLE
        val discount = mPrefs.currencySymbol + " " + mAppUtils.round(response.discount, 2)
        txtDiscount.text = discount
        if (response.discount_type.equals("Product", true)) {
            mCartTotal -= response.discount
        } else if (response.discount_type.equals("Shipping", true)) {
            mShippingTotal -= response.discount
        }
        updatePriceViews()
    }

    private fun updatePrice(response: CheckoutData) {
        mCartTotal = 0.0
        mShippingTotal = 0.0

        for (item in response.product_data) {
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

            if (item.shipping_charges != null && !item.shipping_charges.equals("0.00", true))
                mShippingTotal += item.shipping_charges.toDouble()
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



        val shippingTotal = mPrefs.currencySymbol + " " + mAppUtils.round(mShippingTotal, 2)
        txtShippingPrice.text = shippingTotal

        val total = mPrefs.currencySymbol + " " + mAppUtils.round(mCartTotal, 2)
        txtItemPrice.text = total

        updatePriceViews()
    }

    private fun updatePriceViews() {
        mGrandTotal = mCartTotal + mShippingTotal
        val grandTotal = mPrefs.currencySymbol + " " + mAppUtils.round(mGrandTotal, 2)
        txtTotal.text = grandTotal
        txtTotalPrice.text = grandTotal
    }

    /*
    apply promocode
    * */
    private fun applyPromoCode() {
        mAppUtils.hideSoftKeyboard(window.decorView.rootView)
        if (edtVoucherCode.text.toString().trim().isEmpty()) {
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.please_enter_voucher_code))
        } else {
            if (mAppUtils.isInternetConnected)
                mCheckoutPresenter.applyPromoCode(mPrefs.userId, edtVoucherCode.text.toString().trim()
                        , mCartTotal.toString(), mShippingTotal.toString())
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }

    }

    private fun enableButton() {
        btnProceed.alpha = 1f
        btnProceed.isEnabled = true
    }

    private fun disableButton() {
        btnProceed.alpha = .3f
        btnProceed.isEnabled = false
    }


}