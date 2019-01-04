package com.utradia.catalogueappv2.module.search

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.SearchCatListModel
import com.utradia.catalogueappv2.module.search.adapter.SearchCategoryAdapter
import com.utradia.catalogueappv2.utils.PreferenceManager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_search_product.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.utradia.catalogueappv2.model.Offer
import com.utradia.catalogueappv2.model.SearchCategoryModel
import com.utradia.catalogueappv2.module.productlist.ProductListActivity
import com.utradia.catalogueappv2.module.search.adapter.SearchProductAdapter


class SearchProduct : BaseActivity(), SearchProductView, SearchProductAdapter.SerachProductCallback, SearchCategoryAdapter.Searchcallback {


    @Inject
    lateinit var mSearchProductPresenter: SearchProductPresenter
    @Inject
    lateinit var mPrefs: PreferenceManager

    private var adapter: SearchCategoryAdapter? = null
    private var prodAdapter: SearchProductAdapter? = null

    private var realm: Realm? = null

    private val searchList = mutableListOf<Offer>()

    private lateinit var mSearchView: SearchView

    private var mClientId = ""

    override val layout: Int
        get() = R.layout.activity_search_product

    override fun showToolbar(): Boolean {
        return false
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)


        realm = Realm.getDefaultInstance()

        mSearchProductPresenter.injectDependencies(this)
        mSearchProductPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        mClientId = intent.getStringExtra("client_id_extra")

        setToolbar()

        settingLayout()

    }

    override fun onDestroy() {
        super.onDestroy()



        if (rv_searchList != null) {
            rv_searchList.adapter = null
        }

        realm!!.close()

        mSearchProductPresenter.cancelAllAsync()
        mSearchProductPresenter.detachView()
    }


    private fun settingLayout() {
        rv_searchList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rv_searchList.addItemDecoration(itemDecoration)

        prodAdapter = SearchProductAdapter(searchList)

        if (mPrefs.isUserLoggedIn)
        {
            adapter = SearchCategoryAdapter(realm!!.where(SearchCatListModel::class.java).findFirst()!!.itemList)
            adapter!!.settingcallback(this)
            rv_searchList.adapter = adapter
        }


        rv_searchList.setHasFixedSize(true)

    }

    private fun setToolbar() {
        setSupportActionBar(my_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)


        val mSearch = menu.findItem(R.id.action_search)

        mSearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = "Search"
        mSearch.expandActionView()

        mSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {

                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                finish()
                return true
            }
        })


        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                mSearchView.setQuery("", false)
                if (mPrefs.isUserLoggedIn)
                    realm!!.executeTransactionAsync { realm -> SearchCategoryModel.create(realm, query, "") }

                startActivity(ProductListActivity.createIntent(this@SearchProduct, "", query, "", "offerSearch", mClientId))

                //adapter.notifyDataSetChanged();
                /*   realm.commitTransaction();*/
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                // handler.removeCallbacks(input_finish_checker);

                if (newText.isEmpty()) {
                    // callApi(searchedTxt);
                    fetchpreviousResult()
                } else {
                    mSearchProductPresenter.getSearchProduct(newText)
                    // presenter.searchProduct(newText);

                }

                Log.e("textChanged", newText)
                return true
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            circleReveal(R.id.my_toolbar, 1, true, true)


        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun circleReveal(viewID: Int, posFromRight: Int, containsOverflow: Boolean, isShow: Boolean) {
        val myView: View = findViewById(viewID)

        var width = myView.width

        if (posFromRight > 0)
            width = -posFromRight * resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) - resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2
        if (containsOverflow)
            width = -resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)

        val cx = width
        val cy = myView.height / 2

        val anim: Animator
        anim = if (isShow)
            ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, width.toFloat())
        else
            ViewAnimationUtils.createCircularReveal(myView, cx, cy, width.toFloat(), 0f)

        anim.duration = 220.toLong()

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!isShow) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.INVISIBLE
                }
            }
        })

        // make the view visible and start the animation
        if (isShow)
            myView.visibility = View.VISIBLE

        // start the animation
        anim.start()


    }


    private fun fetchpreviousResult() {

        if (mPrefs.isUserLoggedIn)
        {
            realm!!.copyToRealmOrUpdate(realm!!.where(SearchCatListModel::class.java).findFirst()!!.itemList)

            txtNoProducts.visibility = View.GONE
            rv_searchList.visibility = View.VISIBLE

            if (rv_searchList.adapter != adapter) {
                rv_searchList.adapter = adapter
                adapter!!.settingcallback(this)
            }

            rv_searchList.adapter!!.notifyDataSetChanged()
        }


    }


    override fun productList(data: List<Offer>) {

        searchList.clear()
        searchList.addAll(data)

        txtNoProducts.visibility = View.GONE
        rv_searchList.visibility = View.VISIBLE

        if (rv_searchList.adapter != prodAdapter) {
            rv_searchList.adapter = prodAdapter
            prodAdapter!!.setCallback(this)
        }

        rv_searchList.adapter!!.notifyDataSetChanged()

    }

    override fun productClick(position: Int) {
        if (mPrefs.isUserLoggedIn) {
            if (searchList[position].category_id.isNullOrBlank())
                realm!!.executeTransactionAsync { realm -> SearchCategoryModel.create(realm, searchList[position].keyword, "") }
            else
                realm!!.executeTransactionAsync { realm -> SearchCategoryModel.create(realm, searchList[position].keyword, searchList[position].category_id) }
        }

        if (searchList[position].category_id.isNullOrEmpty())
            startActivity(ProductListActivity.createIntent(this@SearchProduct, "", searchList[position].keyword, "", "offerSearch", mClientId))
        else
            startActivity(ProductListActivity.createIntent(this, searchList[position].category_id, searchList[position].keyword, "", "productSearch", mClientId))
    }


    override fun getSearch(id: String, name: String) {
        if (id.isNotBlank())
            startActivity(ProductListActivity.createIntent(this, id, name, "", "productSearch", mClientId))
        else
            startActivity(ProductListActivity.createIntent(this@SearchProduct, "", name, "", "offerSearch", mClientId))
    }


    override fun onError(error: String) {
        txtNoProducts.visibility = View.VISIBLE
        rv_searchList.visibility = View.GONE
        txtNoProducts.text = error
    }


    companion object {

        fun createIntent(context: Context, client_id: String): Intent {
            return Intent(context, SearchProduct::class.java)
                    .putExtra("client_id_extra", client_id)
        }
    }
}
