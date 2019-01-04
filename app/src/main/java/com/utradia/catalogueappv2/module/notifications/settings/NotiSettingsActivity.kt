package com.utradia.catalogueappv2.module.notifications.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.SettingsResponse
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_noti_settings.*
import javax.inject.Inject

class NotiSettingsActivity : BaseActivity(), NotiSettingsView {

    @Inject
    lateinit var mNotiSettingsPresenter: NotiSettingsPresenter
    @Inject
    lateinit var mPrefs: PreferenceManager



    override val layout: Int
        get() = R.layout.activity_noti_settings

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
        mNotiSettingsPresenter.injectDependencies(this)
        mNotiSettingsPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()


        initListeners()

        getNotificationSettings()

    }

    /*setting listeners*/
    private fun initListeners() {

        swNotifications.setOnClickListener {

            val vals = HashMap<String, String>()
            if(swNotifications.isChecked)
                vals["notifications"] = "1"
            else
                vals["notifications"] = "0"

            updateNotificationSettings(vals)
        }

        swOrders.setOnClickListener {

            val vals = HashMap<String, String>()
            if(swOrders.isChecked)
                vals["order_and_logistics"] = "1"
            else
                vals["order_and_logistics"] = "0"

            updateNotificationSettings(vals)
        }

        swSystemMessages.setOnClickListener {

            val vals = HashMap<String, String>()
            if(swSystemMessages.isChecked)
                vals["system_messages"] = "1"
            else
                vals["system_messages"] = "0"

            updateNotificationSettings(vals)
        }
    }


    private fun getNotificationSettings() {
        if (mAppUtils.isInternetConnected)
            mNotiSettingsPresenter.getNotificationSettings(mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

    }


    override fun onDestroy() {
        super.onDestroy()
        mNotiSettingsPresenter.cancelAllAsync()
        mNotiSettingsPresenter.detachView()
    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.notifications_settings)

        toolbar.setNavigationOnClickListener { finish() }
    }


    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, NotiSettingsActivity::class.java)
        }
    }


    /*View model methods*/
    override fun onSettingsReceived(response: SettingsResponse) {

        //  mAppUtils.showSuccessToast(response.success_message)

            val notifications = response.data.notifications
            swNotifications.isChecked = notifications.equals("1", ignoreCase = true)
            val orderLogistics = response.data.order_and_logistics
            swOrders.isChecked = orderLogistics.equals("1", ignoreCase = true)
            val systemMessages = response.data.system_messages
            swSystemMessages.isChecked = systemMessages.equals("1", ignoreCase = true)

    }

    override fun onSettingsNotReceived(msg: String) {
        mAppUtils.showErrorToast(msg)
    }



    /*
    * update Settings
    * */
    private fun updateNotificationSettings(map: HashMap<String, String>) {

        map["user_id"] = mPrefs.userId

        if (mAppUtils.isInternetConnected)
            mNotiSettingsPresenter.updateNotificationSettings(map)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }
}