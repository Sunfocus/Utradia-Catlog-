package com.utradia.catalogueappv2.module.notifications

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.NotificationData
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.notifications.settings.NotiSettingsActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_notifications.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NotificationsActivity :BaseActivity(),NotificationView, SwipeRefreshLayout.OnRefreshListener {



    @Inject lateinit var mNotificationPresenter: NotificationPresenter
    @Inject lateinit var mPrefs:PreferenceManager

    var mAdapter:NotificationListAdapter?=null

    var mNotificationList= mutableListOf<NotificationData>()

    private var formattedDate:String?=null

    override val layout: Int
        get() = R.layout.activity_notifications

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
        mNotificationPresenter.injectDependencies(this)
        mNotificationPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()


        settingLayout()

        getNotifcationList()

    }

    private fun settingLayout() {

        mAdapter= NotificationListAdapter(this,mNotificationList)


        refreshLayout.setOnRefreshListener(this)

        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_notificationList.layoutManager = layoutManager1

  /*      val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rv_notificationList.addItemDecoration(itemDecoration)*/

        rv_notificationList.itemAnimator= ScaleInAnimator()
        rv_notificationList.adapter = mAdapter
    }

    private fun getNotifcationList() {
        formattedDate=""

        if (mAppUtils.isInternetConnected)
            mNotificationPresenter.notificationList()
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.notifications)

        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_notification, menu)
        return true
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, NotificationsActivity::class.java)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(CartActivity.createIntent(this))
                true
            }
            R.id.action_settings -> {
                startActivity(NotiSettingsActivity.createIntent(this))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mNotificationList.clear()
        mNotificationPresenter.cancelAllAsync()
        mNotificationPresenter.detachView()
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val itemCart = menu?.findItem(R.id.action_cart)
        val icon = itemCart?.icon as LayerDrawable
        if (!mPrefs.cartCount.equals("",true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)


        return super.onPrepareOptionsMenu(menu)

    }


    override fun onErrorOccur(errorMsg: String) {
       mAppUtils.showErrorToast(errorMsg)

        tv_no_record.visibility=View.VISIBLE
        rv_notificationList.visibility=View.GONE
    }

    override fun onNotificationResponse(data: List<NotificationData>) {

        mNotificationList.clear()

        if (data.isNotEmpty())
        {

            for ((index,dataBean) in data.withIndex()) {
                if (formattedDate != messageDate(dataBean.created)) {
                    formattedDate = messageDate(dataBean.created)

                    mNotificationList.add(NotificationData("","","","","","","","",formattedDate!! ))


                    dataBean.created=notificationDate(dataBean.created)
                    mNotificationList.add(dataBean)
                } else {
                    dataBean.created=notificationDate(dataBean.created)
                    mNotificationList.add(dataBean)
                }

            }
            mAdapter!!.notifyDataSetChanged()

            tv_no_record.visibility=View.GONE
            rv_notificationList.visibility=View.VISIBLE
        }
        else
        {
            tv_no_record.visibility=View.VISIBLE
            rv_notificationList.visibility=View.GONE
        }
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing=false

        getNotifcationList()
    }


    private fun messageDate(time: String): String {

        var updated_date = ""
        try {

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currenTimeZone = sdf.parse(time)


            val formattedTimeZone = sdf.format(currenTimeZone)


            val c = Calendar.getInstance()


            val formattedDate = sdf.format(c.time)


            val dt: String
            c.time = sdf.parse(formattedDate)
            c.add(Calendar.DATE, -1)  // number of days to add
            dt = sdf.format(c.time)


            if (formattedTimeZone == formattedDate)
                updated_date = "Today"
            else if (formattedTimeZone == dt)
                updated_date = "Yesterday"
            else
                updated_date = formattedTimeZone


        } catch (ignored: Exception) {
        }

        return updated_date
    }


    private fun notificationDate(date: String): String {

        var spf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
        val newDate: Date
        try {
            newDate = spf.parse(date)
            spf = SimpleDateFormat("hh:mm aaa, dd MMM yyyy", Locale.getDefault())

            return spf.format(newDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }
}