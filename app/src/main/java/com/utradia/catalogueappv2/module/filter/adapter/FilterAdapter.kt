package com.utradia.catalogueappv2.module.filter.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.custom.FilterDataModel

class FilterAdapter(private val stringArray: List<FilterDataModel>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {


    private var mCallback: FilterCallback? = null

    private var row_index = 0

    private lateinit var context: Context

    fun setCallback(callback: FilterCallback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context=parent.context

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_filter_category_item, parent, false))

    }


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        val pos=holder.adapterPosition

        holder.filterCategoryName.text=stringArray[pos].name

        if(stringArray[pos].count.equals("0"))
        {
            holder.filterCategoryCount.visibility=View.GONE
        }
        else
        {
            holder.filterCategoryCount.visibility=View.VISIBLE
            holder.filterCategoryCount.text=stringArray[pos].count
        }

        holder.itemView.setOnClickListener {
            row_index = pos
            mCallback!!.onViewClick(pos)
            notifyDataSetChanged()
        }

        if( row_index == holder.adapterPosition)
        {
            holder.filterLayout.background=ContextCompat.getDrawable(context,R.drawable.filter_open_shape_style)
        }
        else
        {
            holder.filterLayout.background=ContextCompat.getDrawable(context,R.drawable.filter_shape_selected)
        }

    }

    override fun getItemCount(): Int {
        return stringArray.size
    }


    interface FilterCallback {
        fun onViewClick(position: Int)
    }


    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filterCategoryName: TextView = itemView.findViewById(R.id.tv_filter_name)
        val filterCategoryCount: TextView = itemView.findViewById(R.id.tv_filter_count)
        val filterLayout: RelativeLayout = itemView.findViewById(R.id.filter_layout)
    }

}
