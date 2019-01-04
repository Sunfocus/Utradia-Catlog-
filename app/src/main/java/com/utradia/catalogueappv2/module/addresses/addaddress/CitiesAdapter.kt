package com.utradia.catalogueappv2.module.addresses.addaddress

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.CitiesResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import javax.inject.Inject

class CitiesAdapter internal constructor(context: Context
                                         , private var mDataList: List<CitiesResponse.RegionsBean>
                                         , private val mListener: OnRegionClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    var context: Context
    init {
        this.context=context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mDataList: List<CitiesResponse.RegionsBean>){
        this.mDataList=mDataList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_region_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtItemName.text= mDataList[holder.adapterPosition].city_name
        holder.clCatParent.setOnClickListener { mListener.onCityClicked(mDataList[position].id,mDataList[position].city_name) }

    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtItemName: TextView = itemView.findViewById(R.id.txtItemName)
        val clCatParent: ConstraintLayout = itemView.findViewById(R.id.clCatParent)

    }

}