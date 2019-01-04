package com.utradia.catalogueappv2.module.splash


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.util.Base64
import android.util.Log
import android.view.WindowManager
import com.facebook.applinks.AppLinkData
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.module.home.HomeActivity

import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity

import com.utradia.catalogueappv2.utils.PreferenceManager
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch
import io.branch.referral.BranchError
import io.branch.referral.util.LinkProperties
import kotlinx.android.synthetic.main.activity_splash.*
import org.json.JSONObject
import pl.droidsonroids.gif.AnimationListener
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import pl.droidsonroids.gif.GifDrawable
import java.io.IOException


class SplashActivity : AppCompatActivity() {


    @Inject
    lateinit var mPrefs: PreferenceManager

    /**
     * subscription to Observe our timer Observable
     */
    lateinit var mSubscriber: Subscription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)

        /*setting currency symbol*/
       // getKeyhash();


        getDrawable()
        //start next activity after delay of 3 seconds
        mSubscriber = Observable.timer(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {



        }

        val intent = intent
        val action = intent.action
        val data = intent.data

      /*  if (action === Intent.ACTION_VIEW) {*/
         //   openUrl(data!!)
       // }
    }

    private fun openUrl(data: Uri) {
        Log.e("data",data.toString())
    }



    private fun getDrawable(){
        var gifDrawable: GifDrawable? = null
        try {
            gifDrawable = GifDrawable(assets, "splash.gif")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (gifDrawable != null) {
            gifDrawable.addAnimationListener(object : AnimationListener {
                override fun onAnimationCompleted(loopNumber: Int) {
                    Log.e("splashscreen", "Gif animation completed")
                    if (mPrefs.isUserLoggedIn)
                        startActivity(HomeActivity.createIntent(this@SplashActivity))
                    else
                        startActivity(LoginActivity.createIntent(this@SplashActivity,ValueConstants.HOME_SCREEN,ValueConstants.UNHIDE_SKIP))

                    finish()
                    /*if (can_be_finished && nextIntent != null) {
                        startActivity(nextIntent)
                        finish()
                    } else {
                        can_be_finished = true
                    }*/
                }
            })
        }
        imgShopLogo.setImageDrawable(gifDrawable)
    }

    /**
     * un subscribe the subscriber
     */
    override fun onDestroy() {
        super.onDestroy()
        mSubscriber.unsubscribe()

    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }






    internal fun getKeyhash() {
        // Add code to print out the key hash
        try {
            val info = packageManager.getPackageInfo(
                    "com.utradia.catalogueappv2",
                    PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val key = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                Log.d("KeyHash:", key)
            }
        } catch (e: Exception) {

        }

    }

}
