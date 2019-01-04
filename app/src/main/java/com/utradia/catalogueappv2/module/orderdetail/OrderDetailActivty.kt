package com.utradia.catalogueappv2.module.orderdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.OrderDetailResponse
import com.utradia.catalogueappv2.module.orderdetail.item_review.RateReviewItemActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_order_detail_activty.*
import javax.inject.Inject

class OrderDetailActivty : BaseActivity(),OrdersDetailView,OnProductReview {



    @Inject
    lateinit var mOrderDetailPresenter: OrdersDetailPresenter
    @Inject
    lateinit var mPrefs: PreferenceManager

    private var mAdapter: OrderProductsListAdapter? = null


    private var mCartTotal:Double=0.00

    private var mGrandTotal:Double=0.00

    var orderId=""

    private var orderProducts = mutableListOf<OrderDetailResponse.OrderDataBean.OrderProductsBean>()

    override val layout: Int
        get() = R.layout.activity_order_detail_activty

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mOrderDetailPresenter.injectDependencies(this)
        mOrderDetailPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        setToolbar()

        orderId=intent.getStringExtra("orderId");

       // getOrderDetail(orderId)
    }


    override fun onResume() {
        super.onResume()

        getOrderDetail(orderId)
    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.order_detail)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mOrderDetailPresenter.cancelAllAsync()
        mOrderDetailPresenter.detachView()
    }




    companion object {
        fun createIntent(context: Context,orderId: String): Intent {
            return Intent(context, OrderDetailActivty::class.java)
                    .putExtra("orderId",orderId)
        }

    }


    private fun getOrderDetail(orderId: String) {
        if (mAppUtils.isInternetConnected)
        // mOrdersPresenter.getOrderList(mPrefs.userId,orderId)
            mOrderDetailPresenter.getOrderList(mPrefs.userId,orderId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    override fun orderDetail(response: OrderDetailResponse) {

        orderProducts.clear()
        orderProducts.addAll(response.order_data.order_products)

        mCartTotal=0.0


        val orderData:OrderDetailResponse.OrderDataBean =response.order_data
        val shipment_address:OrderDetailResponse.OrderDataBean.ShipmentAddressBean =response.order_data.shipment_address

        txtDeliveryDate.text=orderData.created
        txtUserName.text=orderData.shipment_address.full_name
        txtOrderId.text=orderData.order_id


        val text=shipment_address.city+", "+shipment_address.state
        txtUserAddress.text=text
        txtUserLocality.text=shipment_address.locality
        txtUserMobile.text=shipment_address.mobile_number



        for (item in response.order_data.order_products)
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
        }

        val total=mPrefs.currencySymbol+" "+mAppUtils.round(mCartTotal,2)
        txtItemPrice.text=total
        txtShippingPrice.text=mPrefs.currencySymbol+" "+mAppUtils.round(response.order_data.shipment_charges,2)

        mGrandTotal=mCartTotal+response.order_data.shipment_charges
        val grandTotal=mPrefs.currencySymbol+" "+mAppUtils.round(mGrandTotal,2)
        txtTotalPrice.text=grandTotal


        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvProductList.layoutManager = layoutManager1


        if (mAdapter == null) {
            mAdapter = OrderProductsListAdapter(this, orderProducts,response.order_data.order_status, this)
        }

        rvProductList.adapter=mAdapter

    }

    override fun itemReview(position: Int) {



        val productItem: OrderDetailResponse.OrderDataBean.OrderProductsBean = orderProducts[position]

        startActivity(RateReviewItemActivity.createIntent(this,productItem.id,productItem.cart_id,productItem.title,productItem.image))
    }

    override fun onErrorOccur(message: String) {

        mAppUtils.showErrorToast(message)
    }
}
