package com.utradia.catalogueappv2.module.productdetail.optionsizes

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.ProductDetailResponse
import java.util.ArrayList


class SizeOptionAdapter(context: Context, private var mSizeList: List<ProductDetailResponse.OfferDetailsBean.PricesBean>
                        , private var mFilterableSizeList: List<ProductDetailResponse.OfferDetailsBean.PricesBean>
                        , private val mListener: OnSizeOptionSelected) : androidx.recyclerview.widget.RecyclerView.Adapter<SizeOptionAdapter.ViewHolder>() , Filterable {

    /**
     *
     * Returns a filter that can be used to constrain data with a filtering
     * pattern.
     *
     *
     * This method is usually implemented by [android.widget.Adapter]
     * classes.
     *
     * @return a filter used to constrain data
     */
    override fun getFilter(): Filter {
        return  SizeFilter()
    }


    val context: Context

    init {
        this.context=context
    }

    fun setItems(mCategoryList: List<ProductDetailResponse.OfferDetailsBean.PricesBean>){
        this.mSizeList=mCategoryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_size_thumb, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        if (mSizeList[pos].isSelected)
            holder.llSizeThumbnail.setBackgroundColor(ContextCompat.getColor(context, R.color.app_light_blue))
        else
            holder.llSizeThumbnail.setBackgroundColor(ContextCompat.getColor(context, R.color.cat_back))

        holder.txtSize.text=mSizeList[pos].sizes
        holder.llSizeThumbnail.setOnClickListener { mListener.onSizeSelected(mSizeList,pos) }

       // holder.llSizeThumbnail.setOnClickListener { mListener.onSizeSelected(mSizeList,pos) }
        if (mSizeList[pos].quantity.toInt()==0)
        {
            holder.llSizeThumbnail.isEnabled=false
            holder.llSizeThumbnail.alpha=.3f
        }
        else
        {
            holder.llSizeThumbnail.isEnabled=true
            holder.llSizeThumbnail.alpha=1f
        }
    }


    override fun getItemCount(): Int {
        return mSizeList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtSize: TextView = itemView.findViewById(R.id.txtSize)

        val llSizeThumbnail: LinearLayout = itemView.findViewById(R.id.llSizeThumbnail)

    }



    internal inner class SizeFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()
            if (constraint == null || constraint.isEmpty()) {
                results.values = mSizeList
                results.count = mSizeList.size
            } else {
                val mCategoryListFiletered = ArrayList<ProductDetailResponse.OfferDetailsBean.PricesBean>()
                for (c in mFilterableSizeList) {
                    if (c.color.contains(constraint.toString(),true)) {
                        // if `contains` == true then add it
                        // to our filtered list
                        mCategoryListFiletered.add(c)
                    }
                }
                results.values = mCategoryListFiletered
                results.count = mCategoryListFiletered.size
            }

            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            mSizeList = results.values as List<ProductDetailResponse.OfferDetailsBean.PricesBean>
            notifyDataSetChanged()
        }
    }
}
