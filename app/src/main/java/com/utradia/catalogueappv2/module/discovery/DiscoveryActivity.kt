package com.utradia.catalogueappv2.module.discovery

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.discovery.tabListScreen.TabListActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_discovery.*
import javax.inject.Inject


class DiscoveryActivity : AppCompatActivity(),DiscoveryAdapter.OnDiscoveryClicked {


    private var mAdapter: DiscoveryAdapter? = null

    private var mList= mutableListOf<Drawable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discovery)

        setToolbar()
        changeStatusBarColor(this)

        settingLayout()

        populateItems()
    }

    private fun populateItems() {

        //depending upon the size of service_list size into strings folder
            mList.add(ContextCompat.getDrawable(this,R.drawable.ic_watsapp)!!)
            mList.add(ContextCompat.getDrawable(this,R.drawable.ic_watsapp)!!)
            mList.add(ContextCompat.getDrawable(this,R.drawable.ic_watsapp)!!)
            mList.add(ContextCompat.getDrawable(this,R.drawable.ic_watsapp)!!)
            mList.add(ContextCompat.getDrawable(this,R.drawable.ic_watsapp)!!)
            mList.add(ContextCompat.getDrawable(this,R.drawable.ic_watsapp)!!)
            mList.add(ContextCompat.getDrawable(this,R.drawable.ic_watsapp)!!)
            mList.add(ContextCompat.getDrawable(this,R.drawable.ic_watsapp)!!)

    }

    private fun settingLayout() {

        val layoutManager = GridLayoutManager(this, 2)
        rvEvents.layoutManager = layoutManager
        mAdapter = DiscoveryAdapter(this, mList,this)
        rvEvents.adapter = mAdapter
    }

    private fun setToolbar(){

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.discover)
        toolbar.setNavigationOnClickListener { finish() }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_discover, menu)
        return true
    }


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        // Handle item selection
        return when (menuItem.itemId) {
            R.id.action_search -> {
                startActivity(SearchProduct.createIntent(this,""))
                true
            }
            R.id.action_more->
            {
                val popup = PopupMenu(this, findViewById(R.id.action_more))
                popup.menuInflater.inflate(R.menu.menu_product_option, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_home -> {
                            finishAffinity()
                            startActivity(HomeActivity.createIntent(this))
                            true
                        }

                        R.id.menu_account -> {
                            startActivity(MyAccountActivity.createIntent(this))
                            true
                        }
                        else ->
                            false
                    }
                }
                popup.show()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
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


    override fun onDestroy() {
        super.onDestroy()
        mList.clear()
    }

    override fun onEventClick(position: Int) {
        startActivity(TabListActivity.createIntent(this,resources.getStringArray(R.array.service_list)[position]))

    }
}
