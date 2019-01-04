package com.utradia.catalogueappv2.module.orders

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.OrderListResponse
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.orderdetail.OrderDetailActivty
import com.utradia.catalogueappv2.module.payment.PaymentActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.view_dialog_popup.*
import javax.inject.Inject


class OrdersActivity : BaseActivity(), OrdersView, OnOrderClicked, TabLayout.OnTabSelectedListener {
    @Inject
    lateinit var mOrdersPresenter: OrdersPresenter
    @Inject
    lateinit var mPrefs: PreferenceManager

    private var mAdapter: OrdersListAdapter? = null

    private lateinit var response: OrderListResponse

    @Inject
    lateinit var mDialogsUtil : DialogsUtil

    private val PAYMENT_REQUEST=758

    //private lateinit var orderBeans: MutableList<OrderListResponse.OrdersBean>
    private var orderBeans = mutableListOf<OrderListResponse.DataBean.OrdersBean>()

    override val layout: Int
        get() = R.layout.activity_orders

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mOrdersPresenter.injectDependencies(this)
        mOrdersPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()


        tabOrderCategory.addOnTabSelectedListener(this)
        getOrders()
    }

    private fun getOrders() {
        if (mAppUtils.isInternetConnected)
            mOrdersPresenter.getOrderList(mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    override fun onDestroy() {
        super.onDestroy()
        mOrdersPresenter.cancelAllAsync()
        mOrdersPresenter.detachView()

    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, OrdersActivity::class.java)
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
        if (!mPrefs.cartCount.equals("", true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)


        return super.onPrepareOptionsMenu(menu)

    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.my_orders)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {

        updateItems(tab!!.position, response)
    }


    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    /*
   * update text in tab layout
   * */
    fun initTabs() {
        tabOrderCategory.addTab(tabOrderCategory.newTab().setText("All"))
        tabOrderCategory.addTab(tabOrderCategory.newTab().setText("Pending"))
        tabOrderCategory.addTab(tabOrderCategory.newTab().setText("Processing"))
        tabOrderCategory.addTab(tabOrderCategory.newTab().setText("Shipped"))
        tabOrderCategory.addTab(tabOrderCategory.newTab().setText("Completed"))
        tabOrderCategory.addTab(tabOrderCategory.newTab().setText("Cancelled"))
    }


    /*
    * View Model methods
    * */
    override fun onOrdersNotFound(error_message: String) {
        mAppUtils.showErrorToast(error_message)
        tabOrderCategory.visibility = View.GONE
        rvOrderList.visibility = View.GONE
        txtNoProducts.visibility = View.VISIBLE
    }


    /*handle payment request*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode==PAYMENT_REQUEST)
        {
            getOrders()

            tabOrderCategory.getTabAt(1)!!.customView!!.isSelected = true
        }

    }


    /*
    * Update Items in list
    * */
    private fun updateItems(pos: Int, response: OrderListResponse) {


        orderBeans.clear()


        when (pos) {
            0 -> orderBeans.addAll(response.data.all_orders)
            1 -> orderBeans.addAll(response.data.pending_orders)
            2 -> orderBeans.addAll(response.data.processing_orders)
            3 -> orderBeans.addAll(response.data.shipped_orders)
            4 -> orderBeans.addAll(response.data.completed_orders)
            5 -> orderBeans.addAll(response.data.cancelled_orders)
        }

        if (orderBeans.size > 0) {

            if (mAdapter != null) {
               // val animatorAdapter = ScaleInAnimatorAdapter<OrdersListAdapter.ViewHolder>(mAdapter!!, rvOrderList)
                rvOrderList.itemAnimator= ScaleInAnimator()
                rvOrderList.adapter = mAdapter

                mAdapter!!.notifyDataSetChanged()
            }
            rvOrderList.visibility = View.VISIBLE
            txtNoProducts.visibility = View.GONE

        } else {
            rvOrderList.visibility = View.GONE
            txtNoProducts.visibility = View.VISIBLE

        }

    }

    override fun onOrdersFound(response: OrderListResponse) {

        tabOrderCategory.visibility = View.VISIBLE
        rvOrderList.visibility = View.VISIBLE
        txtNoProducts.visibility = View.GONE

        this.response = response

        initTabs()

        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvOrderList.layoutManager = layoutManager1


        if (mAdapter == null) {
            mAdapter = OrdersListAdapter(this, orderBeans, this)
        }


        //orderBeans=response.pending_orders
        updateItems(0, response)
    }


    /*On order item click*/
    override fun onItemClicked(id: String, orderTotal:Double, position: Int, viewType:String) {

        when {
            viewType.equals("view") -> startActivity(OrderDetailActivty.createIntent(this,id))
            viewType.equals("payment") -> startActivityForResult(PaymentActivity.createIntent(this,id,"Completed",orderTotal,position,false,"order"),PAYMENT_REQUEST)
            else -> deletePopup(id)
        }

    }

    override fun onCancelOrder(message: String) {
        mAppUtils.showSuccessToast(message)
        getOrders()
    }

    override fun onErrorOccur(message: String) {
        mAppUtils.showErrorToast(message)
    }

    private fun deletePopup(id: String) {
        val dialog = mDialogsUtil.showDialog(this, R.layout.view_dialog_popup)
        dialog.show()

        dialog.txtMessage.text=getString(R.string.delete_msg)
        dialog.txtAction.text=getString(R.string.remove)
        dialog.txtCancel.text=getString(R.string.cancel)

        dialog.txtAction.setOnClickListener {
            dialog.dismiss()
            if (mAppUtils.isInternetConnected)
                mOrdersPresenter.onCancelOrder(id)
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
        dialog.txtCancel.setOnClickListener {
            dialog.dismiss()
        }

    }



}