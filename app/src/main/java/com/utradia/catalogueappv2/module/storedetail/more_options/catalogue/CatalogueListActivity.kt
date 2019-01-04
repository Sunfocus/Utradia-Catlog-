package com.utradia.catalogueappv2.module.storedetail.more_options.catalogue

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.Catalogue
import com.utradia.catalogueappv2.module.imageZoomView.ZoomImageActivity
import kotlinx.android.synthetic.main.activity_catalogue_list.*
import javax.inject.Inject

class CatalogueListActivity : BaseActivity(),CatalogueView,CatalogueListAdapter.ProductCallback,SwipeRefreshLayout.OnRefreshListener {


    @Inject
    lateinit var mPresenter: CataloguePresenter


    private lateinit var mClientId:String

    private var mAdapter: CatalogueListAdapter? = null

    override val layout: Int
        get() = R.layout.activity_catalogue_list

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

        mClientId=intent.getStringExtra("client_id")

        getCatalogue()
    }

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.catalogue)

        toolbar.setNavigationOnClickListener { finish() }

        refreshLayout.setOnRefreshListener(this)
    }

    private fun getCatalogue(){
        if (mAppUtils.isInternetConnected)
            mPresenter.catalogueList(mClientId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    override fun onDestroy() {
        super.onDestroy()

        mPresenter.cancelAllAsync()
    }

    override fun onErrorOccur(errorMsg: String) {
        rv_catalogueList.visibility=View.GONE
        tv_no_record.visibility=View.VISIBLE
    }

    override fun onCtalogueListResponse(data: List<Catalogue>) {

        if(data.isNotEmpty())
        {
            rv_catalogueList.visibility=View.VISIBLE
            tv_no_record.visibility=View.GONE

            if (mAdapter==null){

                val layoutManager = GridLayoutManager(this, 2)
                rv_catalogueList.layoutManager = layoutManager
                mAdapter = CatalogueListAdapter(this, data,this)
                rv_catalogueList.adapter = mAdapter
            }
            else{
                mAdapter?.notifyDataSetChanged()
            }
        }
        else
        {
            rv_catalogueList.visibility=View.GONE
            tv_no_record.visibility=View.VISIBLE
        }



    }

    override fun onRefresh() {
        refreshLayout.isRefreshing=false
        getCatalogue()
    }


    override fun itemClick(item: Catalogue) {
        startActivity(Intent(this, ZoomImageActivity::class.java).putExtra("catalogueId",item.id)
                .putExtra("clsName",item.title))
    }
}
