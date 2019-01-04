package com.utradia.catalogueappv2.module.storedetail.more_options.location

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.interfaces.OnDialogButtonClickListener
import com.utradia.catalogueappv2.model.StoreDetailResponse
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity
import com.utradia.catalogueappv2.utils.ProgressBarHandler
import kotlinx.android.synthetic.main.activity_location.*
import javax.inject.Inject

class LocationActivity : BaseActivity(), LocationView, OnDialogButtonClickListener, OnMapReadyCallback {


    @Inject
    lateinit var mLocationPresenter: LocationPresenter
    @Inject
    lateinit var mProgressBarHandler: ProgressBarHandler


    private val response = StoreDetailActivity.response
    var mDialogType: Int = 0
    lateinit var mGoogleMap: GoogleMap
    lateinit var mapFrag: SupportMapFragment
    var v: View? = null



    override val layout: Int
        get() = R.layout.activity_location

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null



    /*View methods*/
    override fun showProgress() {
        mProgressBarHandler.showProgress()
    }

    override fun hideProgress() {
        mProgressBarHandler.hideProgress()
    }

    override fun onError(t: Throwable) {

    }

    override fun onException(message: String) {

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mLocationPresenter.injectDependencies(this)
        mLocationPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)

        /*set up map activity*/
        setUpMapFragment()

        setToolbar()

        if (response!!.locations.size > 0)
            updateUI()
        handleViewClicks()
    }


    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.location)

        toolbar.setNavigationOnClickListener { finish() }
    }



    private fun handleViewClicks() {

        imgCall.setOnClickListener { mLocationPresenter.checkCallPermissions() }

        imgWebsite.setOnClickListener {
            if (response!!.locations.size > 0)
                openWebsite()
        }

        imgShare.setOnClickListener {
            if (response!!.locations.size > 0)
                openEmailIntent()
        }

        tv_navigate_loc.setOnClickListener {
            if (response!!.locations[0].lat != null && response.locations[0].lng != null) {
                val gmmIntentUri = Uri.parse("google.navigation:q="+ response.locations[0].lat+","+ response.locations[0].lng)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }

    private fun updateUI() {
        txtBrandName.text = response!!.locations[0].address
    }

    private fun setUpMapFragment() {
        mapFrag = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mGoogleMap.setPadding(0, 10, 0, 0)
        mGoogleMap.uiSettings.isMapToolbarEnabled = true
        mGoogleMap.uiSettings.isZoomGesturesEnabled = false
        mGoogleMap.uiSettings.isZoomControlsEnabled = false
        mGoogleMap.uiSettings.isScrollGesturesEnabled = false

        /*set user location*/
        if (response!!.locations.size > 0)
            setMarker(response)
    }

    /*
       open website url
       * */
    private fun openWebsite() {
        var url = response!!.locations[0].website
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    /*
    open email intent
    * */

    private fun openEmailIntent() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(response!!.locations[0].email))


        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))

        startActivity(Intent.createChooser(intent, "Send Email"))
    }


    private fun setMarker(response: StoreDetailResponse) {
        if (response.locations[0].lat != null && response.locations[0].lng != null) {
            mGoogleMap.clear()
            val lats = response.locations[0].lat
            val lngs = response.locations[0].lng
            if (!lats.equals("", ignoreCase = true)) {
                val loc = LatLng(lats.toDouble(), lngs.toDouble())
                val markerOptions = MarkerOptions()
                markerOptions.position(loc)
                mGoogleMap.addMarker(markerOptions)
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12f))
            }
        }
    }

    /*
    Call permission methods
    */
    override fun callPermissionsGranted() {
        if (response!!.locations.size > 0) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + "+233" + response.locations[0].contact_number)
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
        mLocationPresenter.takeActionOnPermissionChanges(grantResults, this,
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
            ValueConstants.DIALOG_DENY -> mLocationPresenter.checkMultiplePermissions(ValueConstants.REQUEST_CODE_ASK_CAMERA_MULTIPLE_PERMISSIONS)
            ValueConstants.DIALOG_NEVER_ASK -> mAppUtils.redirectToAppSettings()
        }
    }

    /**
     * User clicked positive button on Alert Dialog
     * so close the activity and prevent user from opening camera
     */
    override fun onNegativeButtonClicked() {

    }

}