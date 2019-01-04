package com.utradia.catalogueappv2.module.deeplink

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.applinks.AppLinkData
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.productlist.ProductListActivity
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity
import io.branch.referral.Branch

class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private fun deferredLink() {

        AppLinkData.fetchDeferredAppLinkData(this) {
            // Process app link data

        }
    }

    public override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }

    override fun onStart() {
        super.onStart()

        // Branch init
        Branch.getInstance().initSession({ branchUniversalObject, linkProperties, error ->
            if (error != null) {
                Log.i("BranchTestBed", "branch init failed. Caused by -" + error.getMessage())
            } else {
                Log.i("BranchTestBed", "branch init complete!")
                if (branchUniversalObject != null) {

                    var childIntent: Intent?=null

                    when(branchUniversalObject.canonicalIdentifier) {
                        "item"->childIntent= ProductDetailActivity.createIntent(this@DeepLinkActivity,branchUniversalObject.keywords[0],branchUniversalObject.title,false)
                        "client"->childIntent= StoreDetailActivity.createIntent(this@DeepLinkActivity,branchUniversalObject.keywords[0],branchUniversalObject.imageUrl,branchUniversalObject.title)
                        "category"->childIntent= ProductListActivity.createIntent(this,branchUniversalObject.keywords[0],branchUniversalObject.title,"","","")
                    }

                    startActivities(arrayOf(HomeActivity.createIntent(this@DeepLinkActivity), childIntent!!))
                    finish()


                    Log.i("BranchTestBed", "title " + branchUniversalObject.title)
                    Log.i("BranchTestBed", "CanonicalIdentifier " + branchUniversalObject.canonicalIdentifier)
                    Log.i("ContentMetaData", "metadata " + branchUniversalObject.contentMetadata.convertToJson())
                }

                if (linkProperties != null) {
                    Log.i("BranchTestBed", "Channel " + linkProperties.channel)
                    Log.i("BranchTestBed", "control params " + linkProperties.controlParams)
                }
            }
        }, this.intent.data, this)

    }
}
