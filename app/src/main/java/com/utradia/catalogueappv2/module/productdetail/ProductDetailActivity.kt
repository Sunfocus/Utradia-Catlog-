package com.utradia.catalogueappv2.module.productdetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.androidadvance.topsnackbar.TSnackbar
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.AddToCartResponse
import com.utradia.catalogueappv2.model.ProductDetailResponse
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.buynowcheckout.BuyCheckoutActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.productdetail.fragments.ShippingFragment
import com.utradia.catalogueappv2.module.productdetail.fragments.description.DescriptionFragment
import com.utradia.catalogueappv2.module.productdetail.fragments.overview.OverViewFragment
import com.utradia.catalogueappv2.module.productdetail.fragments.ratingreview.RatingReviewFragment
import com.utradia.catalogueappv2.module.productdetail.optioncolors.OptionColorsActivity
import com.utradia.catalogueappv2.module.productdetail.optionsizes.OptionSizeActivity
import com.utradia.catalogueappv2.module.productdetail.productoptions.ProductOptionsActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity
import com.utradia.catalogueappv2.utils.MessageEvent
import com.utradia.catalogueappv2.utils.PreferenceManager
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch
import io.branch.referral.BranchError
import io.branch.referral.SharingHelper
import io.branch.referral.util.LinkProperties
import io.branch.referral.util.ShareSheetStyle
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception
import java.net.URLEncoder
import javax.inject.Inject

class ProductDetailActivity : BaseActivity(), ProductDetailView, OverViewFragment.OnShareItem {
    override fun onBuyNowCart(checkNumberReponse: AddToCartResponse) {
        startActivity(BuyCheckoutActivity.createIntent(this))
    }

    override fun onAddedToCart(checkNumberReponse: AddToCartResponse) {

        mPrefs.cartCount = checkNumberReponse.item_count
        invalidateOptionsMenu()

        snackBarSuccess(checkNumberReponse.success_message)
    }

    override fun onCartError(error_message: String) {
        snackBarError(error_message)
    }

    @Inject
    lateinit var mProductDetailPresenter: ProductDetailPresenter
    @Inject
    lateinit var mPrefs: PreferenceManager

    private var itemPosition: Int? = null

    private var favStatus: Int? = null


    private var branchObject: BranchUniversalObject? = null

    private var linkProperties: LinkProperties? = null

    private var reviewSelection = false


    override val layout: Int
        get() = R.layout.activity_product_detail

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
        mProductDetailPresenter.injectDependencies(this)
        mProductDetailPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        setToolbar()

        initClicks()
        /*getting intent extra*/


        if (intent.hasExtra("prod_pos_extra")) {
            itemPosition = intent.getIntExtra("prod_pos_extra", 0)
        }

        mProductId = intent.getStringExtra("product_id_extra")

        if (intent.hasExtra("buy_now_extra")) {
            isBuyNow = intent.getBooleanExtra("buy_now_extra", false)
        } else {
            isBuyNow = false
        }


        if (intent.hasExtra("review_extra")) {
            reviewSelection = true
        }

        /*get product detail*/
        getProductDetail()
    }

    /*
    * Event bus method
    * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        /* Do something */
        tabProductFeatures.getTabAt(1)!!.select()
    }


    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun initClicks() {
        btnBuyNow.setOnClickListener {
            handleBuyNow()
        }

        imgStore.setOnClickListener {
            startActivity(StoreDetailActivity.createIntent(this, response?.offer_details?.user_data!!.id
                    , response?.offer_details?.user_data!!.logo, response?.offer_details?.user_data!!.brand_name))
        }

        imgWhatsApp.setOnClickListener {
            // shareProduct()
            if (response!!.offer_details.user_data.phone.isNotEmpty())
                openWhatsApp(response!!.offer_details.user_data.phone)
            else
                mAppUtils.showErrorToast(getString(R.string.whatsapp_error_msg))
        }
    }

    private fun openWhatsApp(phone: String) {

        val packageManager: PackageManager = packageManager
        val i = Intent(Intent.ACTION_VIEW);
        try {
            val url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(getString(R.string.share_msg_content, response!!.offer_details.user_data.name, "product") + branchObject!!.getShortUrl(this@ProductDetailActivity, linkProperties!!), "UTF-8")
            i.setPackage("com.whatsapp");
            i.data = Uri.parse(url);
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (ex: Exception) {
            mAppUtils.showErrorToast(getString(R.string.whatsapp_not_installed))
            ex.printStackTrace();
        }
    }

    override fun itemShare() {
        shareProduct()
    }

    private fun shareProduct() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, branchObject!!.getShortUrl(this@ProductDetailActivity, linkProperties!!))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        startActivity(Intent.createChooser(intent, "Share Link"))
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.getStringExtra("product_id_extra") != null) {
            /*getting intent extra*/
            mProductId = intent.getStringExtra("product_id_extra")
            ProductDetailActivity.mProductId = mProductId
            supportActionBar!!.title = intent.getStringExtra("product_name_extra")
            /*get product detail*/
            getProductDetail()
        }
    }

    private fun getProductDetail() {
        if (mAppUtils.isInternetConnected)
            mProductDetailPresenter.getProductDetails(mProductId!!, mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("product_name_extra")
        toolbar.setNavigationOnClickListener {

            setLikeStatus()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        setLikeStatus()
    }

    private fun setLikeStatus() {
        val returnIntent = Intent()
        returnIntent.putExtra("position", itemPosition)
        returnIntent.putExtra("favStatus", favStatus)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun onDestroy() {
        super.onDestroy()
        response = null
        isBuyNow = false
        mProductDetailPresenter.cancelAllAsync()
        mProductDetailPresenter.detachView()
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
                startActivity(SearchProduct.createIntent(this, ""))
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
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val itemCart = menu?.findItem(R.id.action_cart)
        val icon = itemCart?.icon as LayerDrawable
        if (!mPrefs.cartCount.equals("", true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)

        return super.onPrepareOptionsMenu(menu)

    }

    companion object {


        var response: ProductDetailResponse? = null

        var isBuyNow: Boolean? = null

        var mProductId: String? = null

        fun createIntent(context: Context, id: String, name: String, isBuyNow: Boolean): Intent {
            return Intent(context, ProductDetailActivity::class.java)
                    .putExtra("product_id_extra", id)
                    .putExtra("product_name_extra", name)
                    .putExtra("buy_now_extra", isBuyNow)

        }

        fun createIntent(context: Context, id: String, name: String, isBuyNow: Boolean, pos: Int): Intent {
            return Intent(context, ProductDetailActivity::class.java)
                    .putExtra("product_id_extra", id)
                    .putExtra("product_name_extra", name)
                    .putExtra("buy_now_extra", isBuyNow)
                    .putExtra("prod_pos_extra", pos)

        }

        fun createIntent(context: Context): Intent {
            return Intent(context, ProductDetailActivity::class.java)
        }
    }

    /*
    *
    * View callback methods
    *
    * */
    override fun onProductDetailFound(response: ProductDetailResponse) {
        ProductDetailActivity.response = response


        favStatus = response.offer_details.favourite

        branchObject = BranchUniversalObject()
                .setCanonicalIdentifier("item")
                .setCanonicalUrl("https://branch.io/deepviews")
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PRIVATE)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setTitle(Companion.response!!.offer_details.title)
                .setContentDescription(Companion.response!!.offer_details.title)
                .setContentImageUrl(Companion.response!!.offer_details.image)
                .addKeyWord(Companion.response!!.offer_details.id)


        linkProperties = LinkProperties()
                .addTag("myShareTag1")
                .addTag("myShareTag2")
                .setChannel("myShareChannel2")
                .setFeature("mySharefeature2")
                .setStage("Sharing Product")
                .setCampaign("Android campaign")
                .addControlParameter("\$android_deeplink_path", "custom/path/*")
                .addControlParameter("\$ios_url", "http://example.com/ios")
                .setDuration(100)

        tabProductFeatures.visibility = View.VISIBLE
        val tabsName = arrayOf(getString(R.string.overview), getString(R.string.description)
                , getString(R.string.rating_reviews)
                , getString(R.string.shipping_return))

        val adapter = FragmentAdapter(supportFragmentManager)

        val overViewFragment = OverViewFragment()
        val descriptionFragment = DescriptionFragment()
        val ratingReviewFragment = RatingReviewFragment()
        val shippingFragment = ShippingFragment()

        adapter.addFragment(overViewFragment, tabsName[0])
        adapter.addFragment(descriptionFragment, tabsName[1])
        adapter.addFragment(ratingReviewFragment, tabsName[2])
        adapter.addFragment(shippingFragment, tabsName[3])

        viewPager.adapter = adapter
        tabProductFeatures.setupWithViewPager(viewPager)


        if (reviewSelection) {
            tabProductFeatures.getTabAt(2)!!.select()
        }


        /*if (response.offer_details.quantity.toInt()==0)
        {
            btnBuyNow.text = getString(R.string.out_of_stock)
            btnBuyNow.isEnabled=false
            btnBuyNow.alpha=.5f
            txtProductOptions.isEnabled=false
        }*/

    }


    override fun onProductDetailNotFound(error_message: String) {
        mAppUtils.showErrorToast(error_message)
    }

    /*
    * handle buy now click
    * */
    private fun handleBuyNow() {
        if (mPrefs.isUserLoggedIn) {
            if (response?.offer_details?.variant_group_type!!.equals("1", true)) {
                handleSizeColor()
            } else if (response?.offer_details?.variant_group_type!!.equals("2", true)) {
                handleColors()
            } else if (response?.offer_details?.variant_group_type!!.equals("3", true)) {
                handleSizes()
            } else if (response?.offer_details?.variant_group_type!!.equals("0", true)) {
                if (isBuyNow!!)
                    buyNowCart()
                else
                    addToCart()
            }
        } else
            startActivity(LoginActivity.createIntent(this, ValueConstants.PRODUCT_DETAIL, ValueConstants.HIDE_SKIP))
    }

    private fun addToCart() {

        if (mAppUtils.isInternetConnected) {
            if (response?.selectedVariant != null) {

                val price = if (response?.selectedVariant!!.price.equals("", true))
                    response?.offer_details!!.discount
                else
                    response?.selectedVariant!!.price

                mProductDetailPresenter.addToCart(mPrefs.userId, mProductId!!
                        , response?.selectedVariant!!.qty
                        , response?.offer_details!!.variant_group_type
                        , response?.selectedVariant!!.variant_id
                        , response?.selectedVariant!!.color
                        , response?.selectedVariant!!.size, price)
            } else {
/*                if (response?.offer_details!!.default_quantity.toInt()>=1)*/
                mProductDetailPresenter.addToCart(mPrefs.userId, mProductId!!
                        , "1"
                        , ""
                        , "", "", "", response?.offer_details!!.discount)
                /*  else
                      mAppUtils.showToast(getString(R.string.qty_not_available))*/
            }
        } else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    private fun buyNowCart() {

        if (mAppUtils.isInternetConnected) {
            if (response?.selectedVariant != null) {

                val price = if (response?.selectedVariant!!.price.equals("", true))
                    response?.offer_details!!.discount
                else
                    response?.selectedVariant!!.price

                mProductDetailPresenter.buyNowCart(mPrefs.userId, mProductId!!
                        , response?.selectedVariant!!.qty
                        , response?.offer_details!!.variant_group_type
                        , response?.selectedVariant!!.variant_id
                        , response?.selectedVariant!!.color
                        , response?.selectedVariant!!.size, price)
            } else {
                /*    if (response?.offer_details!!.default_quantity.toInt()>=1)*/
                mProductDetailPresenter.buyNowCart(mPrefs.userId, mProductId!!
                        , "1"
                        , ""
                        , "", "", "", response?.offer_details!!.discount)
                /*   else
                       mAppUtils.showToast(getString(R.string.qty_not_available))*/
            }
        } else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun handleSizes() {
        if (response?.selectedVariant == null
                || response?.selectedVariant?.size.equals("", true)) {
            startActivity(OptionSizeActivity.createIntent(this))
            overridePendingTransition(R.anim.slide_in_up, R.anim.do_nothing)
        } else {
            if (isBuyNow!!)
                buyNowCart()
            else
                addToCart()
        }

    }

    private fun handleColors() {
        if (response?.selectedVariant == null
                || response?.selectedVariant?.color.equals("", true)) {
            startActivity(OptionColorsActivity.createIntent(this))
            overridePendingTransition(R.anim.slide_in_up, R.anim.do_nothing)
        } else {
            if (isBuyNow!!)
                buyNowCart()
            else
                addToCart()
        }
    }

    /*handle size color*/
    private fun handleSizeColor() {
        if (response?.selectedVariant == null
                || response?.selectedVariant?.color.equals("", true)
                || response?.selectedVariant?.size.equals("", true)) {
            startActivity(ProductOptionsActivity.createIntent(this))
            overridePendingTransition(R.anim.slide_in_up, R.anim.do_nothing)
        } else {
            if (isBuyNow!!)
                buyNowCart()
            else
                addToCart()
        }

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

    override fun itemFav(status: Int) {
        favStatus = status
    }


    /*  public static Spanned fromHtml(String html){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
         return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
      } else {
         return Html.fromHtml(html);
      }*/


}
