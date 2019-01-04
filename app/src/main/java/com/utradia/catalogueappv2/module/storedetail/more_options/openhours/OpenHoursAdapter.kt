package com.utradia.catalogueappv2.module.storedetail.more_options.openhours

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.OpenHours
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject


class OpenHoursAdapter internal
constructor(context: Context, private var mProductList: List<OpenHours>) : androidx.recyclerview.widget.RecyclerView.Adapter<OpenHoursAdapter.ViewHolder>() {


    @Inject
    lateinit var mPrefs: PreferenceManager

    private var context:Context
    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenHoursAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_openhours, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pos = holder.adapterPosition
        holder.txtDay.text=mProductList[pos].day
        holder.txtTiming.text=mProductList[pos].timing

        if (mProductList[pos].status.equals("0",true))
            holder.txtTiming.setTextColor(ContextCompat.getColor(context,R.color.flash_red))
        else
            holder.txtTiming.setTextColor(ContextCompat.getColor(context,R.color.appTextColor_dark))
    }

    override fun getItemCount(): Int {
        return mProductList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtDay: TextView = itemView.findViewById(R.id.txtDay)
        val txtTiming: TextView = itemView.findViewById(R.id.txtTiming)


    }

}
