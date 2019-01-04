package com.utradia.catalogueappv2.module.imageZoomView

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.CatalogueItem
import com.utradia.catalogueappv2.module.imageZoomView.intrface.GetItemClickPosition
import kotlinx.android.synthetic.main.activity_zoom_image.*
import java.util.*
import javax.inject.Inject

class ZoomImageActivity :  BaseActivity(), GetItemClickPosition,ZoomImageView {

    override val layout: Int
        get() = R.layout.activity_zoom_image


    override fun showToolbar(): Boolean {
       return true
    }

    override val views: List<View>?
        get() = null




    @Inject
    lateinit var mPresenter: ZoomImagePresenter

    private var imgSldAdapter: ImageSliderAdapter? = null

    var pgerAdpter:PagerAdapter?=null

    private var imageList: ArrayList<String>? = null

    private var clsName=""

    private var currentPosition: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as UtradiaApplication).appComponent?.inject(this)

        mPresenter.injectDependencies(this)
        mPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        setToolbar()

        settingLayout()
    }


    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)

        toolbar.setNavigationOnClickListener { finish() }
    }



    private fun settingLayout() {

        imageList = ArrayList()

        pgerAdpter = PagerAdapter(imageList!!)

        imgSldAdapter = ImageSliderAdapter(imageList!!, this)
        val layoutManagaer = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        imageSilder.layoutManager = layoutManagaer
        imageSilder.adapter = imgSldAdapter

        showImage.adapter = pgerAdpter


        clsName=intent.getStringExtra("clsName")



        if(intent.hasExtra("imageList")) {
            imageList!!.addAll(intent.getStringArrayListExtra("imageList"))

            pgerAdpter!!.notifyDataSetChanged()
            imgSldAdapter!!.notifyDataSetChanged()
        }
        else
            getCatalogueItem(intent.getStringExtra("catalogueId"))



        currentPosition = intent.getIntExtra("Itemposition", 0)


        showImage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {


                imgSldAdapter!!.loadPosition(position)

                supportActionBar!!.title = String.format("$clsName (%s of %s)", (position + 1).toString(), imageList!!.size.toString())
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        showImage.currentItem = currentPosition

    }


    private fun getCatalogueItem(id:String){
        if (mAppUtils.isInternetConnected)
            mPresenter.catalogueList(id)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    private fun showErrorMsg() {
        showImage.visibility=View.GONE
        imageSilder.visibility=View.GONE
        tv_no_record.visibility=View.VISIBLE
    }

    override fun onCtalogueItemResponse(catalogue_items: List<CatalogueItem>) {

        imageList!!.clear()

        if(catalogue_items.isNotEmpty())
        {
            for(item in catalogue_items)
            {
                imageList!!.add(item.image)

                pgerAdpter!!.notifyDataSetChanged()
                imgSldAdapter!!.notifyDataSetChanged()
            }
        }
        else
        {
            showErrorMsg()
        }

    }


    override fun onErrorOccur(error_message: String) {
        showErrorMsg()
    }

    override fun getItemPosition(position: Int) {

        showImage.currentItem = position
    }


}
