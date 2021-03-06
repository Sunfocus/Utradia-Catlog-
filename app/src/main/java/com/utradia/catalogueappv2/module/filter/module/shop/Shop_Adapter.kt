package com.utradia.catalogueappv2.module.filter.module.shop


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.ProductsByCatResponse

import com.utradia.catalogueappv2.model.ShopListsData
import kotlinx.android.synthetic.main.view_filter_item.view.*
import kotlinx.android.synthetic.main.view_filter_item_header.view.*


class Shop_Adapter
internal constructor(private val mShopList: MutableList<ProductsByCatResponse.ShopsBean>,
                     private val mListener: ShopFrag.ShopInteraction?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1


    private var beanFilterArrayList: MutableList<ProductsByCatResponse.ShopsBean>


    init {

        this.beanFilterArrayList = mShopList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            HeaderHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_filter_item_header, parent, false))
        } else {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_filter_item, parent, false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val pos=holder.adapterPosition

        if (holder is ViewHolder) {


            holder.mContentView.text=beanFilterArrayList[position].name


            holder.mContentView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(arg0: View) {
                    mListener?.onItemShopClick(beanFilterArrayList[position])
                }
            })


            holder.mContentView.isChecked = beanFilterArrayList[pos].isShopStatus

        }
        else
        {
            val headerHolder = holder as HeaderHolder

            headerHolder.mHeaderView.text=beanFilterArrayList[position].name
        }
    }

    override fun getItemCount(): Int = beanFilterArrayList.size





    override fun getItemViewType(position: Int): Int {
        return if (beanFilterArrayList[position].isHeader) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }

    }



    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mContentView: CheckBox = mView.itemName

        override fun toString(): String {
            return super.toString() +mContentView.text
        }
    }

    inner class HeaderHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mHeaderView: TextView = mView.tv_header_name

        override fun toString(): String {
            return super.toString() + mHeaderView.text
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    beanFilterArrayList = mShopList
                } else {
                    val filteredList = ArrayList<ProductsByCatResponse.ShopsBean>()
                    for (row in mShopList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.toLowerCase().contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }

                    beanFilterArrayList = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = beanFilterArrayList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                beanFilterArrayList = filterResults.values as MutableList<ProductsByCatResponse.ShopsBean>
                notifyDataSetChanged()
            }
        }
    }


}
