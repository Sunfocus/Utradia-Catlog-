package com.utradia.catalogueappv2.module.search.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.Offer

class SearchProductAdapter(private val dataBean: List<Offer>) : RecyclerView.Adapter<SearchProductAdapter.ViewHolder>() {


    private var mCallback: SerachProductCallback? = null

    fun setCallback(callback: SerachProductCallback) {
        mCallback = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductAdapter.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_search_item, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: SearchProductAdapter.ViewHolder, position: Int) {

        val pos=holder.adapterPosition

        holder.tvSuggestionName.text = dataBean[position].keyword

        holder.searchLayout.setOnClickListener {
            mCallback!!.productClick(pos)
        }
    }

    override fun getItemCount(): Int {
        return dataBean.size
    }


    interface SerachProductCallback {

        fun productClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val tvSuggestionName: TextView = itemView.findViewById(R.id.tv_suggestionName)
        val ivSearchSuggestion: ImageView = itemView.findViewById(R.id.iv_searchSuggestion)
        val searchLayout: ConstraintLayout = itemView.findViewById(R.id.searchLayout)

    }


}
