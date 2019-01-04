package com.utradia.catalogueappv2.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.core.content.ContextCompat
import android.util.Log
import com.utradia.catalogueappv2.constants.ValueConstants
import java.util.*
import javax.inject.Inject


/**
 * Created by Ajit on 05-10-2017.
 */

class PermissionFile @Inject
constructor(private val mContext: Context) {

    fun checkStorgePermission(ctx: Context): Boolean {
        val permissionCAMERA = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CAMERA)


        val readStorage = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE)

        val writeStorage = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)


        val listPermissionsNeeded = ArrayList<String>()
        if (readStorage != PackageManager.PERMISSION_GRANTED && writeStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(ctx as Activity,

                        listPermissionsNeeded.toTypedArray(), ValueConstants.MULTI_LOC_STOR)
                return false
            }
        }

        return true
    }


    fun checklocationPermissions(ctx: Context): Boolean {
        val permissionCAMERA = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        val listPermissionsNeeded = ArrayList<String>()


        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(ctx as androidx.fragment.app.FragmentActivity,

                        listPermissionsNeeded.toTypedArray(), ValueConstants.REQUEST_CODE_LOCATION)
                return false
            }
        }
        return true
    }


    fun checkCallPermissions(ctx: Context): Boolean {


        if (Build.VERSION.SDK_INT >= 23) {
            if (ctx.checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted")
                return true
            } else {

                Log.v("TAG", "Permission is revoked")
                ActivityCompat.requestPermissions(ctx as androidx.fragment.app.FragmentActivity, arrayOf(Manifest.permission.CALL_PHONE), ValueConstants.REQUEST_CALL)
                return false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted")
            return true
        }


    }


    fun hasPermissionInManifest(context: Context, permissionName: String): Boolean {
        val packageName = context.packageName
        try {
            val packageInfo = context.packageManager
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            val declaredPermisisons = packageInfo.requestedPermissions
            if (declaredPermisisons != null && declaredPermisisons.size > 0) {
                for (p in declaredPermisisons) {
                    if (p == permissionName) {
                        return true
                    }
                }
            }
        } catch (ignored: PackageManager.NameNotFoundException) {

        }

        return false
    }
}
