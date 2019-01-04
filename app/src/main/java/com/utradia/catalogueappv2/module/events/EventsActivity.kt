package com.utradia.catalogueappv2.module.events

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.EventsResponse
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.eventdetail.EventDetailActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.utils.PreferenceManager
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_events.*

import javax.inject.Inject

class EventsActivity:BaseActivity(),EventsView,OnEventClicked , TabLayout.OnTabSelectedListener,TextWatcher{


    override fun onAnEventClicked(event_id: String,title: String,imageUrl: String,imageView: ImageView) {

        startActivity(EventDetailActivity.createIntent(this,event_id,title,imageUrl))
        overridePendingTransition(R.anim.slide_in_up, R.anim.do_nothing)

    }


    override fun onEventsNotFound(error_message: String) {
        rvEvents.visibility=View.GONE
        txtNoEvents.visibility=View.VISIBLE
        edtSearchEvents.visibility=View.GONE
        tabCategories.visibility=View.GONE
    }

    override fun onEventsFound(response: EventsResponse) {
        tabCategories.visibility=View.VISIBLE
        this.response=response
        initTabs(response)
        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvEvents.layoutManager = layoutManager1

        updateItems(0,response)
    }

    @Inject lateinit var mEventsPresenter: EventsPresenter
    @Inject lateinit var mPrefs: PreferenceManager

    private lateinit var response:EventsResponse
    lateinit var mAdapter:EventsAdapter
    var position:Int=0

    /**
     * get layout to inflate
     */
    override val layout: Int
        get() = R.layout.activity_events

    override fun showToolbar(): Boolean {
        return true
    }

    /**
     * get layout to inflate
     */
    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)
        mEventsPresenter.injectDependencies(this)
        mEventsPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)
        setToolbar()
        handleViewClicks()

        /*getting event list*/
        getEventsList()

    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent (context, EventsActivity::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_cart, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(CartActivity.createIntent(this))
                true
            }

            R.id.action_search -> {
                startActivity(SearchProduct.createIntent(this,""))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mEventsPresenter.cancelAllAsync()
        mEventsPresenter.detachView()
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val itemCart = menu?.findItem(R.id.action_cart)
        val icon = itemCart?.icon as LayerDrawable
        if (!mPrefs.cartCount.equals("",true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)


        return super.onPrepareOptionsMenu(menu)

    }

    /*method calls api to get event list*/
    private fun getEventsList(){
        if (mAppUtils.isInternetConnected)
            mEventsPresenter.getEvents()
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.events)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleViewClicks(){
        tabCategories.addOnTabSelectedListener(this)
        edtSearchEvents.addTextChangedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        position=tab.position
        updateItems(tab.position,response)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    /*
    * Update Items in list
    * */
    private fun updateItems(pos:Int,response: EventsResponse){
        if (response.events[pos].size>0)
        {
            rvEvents.visibility=View.VISIBLE
            txtNoEvents.visibility=View.GONE

            edtSearchEvents.visibility=View.VISIBLE
             mAdapter = EventsAdapter(this, response.events[pos], response.events[pos],this)
            rvEvents.itemAnimator= ScaleInAnimator()
          //  val animatorAdapter = ScaleInAnimatorAdapter<EventsAdapter.ViewHolder>(mAdapter, rvEvents)
            rvEvents.adapter = mAdapter
        }
        else{
            rvEvents.visibility=View.GONE
            txtNoEvents.visibility=View.VISIBLE
            edtSearchEvents.visibility=View.GONE

        }

    }

    /*
    * update text in tab layout
    * */
    fun initTabs(categories: EventsResponse){

        for (item: String in categories.categories) {

                tabCategories.addTab(tabCategories.newTab().setText(item))

        }
    }


    /*
    *
    * Text watcher events
    * */

    override fun afterTextChanged(s: Editable) {}


    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    /**
     * This method is called to notify you that, within `s`,
     * the `count` characters beginning at `start`
     * have just replaced old text that had length `before`.
     * It is an error to attempt to make changes to `s` from
     * this callback.
     */
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotEmpty())
            mAdapter.filter.filter(s)
        else{
            mAdapter.setData(response.events[position])
            mAdapter.notifyDataSetChanged()
        }
    }
}