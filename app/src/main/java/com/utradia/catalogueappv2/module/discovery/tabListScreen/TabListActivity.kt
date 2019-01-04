package com.utradia.catalogueappv2.module.discovery.tabListScreen


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.tabs.TabLayout
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.Catalogue
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.search.SearchProduct
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.activity_tab_list.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.utradia.catalogueappv2.module.eventdetail.EventDetailActivity


class TabListActivity : BaseActivity(), TabLayout.OnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener,TabListAdapter.ListCallback,TabView {



    @Inject
    lateinit var mPresenter: TabScreenPresenter

    private var mAdapter: TabListAdapter? = null


    var mDealList= mutableListOf<String>()


    override val layout: Int
        get() = R.layout.activity_tab_list
    override fun showToolbar(): Boolean {
       return true
    }

    override val views: List<View>?
        get() = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        (application as UtradiaApplication).appComponent?.inject(this)

        mPresenter.injectDependencies(this)
        mPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)


        setToolbar()

        initTabs()

        tab_category.addOnTabSelectedListener(this)

        refreshLayout.setOnRefreshListener(this@TabListActivity )

        intializeRecylervItem()

        populateItem()
    }

    private fun intializeRecylervItem() {

        rv_deals_list.visibility=View.VISIBLE
        tv_noData.visibility=View.GONE

        mAdapter = TabListAdapter(mDealList)
        mAdapter!!.settingCallback(this)


        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rv_deals_list.addItemDecoration(itemDecoration)

        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_deals_list.layoutManager = layoutManager1
        rv_deals_list.itemAnimator = SlideInDownAnimator()
        rv_deals_list.adapter = mAdapter
    }

    private fun populateItem() {
        mDealList.add("as")
        mDealList.add("as")
        mDealList.add("as")

        mAdapter!!.notifyDataSetChanged()
    }



    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.catalogue)

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

    /*
   * update text in tab layout
   * */
    fun initTabs() {


        val linearLayout = tab_category.getChildAt(0) as LinearLayout
        linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        val drawable = GradientDrawable()
        drawable.setColor(Color.GRAY)
        drawable.setSize(1, 1)
        linearLayout.dividerPadding = 10
        linearLayout.dividerDrawable = drawable

        tab_category.addTab(tab_category.newTab().setText("Spa"))
        tab_category.addTab(tab_category.newTab().setText("Wellness"))
        tab_category.addTab(tab_category.newTab().setText("Beauty"))
    }

    companion object {
        fun createIntent(context: Context, name: String): Intent {
            return Intent(context, TabListActivity::class.java)
                    .putExtra("clsName_extra",name)

        }
    }




    override fun onErrorOccur(errorMsg: String) {

    }

    override fun onCtalogueListResponse(data: List<Catalogue>) {

    }

    override fun onRefresh() {
        refreshLayout.isRefreshing=false

        mDealList.clear()
        populateItem()
    }

    override fun eventDetail(id: String, status: String) {

        startActivity(EventDetailActivity.createIntent(this,"3","Rock Night","https://utradiapp.s3-us-west-2.amazonaws.com/1527246611_event.jpg"))
        overridePendingTransition(R.anim.slide_in_up, R.anim.do_nothing)
    }




    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabSelected(p0: TabLayout.Tab?) {

        mDealList.clear()
        populateItem()
    }
}
