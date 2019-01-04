package com.utradia.catalogueappv2.module.imageZoomView

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import java.util.*


class PagerAdapter(private var imageDta: ArrayList<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return imageDta.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val photoView = PhotoView(container.context)

        Glide.with(container.context).load(imageDta[position]).into(photoView)

        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return photoView

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as PhotoView)
    }


}