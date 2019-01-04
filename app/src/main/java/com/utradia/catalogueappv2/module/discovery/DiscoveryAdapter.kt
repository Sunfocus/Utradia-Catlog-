package com.utradia.catalogueappv2.module.discovery

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.utils.ImageUtility
import javax.inject.Inject


class DiscoveryAdapter internal constructor(var context: Context
            , private var mServiceList: List<Drawable>
            , private val mListener: OnDiscoveryClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<DiscoveryAdapter.ViewHolder>() {


    @Inject
    lateinit var mImageUtility: ImageUtility

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_discovery_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pos=holder.adapterPosition

        mImageUtility.loadDrawableWithoutPlaceholder(mServiceList[pos], holder.imgCatalogue)

        holder.imgCatalogue.setOnClickListener {

            mListener.onEventClick(pos)
        }
    }

    override fun getItemCount(): Int {
        return mServiceList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

         val imgCatalogue: ImageView = itemView.findViewById(R.id.imgCatalogue)
    }


    interface OnDiscoveryClicked
    {
        fun onEventClick(position: Int)
    }


}