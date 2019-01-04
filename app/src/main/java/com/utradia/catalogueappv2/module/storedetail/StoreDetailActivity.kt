package com.utradia.catalogueappv2.module.storedetail

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.view.*
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.interfaces.OnDialogButtonClickListener
import com.utradia.catalogueappv2.model.StoreDetailResponse
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.module.storedetail.more_options.InfoActivity
import com.utradia.catalogueappv2.module.storedetail.more_options.catalogue.CatalogueListActivity
import com.utradia.catalogueappv2.module.storedetail.more_options.shop_products.ShopProductFragment
import com.utradia.catalogueappv2.module.storedetail.more_options.location.LocationActivity
import com.utradia.catalogueappv2.module.storedetail.more_options.openhours.OpenHoursActivity
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.LinkProperties
import kotlinx.android.synthetic.main.activity_store_detail.*
import java.lang.Exception
import java.net.URLEncoder
import javax.inject.Inject



class StoreDetailActivity :BaseActivity(),StoreDetailView , OnDialogButtonClickListener {

    override fun onClientDataFound(response: StoreDetailResponse) {

        StoreDetailActivity.response=response
        updateUI(response)

        supportFragmentManager!!.beginTransaction().add(R.id.shop_frameLayout, ShopProductFragment()).commit()

    }

    private fun updateUI(response: StoreDetailResponse) {

        branchObject = BranchUniversalObject()
                .setCanonicalIdentifier("client")
                .setCanonicalUrl("https://branch.io/deepviews")
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PRIVATE)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setTitle(response.client_data.name)
                .setContentDescription(response.client_data.name)
                .setContentImageUrl(response.client_data.logo)
                .addKeyWord(response.client_data.id)


        linkProperties = LinkProperties()
                .addTag("myShareTag1")
                .addTag("myShareTag2")
                .setChannel("myShareChannel2")
                .setFeature("mySharefeature2")
                .setStage("10")
                .setCampaign("Android campaign")
                .addControlParameter("\$android_deeplink_path", "custom/path/*")
                .addControlParameter("\$ios_url", "http://example.com/ios")
                .setDuration(100)

    }

    override fun onClientDataNotFound(error_message: String) {
        mAppUtils.showErrorToast(error_message)
    }

    @Inject lateinit var mStoreDetailPresenter: StoreDetailPresenter
    @Inject lateinit var mPrefs:PreferenceManager
    @Inject lateinit var mImageUtility: ImageUtility
    var mDialogType:Int=0

    private var branchObject: BranchUniversalObject? = null

    private var linkProperties:LinkProperties?=null

    private lateinit var mClientId:String

    override val layout: Int
        get() = R.layout.activity_store_detail

    override fun showToolbar(): Boolean {
        return false
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mStoreDetailPresenter.injectDependencies(this)
        mStoreDetailPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        /*getting intent data*/
        getIntentData()

        //initClicks()
        initClicks()
        /*get Store Data*/
        getStoreData()

        /*set toolbar layout*/
        settingToolbar()

    }

    private fun settingToolbar() {

        if(mPrefs.cartCount.isNotEmpty())
        {
            tv_msg_count.visibility=View.VISIBLE

            if(mPrefs.cartCount.toInt()>99)
                tv_msg_count.text= "99+"
            else
                tv_msg_count.text= mPrefs.cartCount
        }
        else
            tv_msg_count.visibility=View.GONE


        imgCart.setOnClickListener {
            startActivity(CartActivity.createIntent(this))
        }

        imgSearch.setOnClickListener {
            startActivity(SearchProduct.createIntent(this,mClientId))
        }
        imgHome.setOnClickListener {
            startActivity(HomeActivity.createIntent(this))
        }


    }


    private fun initClicks() {
        imgBack.setOnClickListener { finish() }
        imgCall.setOnClickListener { mStoreDetailPresenter.checkCallPermissions() }
        imgWhatsApp.setOnClickListener {
            if(response!!.client_data.phone.isNotEmpty())
                openWhatsApp(response!!.client_data.phone)
            else
                mAppUtils.showErrorToast(getString(R.string.whatsapp_error_msg))
        }
        imgShare.setOnClickListener {
            shareShopDetail()

        //    openEmailIntent()
        }
        imgMore.setOnClickListener {

            val dialog = BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                    .setMode(BottomSheetBuilder.MODE_LIST)
                    .setMenu(R.menu.menu_more)
                    .expandOnStart(true)           // expand the dialog automatically:
                    .setIconTintColorResource(R.color.unchecked_grey_state)   // tint the menu icons:
                    .setItemClickListener { item ->
                        val id = item.itemId
                        when (id) {
                            R.id.menu_catalogue -> {
                                startActivity(Intent(this, CatalogueListActivity::class.java).putExtra("client_id",mClientId))
                               }
                            R.id.menu_opnHour -> {
                                startActivity(Intent (this,OpenHoursActivity::class.java))
                            }
                            R.id.menu_location -> {
                                startActivity(Intent (this,LocationActivity::class.java))
                            }
                            R.id.menu_shpInfo -> {
                                startActivity(Intent (this,InfoActivity::class.java))
                            }
                            R.id.menu_email -> {
                                if (response!!.locations!=null)
                                    openEmailIntent()
                            }
                            R.id.menu_share -> {
                                if (response!!.locations!=null)
                                    shareShopDetail()
                            }
                        }
                    }
                    .createDialog()
                dialog.show()

        }
    }

    private fun shareShopDetail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,branchObject!!.getShortUrl(this@StoreDetailActivity, linkProperties!!))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        startActivity(Intent.createChooser(intent, "Share Link"))
    }

    private fun getIntentData() {
        mClientId=intent.getStringExtra("client_id_extra")
        txtTitle.text=intent.getStringExtra("brand_extra")

        mImageUtility.loadImage(intent.getStringExtra("image_extra"),imgShopBanner)

    }

    private fun getStoreData() {
        if (mAppUtils.isInternetConnected)
            mStoreDetailPresenter.getClientData(mClientId,mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    override fun onDestroy() {
        super.onDestroy()
        response=null
        mStoreDetailPresenter.cancelAllAsync()
        mStoreDetailPresenter.detachView()

    }
    companion object {
        var response: StoreDetailResponse?=null

        fun createIntent(context: Context,client_id:String,image: String,brand:String): Intent {
            return Intent (context,StoreDetailActivity::class.java)
                    .putExtra("client_id_extra",client_id)
                    .putExtra("image_extra",image)
                    .putExtra("brand_extra",brand)
        }
    }


    /*
    open email intent
    * */

    private fun openEmailIntent() {

        if(response!!.client_data.email.isNotEmpty())
        {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", response!!.client_data.email, null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
 /*


            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.ACTION_SENDTO,response!!.client_data.email)
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            startActivity(Intent.createChooser(intent, "Send Email"))*/
        }


    }



    private fun openShareIntent(){
        if(response!!.locations.size>0)
        {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            intent.putExtra(Intent.EXTRA_TEXT, response!!.locations[0].website)

            startActivity(Intent.createChooser(intent, "Share"))
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////

    /*
       Call permission methods
       */
    override fun callPermissionsGranted() {
        if(response!!.locations.size>0) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + "+233" + response!!.locations[0].contact_number)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            startActivity(intent)
        }
    }

    override fun getDialogType(dialogType: Int) {
        mDialogType = dialogType
    }

    /**
     * check if request permissions are granted by user or not and
     * take next action accordingly
     */
    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        mStoreDetailPresenter.takeActionOnPermissionChanges(grantResults, this,
                getString(R.string.explanation_multiple_request),
                getString(R.string.explanation_multiple_settings),
                getString(R.string.action_grant_permission),
                getString(R.string.action_cancel),
                getString(R.string.action_goto_settings))
    }

    /**
     * User clicked positive button on Alert Dialog
     */
    override fun onPositiveButtonClicked() {
        when (mDialogType) {
            ValueConstants.DIALOG_DENY -> mStoreDetailPresenter.checkMultiplePermissions(ValueConstants.REQUEST_CODE_ASK_CAMERA_MULTIPLE_PERMISSIONS)
            ValueConstants.DIALOG_NEVER_ASK -> mAppUtils.redirectToAppSettings()
        }
    }

    /**
     * User clicked positive button on Alert Dialog
     * so close the activity and prevent user from opening camera
     */
    override fun onNegativeButtonClicked() {

    }

    private fun openWhatsApp(phone:String) {

        val packageManager: PackageManager = packageManager
        val i =Intent(Intent.ACTION_VIEW);
        try {
            val url = "https://api.whatsapp.com/send?phone="+ phone +"&text=" + URLEncoder.encode(getString(R.string.share_msg_content, response!!.client_data.name,"store")+branchObject!!.getShortUrl(this@StoreDetailActivity, linkProperties!!), "UTF-8");
            i.setPackage("com.whatsapp");
            i.data = Uri.parse(url);
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (ex: Exception){
            mAppUtils.showErrorToast(getString(R.string.whatsapp_not_installed))
            ex.printStackTrace()
        }
    }


}