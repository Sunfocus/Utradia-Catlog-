package com.utradia.catalogueappv2.module.payment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.apps.norris.paywithslydepay.core.PayWithSlydepay
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.CheckoutData
import com.utradia.catalogueappv2.model.OrderPlacedResponse
import com.utradia.catalogueappv2.module.buynowcheckout.BuyCheckoutActivity
import com.utradia.catalogueappv2.module.checkout.CheckoutActivity
import com.utradia.catalogueappv2.module.mtnmoney.MTNActivity
import com.utradia.catalogueappv2.module.orderplaced.OrderPlacedActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_payment.*
import javax.inject.Inject

class PaymentActivity :BaseActivity(),PaymentView ,RadioGroup.OnCheckedChangeListener{

    @Inject lateinit var mPaymentPresenter: PaymentPresenter
    @Inject lateinit var mPrefs: PreferenceManager
    private var mGrandTotal:Double=0.00
    private var mCartTotal:Double=0.00
    private var mShippingTotal:Double=0.00
    private lateinit var mPayMode:String
    private lateinit var mPayMethod:String
    private lateinit var mRadioSelected:String
    private lateinit var mOrderType:String
    private lateinit var mPaymentStatus:String
    private lateinit var mOrderId:String
    private lateinit var mProductType:String
    private var mItemPos:Int=0
    private var mCodOption=false

    private val YOUR_REQUEST_CODE:Int=1001

    private lateinit var  title:String

    override val layout: Int
        get() = R.layout.activity_payment

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
        mPaymentPresenter.injectDependencies(this)
        mPaymentPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        getData()
        rdgPayment.setOnCheckedChangeListener(this)
        disableButton()
        initClicks()
    }

    /*
   * getting Intent Data
   * */
    private fun getData() {

        mProductType=intent.getStringExtra("prod_type_extra")

        if(intent.getStringExtra("prod_type_extra").equals("product"))
        {
            mCartTotal = intent.getDoubleExtra("cart_total_extra", 0.00)
            mShippingTotal = intent.getDoubleExtra("ship_total_extra", 0.00)
            mOrderType = intent.getStringExtra("order_type_extra")

            if(mOrderType.equals("cart"))
                title=CheckoutActivity.response!!.product_data[0].title
            else
                title=BuyCheckoutActivity.response!!.product_data[0].title

        }
        else
        {
            mPaymentStatus=intent.getStringExtra("payment_status_extra")
            mOrderId=intent.getStringExtra("order_id_extra")
            mItemPos=intent.getIntExtra("item_position_extra",0)
        }

        //common in both intent
        mGrandTotal = intent.getDoubleExtra("total_extra", 0.00)
        mCodOption = intent.getBooleanExtra("cod_option_extra", false)
        val grandTotal = mPrefs.currencySymbol + " " + mAppUtils.round(mGrandTotal, 2)
        txtTotal.text = grandTotal

        radioCashOnDelivery.visibility = if (mCodOption) View.VISIBLE else View.GONE


    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.payment)
        toolbar.setNavigationOnClickListener { finish() }
    }

    companion object {
        fun createIntent(context: Context, total: Double, mCartTotal: Double, mShippingTotal: Double,order_type:String,cod_option:Boolean,productType:String): Intent {
            return Intent(context, PaymentActivity::class.java)
                    .putExtra("total_extra",total)
                    .putExtra("cart_total_extra",mCartTotal)
                    .putExtra("ship_total_extra",mShippingTotal)
                    .putExtra("order_type_extra",order_type)
                    .putExtra("cod_option_extra",cod_option)
                    .putExtra("prod_type_extra",productType)
        }

   /*     Required: payment_mode, payment_method, order_id, payment_status('Completed', 'pending')
        Optional: payment_token, payment_common_id*/

        fun createIntent(context: Context, order_id:String,payment_status:String,totalAmount:Double,itemPosition:Int,cod_option:Boolean,productType:String): Intent {
            return Intent(context, PaymentActivity::class.java)
                    .putExtra("order_id_extra",order_id)
                    .putExtra("payment_status_extra",payment_status)
                    .putExtra("total_extra",totalAmount)
                    .putExtra("item_position_extra",itemPosition)
                    .putExtra("cod_option_extra",cod_option)
                    .putExtra("prod_type_extra",productType)
        }
    }

    private fun initClicks() {
        btnProceed.setOnClickListener {
            if (mAppUtils.isInternetConnected) {
               // disableButton()
                if(mProductType.equals("product"))
                {
                    processOrder()
                }
                else
                {
                    OrderPayment(mOrderId,mPaymentStatus)
                }

            }
            else {
                //enableButton()
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
            }
        }

    }


    /*View model ....................*/
    override fun onOrderPlaced(response: OrderPlacedResponse) {
        startActivity(OrderPlacedActivity.createIntent(this,response.orderId,response.insert_id,mOrderType))
    }

    override fun onOrderNotPlaced(error_message: String) {
        mAppUtils.showErrorToast(error_message)
    }

    override fun onSuccess(success: String) {
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
    }



    /*Initiate Pending Order Payment*/

    private fun OrderPayment(mOrderId: String, mPaymentStatus: String)
    {
        mPaymentPresenter.orderPaymnet(mOrderId,mPayMode,mPayMethod,mPaymentStatus)
    }



    /*
    *Initiate Order Processing
    */
    private fun processOrder() {
        if (mRadioSelected.equals("COD",true))
        {
            val productIds = StringBuilder("[")
            val shipmentCharges = StringBuilder("[")
            val shipmentType = StringBuilder("[")

            val addressId:String
            val items:List<CheckoutData.ProductDataBean>
            if (CheckoutActivity.response!=null)
            {
                 addressId=CheckoutActivity.response!!.default_address.id
                 items=CheckoutActivity.response!!.product_data
            }
           else{
                addressId=BuyCheckoutActivity.response!!.default_address.id
                items=BuyCheckoutActivity.response!!.product_data
            }


            for (i in items.indices) {
                productIds.append('"'+ items[i].cart_id+'"')
                shipmentCharges.append('"'+ items[i].shipping_charges+'"')
                shipmentType.append('"'+ items[i].shipping_data.delivery_method+'"')


                if (i < items.size - 1) {
                    productIds.append(",")
                    shipmentCharges.append(",")
                    shipmentType.append(",")

                }
            }
            productIds.append("]")
            shipmentCharges.append("]")
            shipmentType.append("]")

            mPaymentPresenter.placeOrder(mPrefs.userId,productIds.toString(),shipmentCharges.toString()
                    ,addressId,addressId,shipmentType.toString()
                    ,mCartTotal.toString(),mShippingTotal.toString(),mGrandTotal.toString()
                    ,mPayMode,"","","","","",mPayMethod,mOrderType)
        }
        else if(mRadioSelected.equals("PAS",true))
        {
            makeMtnPayment()
        }
        else if(mRadioSelected.equals("Slyde",true))
        {
            makeSlydePayment()
        }
    }

    private fun makeSlydePayment() {
//Performing transaction with provided ui
        PayWithSlydepay.Pay(this,title
                ,mGrandTotal,"description"
                ,"customerName","customerEmail"
                ,"orderCode","phoneNumber"
                ,YOUR_REQUEST_CODE);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == YOUR_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Payment was successful
                }
                Activity.RESULT_CANCELED -> {
                    //Payment failed
                }
                Activity.RESULT_FIRST_USER -> {
                    //Payment was cancelled by user
                }
            }


        }
    }

    private fun makeMtnPayment() {


        startActivity(MTNActivity.createIntent(this
                ,title,mGrandTotal))
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        when(checkedId)
        {
            R.id.radioMtn ->{
                mPayMode="PAS"
                mRadioSelected="PAS"
                mPayMethod="Pay Online"
                enableButton()
            }
            R.id.radioSlydepay ->{
                mPayMode="PAS"
                mRadioSelected="Slyde"
                mPayMethod="Pay Online"
                enableButton()
            }
            R.id.radioCashOnDelivery->{
                mPayMode="COD"
                mRadioSelected="COD"
                mPayMethod="Pay on Delivery"
                enableButton()
            }
        }
    }

    private fun enableButton(){
        btnProceed.isEnabled=true
        btnProceed.alpha=1f
    }

    private fun disableButton(){
        btnProceed.isEnabled=false
        btnProceed.alpha=.3f
    }
}