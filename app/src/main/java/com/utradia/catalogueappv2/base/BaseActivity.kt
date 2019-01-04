package com.utradia.catalogueappv2.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import butterknife.ButterKnife
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.mvpbase.MvpView
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.ProgressBarHandler
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.ArrayList
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), MvpView {
    @Inject
    open
    lateinit var mProgressHandler: ProgressBarHandler

    @Inject
    open
    lateinit var mAppUtils: AppUtils

   var mNameViews: MutableList<View>? = null

    lateinit var toolbar:Toolbar
    /**
     * get layout to inflate
     */
    @get:LayoutRes
    abstract val layout: Int

    abstract fun showToolbar(): Boolean
    /**
     * get layout to inflate
     */
    abstract val views: List<View>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)
        mProgressHandler.setContext(this)
        setContentView(layout)
        //bind view here for all activities extending this Activity
        ButterKnife.bind(this)
        if (showToolbar())
        configureToolbar()
        mNameViews = ArrayList()
        if (views != null)
            mNameViews!!.addAll(views!!)

    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    private fun configureToolbar() {
        toolbar = findViewById(R.id.toolbar)

            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false);
    }

    //calligraphy configuration
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    /**
     * show ProgressBar for long running operations and
     * disable views so that user cannot perform any task
     */
    override fun showProgress() {
        mProgressHandler.showProgress()
        ButterKnife.apply(mNameViews!!, mAppUtils.ENABLED, false)
    }

    /**
     * hide Progressbar and enable views for user interaction
     */
    override fun hideProgress() {
        mProgressHandler.hideProgress()
        ButterKnife.apply(mNameViews!!, mAppUtils.ENABLED, true)
    }

    /**
     * handle any error during any operation and display proper message to user
     */
    override fun onError(t: Throwable) {
        t.printStackTrace()
        mAppUtils.showErrorMessage(window.decorView.rootView, t)
        ButterKnife.apply(mNameViews!!, mAppUtils.ENABLED, true)
    }

    /*
     * handle any logical error here and display message to user (Maybe in case of invalid credentials)
     *
     * @param message warning message to be displayed to user
     */
    override fun onException(message: String) {
        mAppUtils.showToast(message)
        ButterKnife.apply(mNameViews!!, mAppUtils.ENABLED, true)
    }

}