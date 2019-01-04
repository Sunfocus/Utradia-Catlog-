package com.utradia.catalogueappv2.module.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.invitereferrals.invitereferrals.InviteReferralsApi
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.allcategories.AllCategoriesActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.discovery.DiscoveryActivity
import com.utradia.catalogueappv2.module.events.EventsActivity
import com.utradia.catalogueappv2.module.flashsales.FlashSalesActivity
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.loginsignup.signup.RegisterActivity
import com.utradia.catalogueappv2.module.notifications.NotificationsActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.productlist.ProductListActivity
import com.utradia.catalogueappv2.module.recomendedItems.RecomendedActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.module.shops.ShopsActivity
import com.utradia.catalogueappv2.module.userAboutSection.UserAboutActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator

import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HomeActivity : BaseActivity(), HomeView, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnHomeCatClicked {
    override fun onDeviceRegistered() {
        mPrefs.isDeviceRegisterIn = true
    }

    /*
    *
    * OnHomeCatClicked call backs
    *
    * */
    override fun omMostPopularClicked(id: String, name: String, cat_id: String) {
        //most popular
        startActivity(ProductListActivity.createIntent(this, cat_id, name, id, "", ""))
    }

    override fun onRecommendedItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(this, id, name, false))
    }

    override fun onFlashItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(this, id, name, true))
    }

    override fun onCategoryClicked(id: String, name: String) {
        startActivity(ProductListActivity.createIntent(this, id, name, "", "", ""))
    }

    override fun onAllCategoryClicked() {
        startActivity(AllCategoriesActivity.createIntent(this))
    }

    override fun onBannerClicked(id: String, name: String) {
        startActivity(ProductListActivity.createIntent(this, id, name, "", "", ""))
    }

    /*------------------------------------------------------------------ callback ends-----------------------------------------*/
    @Inject
    lateinit var mPrefs: PreferenceManager

    @Inject
    lateinit var homePresenter: HomePresenter

    lateinit var slideshow: TextView

    private var mAdapter: HomeCatAdapter? = null
    private var mRecommendedAdapter: RecommendedAdapter? = null
    private var mMostPopularAdapter: MostPopularAdapter? = null
    private var mFlashAdapter: FlashAdapter? = null
    private val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    private var variableName: CountDownTimer? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private val colors = arrayOf(R.drawable.shape1, R.drawable.shape2
            , R.drawable.shape3, R.drawable.shape4
            , R.drawable.shape5, R.drawable.shape6
            , R.drawable.shape7, R.drawable.shape8)

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgFB -> {
            }

            R.id.imgTwitter -> {
            }

            R.id.imgInsta -> {
            }

            R.id.imgYoutube -> {
            }

            R.id.imgAccount -> {
                if (mPrefs.isUserLoggedIn)
                    startActivity(MyAccountActivity.createIntent(this))
                else
                    startActivity(LoginActivity.createIntent(this, ValueConstants.HOME_SCREEN, ValueConstants.HIDE_SKIP))

            }

            R.id.imgShops -> {
                startActivity(ShopsActivity.createIntent(this))
            }

            R.id.txtFlashSales -> {
                startActivity(FlashSalesActivity.createIntent(this))
            }

            R.id.txtRecommendedTitle -> {
                startActivity(RecomendedActivity.createIntent(this))
            }

            R.id.imgEvents -> {
                //startActivity(Intent(this, DiscoveryActivity::class.java))
            }

            R.id.txtFirstNametxtFirstNam -> {
                if (mPrefs.isUserLoggedIn) {
                } else {
                    startActivity(LoginActivity.createIntent(this, ValueConstants.HOME_SCREEN, ValueConstants.HIDE_SKIP))
                }
            }
        }
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     *
     * @return true to display the item as the selected item
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        // close drawer when item is tapped
        when (item.itemId) {

            R.id.nav_home -> {
                drawerLayout.closeDrawers()
            }
            R.id.nav_notifications -> {
                startActivity(NotificationsActivity.createIntent(this))
                drawerLayout.closeDrawers()
            }
            R.id.nav_follow -> {
            }

            R.id.nav_friends -> {
                if (mPrefs.isUserLoggedIn)
                    InviteReferralsApi.getInstance(this).userDetails(mPrefs.name, mPrefs.email, null, 0, null, null)
                else
                    InviteReferralsApi.getInstance(this).userDetails("Yaw", "Yaw@gmail.com", null, 0, null, null)

                InviteReferralsApi.getInstance(this@HomeActivity).inline_btn(17168)
                drawerLayout.closeDrawers()
            }

            R.id.nav_about -> {
                startActivity(UserAboutActivity.createIntent(this, getString(R.string.about_url), getString(R.string.about_us)))
                drawerLayout.closeDrawers()
            }

            R.id.nav_terms -> {
                startActivity(UserAboutActivity.createIntent(this, getString(R.string.user_terms_url), getString(R.string.user_terms)))
                drawerLayout.closeDrawers()
            }

            R.id.nav_service -> {
                drawerLayout.closeDrawers()
            }

        }

        return true
    }

    /**
     * get layout to inflate
     */
    override val layout: Int
        get() = R.layout.activity_home

    override val views: List<View>?
        get() = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as UtradiaApplication).appComponent?.inject(this)

        homePresenter.injectDependencies(this)
        homePresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()

        navView.setNavigationItemSelectedListener(this)

        slideshow = MenuItemCompat.getActionView(navView.menu.findItem(R.id.nav_notifications)) as TextView

        initializeCountDrawer()
        iniClickSocial()
        initClickListener()
        updateUI()


        updateDeviceToken()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        onResume()
    }

    private fun updateDeviceToken() {
        if (!mPrefs.isDeviceRegisterIn) {
            if (mAppUtils.isInternetConnected) {
                homePresenter.registerToken(FirebaseInstanceId.getInstance().token!!)
            } else {
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
            }
        }

    }

    /*Updating Ui*/
    private fun updateUI() {
        val navigationView = findViewById(R.id.navView) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById<View>(R.id.txtFirstNametxtFirstNam) as TextView

        if (mPrefs.isUserLoggedIn) {
            navUsername.text = mPrefs.name
        } else {
            navUsername.text = "Login / Sign Up"
            navUsername.setOnClickListener(this)
        }
    }

    /*
    * setup toolbar
    * */
    @SuppressLint("WrongConstant")
    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setTitle("UTRADIA")
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(Gravity.START)
        }
    }

    override fun onResume() {
        super.onResume()

        if (mAppUtils.isInternetConnected) {
            homePresenter.getHomeData(mPrefs.userId)
        } else {

            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_cart_home, menu)
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
                startActivity(SearchProduct.createIntent(this, ""))
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

    override fun onDestroy() {
        super.onDestroy()
        if (variableName != null)
            variableName!!.cancel()
        homePresenter.cancelAllAsync()
        homePresenter.detachView()
    }

    /*
    * called when api data is received
    * */
    override fun onHomeDataReceived(response: HomeResponse) {

        updateBanners(response.banners)
        if (response.categories != null)
            updateCategories(response.categories)

        /*checking for recommended items*/
        if (response.recommended_items != null && response.recommended_items.size > 0)
            updateRecommendedItems(response.recommended_items)
        else
            txtRecommendedTitle.visibility = View.GONE

        /*checking for most popular items*/
        if (response.most_popular != null)
            updateMostPopular(response.most_popular)


        /*checking for flash items*/
        if (response.flash_sale != null && response.flash_sale.size > 0 && response.flash_sale[0].products.size > 0) {
            startCountDown(response.flash_sale[0].end_datetime)
            updateFlashSales(response.flash_sale[0].products)
        } else {
            llFlashSales.visibility = View.GONE
        }
        // startCountDown("2018-6-2 10:30:00")
    }

    /*
    *
    * update flash sale content*/
    private fun updateFlashSales(flash_sale: List<HomeResponse.FlashSaleBean.ProductsBean>) {

        llFlashSales.visibility = View.VISIBLE


        if (mFlashAdapter == null) {
            val layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
            rvFlashSales.layoutManager = layoutManager
            mFlashAdapter = FlashAdapter(this, flash_sale, this)
            rvFlashSales.itemAnimator = ScaleInAnimator()

            //  val animatorAdapter = ScaleInAnimatorAdapter<FlashAdapter.ViewHolder>(mFlashAdapter!!, rvFlashSales)
            rvFlashSales.adapter = mFlashAdapter
        } else {
            mFlashAdapter!!.setItems(flash_sale)
            mFlashAdapter!!.notifyDataSetChanged()
        }
    }
    /*
    *
    * updating most popular item view
    *
    **/
    private fun updateMostPopular(mostpopular_items: List<HomeResponse.MostPopularBean>) {
        txtMostPopular.visibility = View.VISIBLE

        rvMostPopular.visibility = View.VISIBLE


        if (mMostPopularAdapter == null) {
            val layoutManager = GridLayoutManager(this, 2)
            rvMostPopular.layoutManager = layoutManager
            mMostPopularAdapter = MostPopularAdapter(this, mostpopular_items, this)
            rvMostPopular.itemAnimator = ScaleInAnimator()
            //  val animatorAdapter = ScaleInAnimatorAdapter<MostPopularAdapter.ViewHolder>(mMostPopularAdapter!!, rvMostPopular)
            rvMostPopular.adapter = mMostPopularAdapter
        } else {
            mMostPopularAdapter!!.setItems(mostpopular_items)
            mMostPopularAdapter!!.notifyDataSetChanged()
        }
    }

    /*
    * updating recommended items
    *
    * */
    private fun updateRecommendedItems(recommended_items: List<HomeResponse.RecommendedItemsBean>) {
        txtRecommendedTitle.visibility = View.VISIBLE
        rvRecommended.visibility = View.VISIBLE
        if (mRecommendedAdapter == null) {
            val layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
            rvRecommended.layoutManager = layoutManager
            mRecommendedAdapter = RecommendedAdapter(this, recommended_items, this)
            rvRecommended.itemAnimator = ScaleInAnimator()
            //  val animatorAdapter = ScaleInAnimatorAdapter<RecommendedAdapter.ViewHolder>(mRecommendedAdapter!!, rvRecommended)
            rvRecommended.adapter = mRecommendedAdapter
        } else {
            mRecommendedAdapter!!.setItems(recommended_items)
            mRecommendedAdapter!!.notifyDataSetChanged()
        }
    }

    /*
    * updating home page categories
    * */
    private fun updateCategories(categories: List<HomeResponse.CategoriesBean>) {
        txtCatsTitle.visibility = View.VISIBLE
        rvCategories.visibility = View.VISIBLE


        if (mAdapter == null) {
            val gridLayoutManager = GridLayoutManager(this, 4)
            rvCategories.layoutManager = gridLayoutManager
            mAdapter = HomeCatAdapter(this, categories, colors, this)
            rvCategories.itemAnimator = ScaleInAnimator()
            //val animatorAdapter = ScaleInAnimatorAdapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>(mAdapter!!, rvCategories)
            rvCategories.adapter = mAdapter
        } else {
            mAdapter!!.setItems(categories)
            mAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onHomeDataFailed(error_message: String?) {

    }

    override fun showToolbar(): Boolean {
        return true
    }


    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }

    }

    private fun initializeCountDrawer() {
        //Gravity property aligns the text
        slideshow.gravity = Gravity.CENTER_VERTICAL
        slideshow.setTypeface(null, Typeface.BOLD)
        slideshow.setTextColor(ContextCompat.getColor(this, R.color.color_notification))
        slideshow.text = "1"

    }

    /*init clicks of side menu social buttons*/
    private fun iniClickSocial() {
        val menu = navView.menu
        val menuItem = menu.findItem(R.id.nav_follow_items)
        val actionView = MenuItemCompat.getActionView(menuItem)

        val fb = actionView.findViewById<ImageView>(R.id.imgFB)
        fb.setOnClickListener(this)

        val twitter = actionView.findViewById<ImageView>(R.id.imgTwitter)
        twitter.setOnClickListener(this)

        val insta = actionView.findViewById<ImageView>(R.id.imgInsta)
        insta.setOnClickListener(this)

        val youtube = actionView.findViewById<ImageView>(R.id.imgYoutube)
        youtube.setOnClickListener(this)

    }

    /*init clicks of view*/
    private fun initClickListener() {
        imgAccount.setOnClickListener(this)
        imgServices.setOnClickListener(this)
        imgShops.setOnClickListener(this)
        imgEvents.setOnClickListener(this)
        txtFlashSales.setOnClickListener(this)
        txtRecommendedTitle.setOnClickListener(this)
    }

    /*
    * update Banners
    * */
    private fun updateBanners(bannersBean: MutableList<HomeResponse.BannersBean>) {

        if (viewPagerAdapter == null) {
            viewPagerAdapter = ViewPagerAdapter(this, bannersBean, this)
            viewpager.adapter = viewPagerAdapter
            page_indicator.viewPager = viewpager
            page_indicator.notifyDataSetChanged()
        } else {
            viewPagerAdapter?.notifyDataSetChanged()
        }
    }

    private fun startCountDown(date: String) {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("Africa/Accra")
        val eventDate = dateFormat.parse(date)
        val currentDate = Date()
        val diff = eventDate.time - currentDate.time
        variableName = null
        variableName = object : CountDownTimer(diff, 1000) {
            override fun onFinish() {
                onResume()
            }

            override fun onTick(millisUntilFinished: Long) {
                txtHours.text = TimeUnit.MILLISECONDS.toHours(millisUntilFinished).toString()
                txtMinutes.text = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)))
                txtSeconds.text = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
            }
        }
        variableName!!.start()
    }

}
