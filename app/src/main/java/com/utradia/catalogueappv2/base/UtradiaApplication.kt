package com.utradia.catalogueappv2.base

import androidx.multidex.MultiDexApplication
import androidx.core.content.ContextCompat
import com.apps.norris.paywithslydepay.core.SlydepayPayment
import com.google.firebase.FirebaseApp
import com.invitereferrals.invitereferrals.InviteReferralsApplication
import com.pushwoosh.Pushwoosh
import com.utradia.catalogueappv2.BuildConfig

import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.api.NetModule
import com.utradia.catalogueappv2.model.SearchCatListModel
import com.utradia.catalogueappv2.utils.UtilsModule
import es.dmoral.toasty.Toasty
import io.branch.referral.Branch
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class UtradiaApplication : MultiDexApplication() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        /*slydepay*/
         SlydepayPayment(this).initCredentials("utradia@gmail.com","1473024435521")

        /*MTN mobile Money*/
        //MobileMoney(this).initCredentials("UTRAD123","7b0fprlm")
       // MobileMoney(this).setThirdPartyId("TPI44768")
        Toasty.Config.getInstance()
                .setErrorColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setSuccessColor(ContextCompat.getColor(this, R.color.app_blue))
                .apply()

        appComponent = DaggerAppComponent.builder()
                .utilsModule(UtilsModule(this)).netModule(NetModule(this)).build()

        //firebase
        FirebaseApp.initializeApp(this)
        //configure timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        //calligraphy for fonts
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

        //intialize realm
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()

                .initialData { realm -> realm.createObject(SearchCatListModel::class.java) }
                .build()
        Realm.setDefaultConfiguration(realmConfig)

        //branch io

/*        // This is needed to deferred deep link from an Android Instant App to a full app
        // It tells the Branch initialization to wait for the Google Play Referrer before proceeding.
        Branch.enablePlayStoreReferrer(1000L);*/

        // Branch logging for debugging
        Branch.enableLogging()

        // Branch object initialization
        Branch.getAutoInstance(this)


        //Pushwoosh Notification Registration
        Pushwoosh.getInstance().registerForPushNotifications()

        //Invite Referrals
        InviteReferralsApplication.register(this)
    }
}
