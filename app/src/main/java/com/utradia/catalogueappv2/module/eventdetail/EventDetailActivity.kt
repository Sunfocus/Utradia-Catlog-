package com.utradia.catalogueappv2.module.eventdetail
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
import android.widget.PopupMenu
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
import com.utradia.catalogueappv2.model.EventDetailResponse
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.DateTimeUtils
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject
import com.utradia.catalogueappv2.utils.ImageUtility
import kotlinx.android.synthetic.main.activity_eventdetail.*


class EventDetailActivity :BaseActivity() ,EventDetailView, OnMapReadyCallback , OnDialogButtonClickListener {


    override fun onEventGoing(success_message: String) {

        mAppUtils.showSuccessToast(success_message)
        txtUnJoin.visibility=View.VISIBLE
        txtJoin.visibility=View.GONE
        mAttendees += 1
        updateAttendeesText()
    }

    override fun onEventGoingFailure(error_message: String) {
        mAppUtils.showErrorToast(error_message)
    }

    override fun onEventUnGoing(success_message: String) {
        mAppUtils.showSuccessToast(success_message)
        txtUnJoin.visibility=View.GONE
        txtJoin.visibility=View.VISIBLE

        mAttendees -= 1
        updateAttendeesText()
    }

    override fun onEventDetailNotFound(error_message: String) {
        animation_view.visibility=View.GONE
        llInfoView.visibility=View.VISIBLE
    }

    override fun onEventDetailFound(response: EventDetailResponse) {
        this.response=response
        animation_view.visibility=View.GONE
        llInfoView.visibility=View.VISIBLE
        updateUI(response)
    }



    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject lateinit var mImageUtility: ImageUtility
    @Inject
    override lateinit var mAppUtils: AppUtils
    @Inject lateinit var mDateTimeUtils: DateTimeUtils
    lateinit var mGoogleMap: GoogleMap
    lateinit var mapFrag: SupportMapFragment
    lateinit var response: EventDetailResponse



    @Inject
    lateinit var mEventDetailPresenter: EventDetailPresenter

    private var mEventId: String=""
    var mDialogType:Int=0
    var mAttendees:Int=0
    /**
     * get layout to inflate
     */
    override val layout: Int
        get() = R.layout.activity_eventdetail

    override fun showToolbar(): Boolean {
        return false
    }

    /**
     * get layout to inflate
     */
    override val views: List<View>?
        get() = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mEventDetailPresenter.injectDependencies(this)
        mEventDetailPresenter.attachView(this)
        mAppUtils.changeStatusBarTranparent(this)

        /*
        * setting support action bar
        * */
        setSupportActionBar(anim_toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        }
        handleViewClicks()

        mEventId=intent.getStringExtra("event_id_extra")
        collapsing_toolbar.title = intent.getStringExtra("title_extra")
        mImageUtility.loadImage(intent.getStringExtra("url_extra"),imgBanner)

        /*getting event details */
        getEventDetail()

        /*set up map fragment*/
        setUpMapFragment()


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_event_detail, menu)
        return true
    }


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        // Handle item selection
        return when (menuItem.itemId) {
            R.id.action_share -> {
                shareEvent()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
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
    }

    /*method calls api to get event list*/
    private fun getEventDetail(){
        if (mAppUtils.isInternetConnected)
            mEventDetailPresenter.getEventDetails(mEventId,mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

    }

    override   fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.do_nothing, R.anim.slide_out_bottom)
    }

    override fun onDestroy() {
        super.onDestroy()
        mEventDetailPresenter.cancelAllAsync()
        mEventDetailPresenter.detachView()

    }
    companion object {
        fun createIntent(context: Context, event_id: String ,title: String,url: String): Intent {
            return Intent (context, EventDetailActivity::class.java)
                    .putExtra("title_extra",title)
                    .putExtra("event_id_extra",event_id)
                    .putExtra("url_extra",url)
        }
    }


    private fun shareEvent() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Dummy Text")
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        startActivity(Intent.createChooser(intent, "Share Link"))
    }




    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    /*
    *
    * handling view clicks
    *
    * */
    private fun handleViewClicks(){

        txtJoin.setOnClickListener {
            if (mAppUtils.isInternetConnected)
                mEventDetailPresenter.goingEvent(mEventId,mPrefs.userId)
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }


        txtUnJoin.setOnClickListener {
            if (mAppUtils.isInternetConnected)
                mEventDetailPresenter.unGoingEvent(mEventId,mPrefs.userId)
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }


        imgCall.setOnClickListener { mEventDetailPresenter.checkCallPermissions() }

        imgWebsite.setOnClickListener {
            if (response.event_details!=null)
                   openWebsite()
        }

        imgShare.setOnClickListener {
           if (response.event_details!=null)
            openEmailIntent()
        }
    }

    /*
    open website url
    * */
    private fun openWebsite() {
        var url = response.event_details.location_data.website
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
        intent.putExtra(Intent.EXTRA_EMAIL,arrayOf(response.event_details.user_data.email))


        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))

        startActivity(Intent.createChooser(intent, "Send Email"))
    }

    /*update UI*/
    private fun updateUI(response: EventDetailResponse) {
        txtEventTitle.text= response.event_details.title
        mAttendees=response.event_details.total_going
        val brandName=response.event_details.user_data.brand_name
        txtDescription.text=  brandName
        txtBrandName.text= brandName

        val text= response.event_details.start_time + " - "+ response.event_details.end_time + ", "+ mDateTimeUtils.convertDate(response.event_details.end_date)
        txtTimeValue.text=text

        txtEventDescription.text=response.event_details.description

        updateAttendeesText()


        if (response.event_details.going_status.equals("1",true))
        {
            txtUnJoin.visibility=View.VISIBLE
            txtJoin.visibility=View.GONE
        }
        else{
            txtUnJoin.visibility=View.GONE
            txtJoin.visibility=View.VISIBLE
        }

        /*set user location*/
        setMarker(response)
    }

    /*
    * update Attendees
    * */
    private fun updateAttendeesText(){
        val attendee=mAttendees.toString()+" "+getString(R.string.attendees)
        txtAttendees.text= attendee
    }

    private fun setMarker(response: EventDetailResponse) {
        if (response.event_details.location_data.lat != null && response.event_details.location_data.lng != null) {
            mGoogleMap.clear()
            val lats = response.event_details.location_data.lat
            val lngs = response.event_details.location_data.lng
            if (!lats.equals("", ignoreCase = true)) {
                val loc = LatLng(lats.toDouble(),lngs.toDouble())
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
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:" + "+233" + response.event_details.user_data.phone )
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        startActivity(intent)
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
        mEventDetailPresenter.takeActionOnPermissionChanges(grantResults, this,
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
            ValueConstants.DIALOG_DENY -> mEventDetailPresenter.checkMultiplePermissions(ValueConstants.REQUEST_CODE_ASK_CAMERA_MULTIPLE_PERMISSIONS)
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