package com.utradia.catalogueappv2.utils

import android.content.Context
import com.utradia.catalogueappv2.constants.ValueConstants
import dagger.Module
import dagger.Provides
import rx.Scheduler
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class UtilsModule(private val mContext: Context) {

    // get AppUtils instance
    val appUtils: AppUtils
        @Provides
        @Singleton
        get() = AppUtils(mContext)


    // get DateTimeUtils instance
    val dateTimeUtils: DateTimeUtils
        @Provides
        @Singleton
        get() = DateTimeUtils()

    // get ProgressBarHandler instance
    val progressBar: ProgressBarHandler
        @Provides
        @Singleton
        get() = ProgressBarHandler()

    //get dialog utils
    val dialogUtils: DialogsUtil
        @Provides
        @Singleton
        get() = DialogsUtil()

    //get dialog utils
    val randomStringUtils: RandomString
        @Provides
        @Singleton
        get() = RandomString(7)

    //get new thread
    val newThread: Scheduler
        @Provides
        @Singleton
        @Named(ValueConstants.NEW_THREAD)
        get() = Schedulers.io()

    //get main thread
    val mainThread: Scheduler
        @Provides
        @Singleton
        @Named(ValueConstants.MAIN_THREAD)
        get() = AndroidSchedulers.mainThread()


    //get Preference Manager
    val preferences: PreferenceManager
        @Provides
        @Singleton
        get() = PreferenceManager(mContext)

    //get Fragment utils
    val fragUtils: FragmentUtils
        @Provides
        @Singleton
        get() = FragmentUtils()

    //get location helper methods
    val locationUtils: LocationHelper
        @Provides
        @Singleton
        get() = LocationHelper(mContext)

    //get location tracker
    val locationTrackerOF: LocationTracker
        @Provides
        @Singleton
        get() = LocationTracker(mContext)

    //get image utils
    val imageUtils: ImageUtility
        @Provides
        @Singleton
        get() = ImageUtility(mContext)

    //get permisision utils
    val permissionUtils: PermissionFile
        @Provides
        @Singleton
        get() = PermissionFile(mContext)


    val progressAnimation: ProgressBarAnimation
        @Provides
        @Singleton
        get() = ProgressBarAnimation()


}
