package com.utradia.catalogueappv2.module.account

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.invitereferrals.invitereferrals.InviteReferralsApi
import com.theartofdev.edmodo.cropper.CropImage
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.SearchCatListModel
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.notifications.NotificationsActivity
import com.utradia.catalogueappv2.module.orders.OrdersActivity
import com.utradia.catalogueappv2.module.profile.ProfileActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.module.wishlist.WishListActivity
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PermissionFile
import com.utradia.catalogueappv2.utils.PreferenceManager
import io.realm.Realm
import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_myaccount.*
import kotlinx.android.synthetic.main.view_logout_popup.*

class MyAccountActivity : BaseActivity(), MyAccountView {



    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    lateinit var mDialogsUtil: DialogsUtil
    @Inject
    lateinit var myAccountPresenter: MyAccountPresenter
    @Inject
    lateinit var imageUtil: ImageUtility

    @Inject
    lateinit var permissionFile: PermissionFile


    private lateinit var imageUri: Uri


    private var realm: Realm? = null

    /**
     * get layout to inflate
     */
    override val layout: Int
        get() = R.layout.activity_myaccount

    override fun showToolbar(): Boolean {
        return true
    }

    /**
     * get layout to inflate
     */
    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        realm = Realm.getDefaultInstance()

        myAccountPresenter.injectDependencies(this)
        myAccountPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()
        handleViewClicks()

    }

    override fun onResume() {
        super.onResume()
        /*setting user name */
        txtFirstNametxtFirstNam.text = mPrefs.name
        imageUtil.loadImage(mPrefs.profilePic,iv_userImage)

    }

    override fun onDestroy() {
        super.onDestroy()
        myAccountPresenter.detachView()
        realm!!.close()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MyAccountActivity::class.java)
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
            R.id.action_more -> {
                val popup = PopupMenu(this, findViewById(R.id.action_more))
                popup.menuInflater.inflate(R.menu.menu_account, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_home -> {
                            finishAffinity()
                            startActivity(HomeActivity.createIntent(this))
                            true
                        }
                        else ->
                            false
                    }
                }
                popup.show()
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
        supportActionBar!!.setTitle(R.string.Settings)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleViewClicks() {
        /*on logout Click*/
        imgLogout.setOnClickListener { logoutPopup() }
        imgEdit.setOnClickListener { startActivity(ProfileActivity.createIntent(this)) }
        txtWishlist.setOnClickListener { startActivity(WishListActivity.createIntent(this)) }
        txtNotifications.setOnClickListener { startActivity(NotificationsActivity.createIntent(this)) }
        txtOrders.setOnClickListener { startActivity(OrdersActivity.createIntent(this)) }
        txtMessages.setOnClickListener{ }
        txtInviteFriends.setOnClickListener {
            if(mPrefs.isUserLoggedIn)
                InviteReferralsApi.getInstance(this).userDetails(mPrefs.name, mPrefs.email, null, 0, null, null)
            else
            InviteReferralsApi.getInstance(this).userDetails("Yaw", "Yaw@gmail.com", null, 0, null, null)

            InviteReferralsApi.getInstance(this@MyAccountActivity).inline_btn(17168)
        }

        /*on image Click*/
        iv_userImage.setOnClickListener {
            if (permissionFile.checkStorgePermission(this)) {
                imageUri = imageUtil.CameraGalleryIntent(this@MyAccountActivity, ValueConstants.CameraPicker, ValueConstants.GalleryPicker)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == ValueConstants.REQUEST_CODE_LOCATION && permissions.size > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                imageUtil.CameraGalleryIntent(this, ValueConstants.CameraPicker, ValueConstants.GalleryPicker)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ValueConstants.GalleryPicker -> {
                    if (data != null && data.data != null) CropImage.activity(data.data)
                            .start(this)
                }

                ValueConstants.CameraPicker -> {
                    CropImage.activity(imageUri)
                            .start(this)
                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    if (resultCode == RESULT_OK) {
                        onCaptureImageResult(result.uri)
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        mAppUtils.showErrorToast(result.error.toString())
                    }
                }
            }
        }

    }


    private fun onCaptureImageResult(data: Uri) {

        val profileImage: String
        try {

            profileImage = imageUtil.compressImage(data.path!!)

            if (mAppUtils.isInternetConnected)
                myAccountPresenter.uploadImage(profileImage)
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun logoutPopup() {
        val dialog = mDialogsUtil.showDialog(this, R.layout.view_logout_popup)
        dialog.show()

        dialog.txtLogout.setOnClickListener {
            if (mAppUtils.isInternetConnected) {
                dialog.dismiss()
                myAccountPresenter.logout(mPrefs.userId)
            } else {

                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
            }
        }
        dialog.txtCancel.setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun clearData() {

        realm!!.executeTransactionAsync { realm ->
            val data = realm.where(SearchCatListModel::class.java).findFirst()!!.itemList
            // Otherwise it has been deleted already.
            data?.deleteAllFromRealm()
        }
    }

    override fun onErrorOccur(error: String) {
        mAppUtils.showErrorToast(error)
    }

    override fun onImageUpload(message: String,image:String) {
        mAppUtils.showSuccessToast(message)
        mPrefs.setProfilePic(image)
        imageUtil.loadImage(image,iv_userImage)
    }

    override fun onLogoutResponse(message: String) {
        mPrefs.clearPrefrences()

        clearData()

        startActivity(LoginActivity.createIntent(this, ValueConstants.DEFAULT, ValueConstants.UNHIDE_SKIP))
        finishAffinity()
    }

}