package com.utradia.catalogueappv2.module.checkout

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ShipMethodsResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject

class ShipMethodsAdapter internal constructor(context: Context
                                              , private var mDataList: List<ShipMethodsResponse.ShippingPriceBean>
                                              , private val mListener: OnMethodClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<ShipMethodsAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject lateinit var mPrefs:PreferenceManager
    var context: Context
    init {
        this.context=context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipMethodsAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_ship_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtItemName.text= mDataList[holder.adapterPosition].delivery_method
        holder.txtPrice.text= mPrefs.currencySymbol+" "+mDataList[holder.adapterPosition].price
        holder.clCatParent.setOnClickListener { mListener.onMethodClicked(position) }

    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtItemName: TextView = itemView.findViewById(R.id.txtItemName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val clCatParent: ConstraintLayout = itemView.findViewById(R.id.clCatParent)

    }

}