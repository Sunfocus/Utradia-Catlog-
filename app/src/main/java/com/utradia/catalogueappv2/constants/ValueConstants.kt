package com.utradia.catalogueappv2.constants

import android.Manifest
import androidx.annotation.IntDef

interface ValueConstants {

    companion object {
        // type of dialog opened
        @IntDef(DIALOG_DENY.toLong().toInt().toLong().toInt(), DIALOG_NEVER_ASK.toLong().toInt().toLong().toInt())
        @kotlin.annotation.Retention(AnnotationRetention.SOURCE)

        annotation class Speed

         const val DIALOG_DENY = 0
         const val DIALOG_NEVER_ASK = 1


        const val NEW_THREAD = "newThread"
        const val MAIN_THREAD = "mainThread"
        const val MY_HASHMAP = "map"
        const val ADDRESS_INTENT = 1011
        //requests for runtime time permissions
        const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
        const val CALL_PERMISSION = Manifest.permission.CALL_PHONE
        const val READ_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
        const val WRITE_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val RECORD_AUDIO_PERMISSIONS = Manifest.permission.RECORD_AUDIO
        const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
        const val ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
        const val ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
        const val ACCESS_NETWORK_STATE = android.Manifest.permission.ACCESS_NETWORK_STATE

        //request codes used in app
        const val GalleryPicker = 123
        const val CameraPicker = 124
        const val REQUEST_CODE_LOCATION = 1002
        const val REQUEST_CALL = 323
        const val MULTI_LOC_STOR = 1254

        const val REQUEST_CAMERA = 1001
        const val REQUEST_GALLERY = 1002
        const val REQUEST_FBPHOTO = 64207
        const val REQUEST_FBLOGIN = 64206
        const val G_SIGN_IN = 1007
        const val REQUEST_LOCATION = 1003
        const val REQUEST_ASK_LOCATION_MULTIPLE_PERMISSIONS = 1003
        const val REQUEST_CODE_ASK_CAMERA_MULTIPLE_PERMISSIONS = 1005
        const val REQUEST_CODE_ASK_AUDIO_MULTIPLE_PERMISSIONS = 1006

        const val PRODUCT_DETAIL=1
        const val PRODUCT_LIST=3
        const val DEFAULT=0
        const val HOME_SCREEN=2
        const val HIDE_SKIP=99
        const val UNHIDE_SKIP=98

    }

    

}
