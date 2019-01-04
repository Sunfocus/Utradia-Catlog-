package com.utradia.catalogueappv2.module.home

import android.content.Context
import android.widget.LinearLayout
import android.view.ViewGroup

import android.view.LayoutInflater
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import javax.inject.Inject


class ViewPagerAdapter(var mContext: Context,var bannersBean: MutableList<HomeResponse.BannersBean>, private val mListener: OnHomeCatClicked) : PagerAdapter() {

    @Inject
    lateinit var mImageUtility:ImageUtility
    var mLayoutInflater: LayoutInflater
    init {
        (mContext.applicationContext as UtradiaApplication).appComponent?.inject(this)
        mLayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return bannersBean.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.view_slider, container, false)

        val imageView = itemView.findViewById(R.id.imageView) as ImageView

      //  mImageUtility.loadImageWithoutPlaceholder(bannersBean[position].banner_image,imageView)
        Glide.with(mContext)
                .applyDefaultRequestOptions(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .load(bannersBean[position].banner_image)
                .into(imageView)
        container.addView(itemView)


        imageView.setOnClickListener { mListener.onBannerClicked(bannersBean[position].category_id,bannersBean[position].title) }
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}