package com.utradia.catalogueappv2.module.userAboutSection

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.webkit.WebView.RENDERER_PRIORITY_BOUND
import androidx.core.content.ContextCompat
import com.utradia.catalogueappv2.R
import kotlinx.android.synthetic.main.activity_user_about.*

class UserAboutActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_about)

        setToolbar()
        changeStatusBarColor(this)

        loadUrl()
    }

    private fun setToolbar(){
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("clsName")

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    private fun loadUrl() {

        val webSettings = webView.settings
        val res = resources
        val fontSize = res.getInteger(R.integer.webview_font_size)
        webSettings.defaultFontSize = fontSize

        webView.settings.javaScriptEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.setRendererPriorityPolicy(RENDERER_PRIORITY_BOUND, true)
        }

        webView.loadUrl(intent.getStringExtra("urlData"))
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun changeStatusBarColor(activity: Activity) {
        val window = activity.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(activity, R.color.colorPrimary)
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    companion object {

        fun createIntent(context: Context, url: String, name: String): Intent {
            return Intent(context, UserAboutActivity::class.java)
                    .putExtra("urlData",url)
                    .putExtra("clsName",name)

        }


    }

}
