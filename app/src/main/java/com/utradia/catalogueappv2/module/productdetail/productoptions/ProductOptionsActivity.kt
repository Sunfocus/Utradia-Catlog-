package com.utradia.catalogueappv2.module.productdetail.productoptions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import com.androidadvance.topsnackbar.TSnackbar
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.AddToCartResponse
import com.utradia.catalogueappv2.model.ProductDetailResponse
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.buynowcheckout.BuyCheckoutActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity.Companion.isBuyNow
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity.Companion.mProductId
import com.utradia.catalogueappv2.module.productdetail.productpresenter.ProductOptionsPresenter
import com.utradia.catalogueappv2.module.productdetail.productpresenter.ProductOptionsView
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_productoptions.*
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductOptionsActivity :BaseActivity(), ProductOptionsView,OnColorSelected,OnSizeSelected{


    @Inject lateinit var mProductOptionsPresenter: ProductOptionsPresenter
    @Inject lateinit var mPrefs:PreferenceManager
    @Inject lateinit var mImageUtility: ImageUtility

    private lateinit var mType:String
    private var mQty:Int=0
    private var mAvailableQty:Int?=null
    private lateinit var mColorAdapter:ColorsAdapter
    private lateinit var mSizeAdapter: SizeAdapter
    private lateinit var offerDetails:ProductDetailResponse.OfferDetailsBean
    private lateinit var response: ProductDetailResponse

    private var mSubscriber: Subscription? = null

    override val layout: Int
        get() = R.layout.activity_productoptions

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
        mProductOptionsPresenter.injectDependencies(this)
        mProductOptionsPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        updateUI()

        initClicks()

        mSubscriber = Observable.interval(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    checkVariant()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        // Handle item selection
        return when (menuItem.itemId) {
            R.id.action_cart -> {
                startActivity(CartActivity.createIntent(this))
                true
            }
            R.id.action_search -> {
                startActivity(SearchProduct.createIntent(this,""))
                true
            }
            R.id.action_more->
            {
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
                            startActivity(MyAccountActivity.createIntent(this))
                            true
                        }
                        else ->
                            false
                    }
                }
                popup.show()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val itemCart = menu?.findItem(R.id.action_cart)
        val icon = itemCart?.icon as LayerDrawable
        if (!mPrefs.cartCount.equals("",true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)

        return super.onPrepareOptionsMenu(menu)

    }


    private fun initClicks() {
        imgPlus.setOnClickListener {
            if (mAvailableQty!=null) {
                if (mQty< mAvailableQty!!) {
                    mQty+=1
                    txtQuantity.text=mQty.toString()
                    response.selectedVariant.qty=mQty.toString()
                    checkQty()
                }
                else
                    mAppUtils.showToast(getString(R.string.qty_not_available))
            }
            else
                mAppUtils.showToast(getString(R.string.please_select_varient_first))
        }

        imgMinus.setOnClickListener {
            mQty-=1
            txtQuantity.text=mQty.toString()
            response.selectedVariant.qty=mQty.toString()
            checkQty()
        }

        btnConfirm.setOnClickListener {
            if (isBuyNow!!)
                buyNowCart()
            else
                addToCart() }
    }

    /*
        * check qty to enable disable btns
        * */
    private fun checkQty() {
        when (mQty) {
            1 -> {
                imgMinus.isEnabled=false
                imgMinus.alpha=.5f

                imgPlus.isEnabled=true
                imgPlus.alpha=1f
            }
            20 -> {
                imgMinus.isEnabled=true
                imgMinus.alpha=1f

                imgPlus.isEnabled=false
                imgPlus.alpha=.5f
            }
            else -> {
                imgMinus.isEnabled=true
                imgMinus.alpha=1f

                imgPlus.isEnabled=true
                imgPlus.alpha=1f
            }
        }
    }


    /*
    * updating UI from detail response*/
    private fun updateUI() {

        response=ProductDetailActivity.response!!
         offerDetails= ProductDetailActivity.response?.offer_details!!

        if (response.selectedVariant==null) {
            response.selectedVariant= ProductDetailResponse.SelectedVariantBean()
            response.selectedVariant.qty="1"
            response.selectedVariant.color=""
            response.selectedVariant.size=""
            response.selectedVariant.price=""
        }


        mType=offerDetails.variant_group_type

        mImageUtility.loadImage(offerDetails.image,imgProductImage)
        txtProName.text=offerDetails.title

        txtDiscountPrice.text = mPrefs.currencySymbol+" "+offerDetails.discounted_price

        txtPrice.text =mPrefs.currencySymbol+" "+ offerDetails.discount

        if(offerDetails.discounted_price.isNotEmpty()){

            txtDiscountPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            val discount=offerDetails.discount
            val discounted_price=offerDetails.discounted_price
            val mPercentage=(discount.toInt() - discounted_price.toInt())
            val mFinalPercentage=  (mPercentage*100)/discount.toInt()
            txtDiscountPercentage.text=mFinalPercentage.toString()+"%"
        }
        else{
            txtDiscountPrice.visibility=View.GONE
            txtDiscountPercentage.text="0%"
        }

        if (mType.equals("1",true))
        {
            llColorOptions.visibility=View.VISIBLE
            llSizeOptions.visibility=View.GONE

            if (offerDetails.colors!=null && offerDetails.colors.size>0){


              //  val layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL,false)
                val layoutManager = GridLayoutManager(this,6)
                rvColors.layoutManager = layoutManager

                 mColorAdapter = ColorsAdapter(this, offerDetails.colors, this)
               // val animatorAdapter = ScaleInAnimatorAdapter<ColorsAdapter.ViewHolder>(mColorAdapter, rvColors)
                rvColors.adapter = mColorAdapter
            }
        }
        if(!response.selectedVariant.color.equals("",true))
        {
            if (offerDetails.sizes!=null && offerDetails.sizes.size>0){
                llSizeOptions.visibility=View.VISIBLE

              //  val layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL,false)
                val layoutManager = GridLayoutManager(this,6)
                rvSizes.layoutManager = layoutManager

                mSizeAdapter = SizeAdapter(this, offerDetails.sizes, offerDetails.sizes,this)
               // val animatorAdapter = ScaleInAnimatorAdapter<SizeAdapter.ViewHolder>(mSizeAdapter, rvSizes)
                rvSizes.itemAnimator= ScaleInAnimator()
                rvSizes.adapter = mSizeAdapter

                /*filtering*/
                mSizeAdapter.filter.filter(response.selectedVariant.color_id)
            }
        }

        /*setting minus disabled*/
        imgMinus.isEnabled=false
        imgMinus.alpha=.5f

        imgPlus.isEnabled=true
        imgPlus.alpha=1f
    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.product_options)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mSubscriber!=null && mSubscriber!!.isUnsubscribed)
        mSubscriber!!.unsubscribe()

        mProductOptionsPresenter.cancelAllAsync()
        mProductOptionsPresenter.detachView()
    }

    override   fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.do_nothing, R.anim.slide_out_bottom)
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, ProductOptionsActivity::class.java)
        }

    }


    override fun onSizeSelected(sizes: List<ProductDetailResponse.OfferDetailsBean.SizesBean>,pos:Int) {

        updateVariant(sizes[pos].price,"")
        response.selectedVariant.size=sizes[pos].sizes
        response.selectedVariant.variant_id=sizes[pos].grp_id
        response.selectedVariant.price=sizes[pos].price
        mAvailableQty=sizes[pos].quantity.toInt()
        for (item in sizes)
        {
            item.isSelected=false
            if ( item.id.equals(sizes[pos].id,true))
                item.isSelected =true
        }

        mSizeAdapter.setItems(sizes)
        mSizeAdapter.notifyDataSetChanged()


    }

    override fun onColorSelected(id: String, pos: Int,name:String,grp_id:String,price:String,image:String) {

        updateVariant(price,image)
        response.selectedVariant.color=name
        response.selectedVariant.color_id=id
        response.selectedVariant.size=""
        response.selectedVariant.variant_id=grp_id
        response.selectedVariant.price=price
        for (item in offerDetails.sizes)
        {
            item.isSelected=false
        }
        for (item in offerDetails.colors)
        {
            item.isSelected=false
        }
        offerDetails.colors[pos].isSelected=true
        mColorAdapter.setItems(offerDetails.colors)
        mColorAdapter.notifyDataSetChanged()


        if (offerDetails.sizes!=null && offerDetails.sizes.size>0){

            llSizeOptions.visibility=View.VISIBLE
           // val layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL,false)
            val layoutManager = GridLayoutManager(this,6)
            rvSizes.layoutManager = layoutManager

            mSizeAdapter = SizeAdapter(this, offerDetails.sizes, offerDetails.sizes,this)
           // val animatorAdapter = ScaleInAnimatorAdapter<SizeAdapter.ViewHolder>(mSizeAdapter, rvSizes)
            rvSizes.itemAnimator= ScaleInAnimator()
            rvSizes.adapter = mSizeAdapter

            /*filtering*/
            mSizeAdapter.filter.filter(id)
        }


    }

    private fun checkVariant(){

        when {
            response.selectedVariant!!.color.equals("",true) -> disableConfirm()
            response.selectedVariant!!.size.equals("",true) -> disableConfirm()
            response.selectedVariant!!.qty.equals("0",true) -> disableConfirm()
            else -> enableConfirm()
        }

    }

    private fun enableConfirm(){
        btnConfirm.isEnabled=true
        btnConfirm.alpha=1f
    }
    private fun disableConfirm(){
        btnConfirm.isEnabled=false
        btnConfirm.alpha=.5f
    }

    private fun updateVariant(price: String,image: String){
        if (image.isNotEmpty())
        mImageUtility.loadImage(image,imgProductImage)
        if (price.isNotEmpty())
            if (price.isNotEmpty())
                txtPrice.text = mPrefs.currencySymbol+" "+price
            else
            {
                txtPrice.text = mPrefs.currencySymbol+" "+offerDetails.discount

                txtDiscountPrice.text =mPrefs.currencySymbol+" "+ offerDetails.discounted_price
            }


    }


    private fun addToCart() {

        if (mAppUtils.isInternetConnected)
        {
            if(response.selectedVariant !=null)
            {

                val price = if(response.selectedVariant!!.price.equals("",true))
                    response.offer_details!!.discount
                else
                    response.selectedVariant!!.price

                mProductOptionsPresenter.addToCart(mPrefs.userId,mProductId!!
                        , response.selectedVariant!!.qty
                        , response.offer_details!!.variant_group_type
                        , response.selectedVariant!!.variant_id
                        , response.selectedVariant!!.color
                        , response.selectedVariant!!.size,price)
            }
            else{
                if (response.offer_details!!.default_quantity.toInt()>=1)
                    mProductOptionsPresenter.addToCart(mPrefs.userId,mProductId!!
                            ,"1"
                            ,""
                            , "","","", response.offer_details!!.discount)
                else
                    mAppUtils.showToast(getString(R.string.qty_not_available))
            }
        }
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    private fun buyNowCart() {

        if (mAppUtils.isInternetConnected)
        {
            if(response.selectedVariant !=null)
            {

                val price = if(response.selectedVariant!!.price.equals("",true))
                    response.offer_details!!.discount
                else
                    response.selectedVariant!!.price

                mProductOptionsPresenter.buyNowCart(mPrefs.userId,mProductId!!
                        , response.selectedVariant!!.qty
                        , response.offer_details!!.variant_group_type
                        , response.selectedVariant!!.variant_id
                        , response.selectedVariant!!.color
                        , response.selectedVariant!!.size,price)
            }
            else{
                if (response.offer_details!!.default_quantity.toInt()>=1)
                    mProductOptionsPresenter.buyNowCart(mPrefs.userId,mProductId!!
                            ,"1"
                            ,""
                            , "","","", response.offer_details!!.discount)
                else
                    mAppUtils.showToast(getString(R.string.qty_not_available))
            }
        }
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    override fun onAddedToCart(checkNumberReponse: AddToCartResponse) {
      //  response.selectedVariant.size=""
        mPrefs.cartCount=checkNumberReponse.item_count
        invalidateOptionsMenu()
        snackBarSuccess(checkNumberReponse.success_message)
    }

    override fun onBuyNowCart(checkNumberReponse: AddToCartResponse) {
        response.selectedVariant.size=""
        startActivity(BuyCheckoutActivity.createIntent(this))
    }

    override fun onCartError(error_message: String) {
        snackBarError(error_message)
    }


    private fun snackBarSuccess(success_message: String) {
        val snackbar = TSnackbar.make(coordinator, success_message, TSnackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.WHITE)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.parseColor("#21bd2e"))

        val textView = snackbarView.findViewById<View>(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }
    private fun snackBarError(success_message: String) {
        val snackbar = TSnackbar.make(coordinator, success_message, TSnackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.WHITE)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.parseColor("#d2232a"))

        val textView = snackbarView.findViewById<View>(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }
}