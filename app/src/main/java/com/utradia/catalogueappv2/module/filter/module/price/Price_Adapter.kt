package com.utradia.catalogueappv2.module.filter.module.price


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.custom.PriceListModel
import kotlinx.android.synthetic.main.view_product_rating_item.view.*

class Price_Adapter
internal constructor(private val mProductRatingList: MutableList<PriceListModel>,
                     private val mListener: PriceFrag.OnPriceInteraction?)
    : RecyclerView.Adapter<Price_Adapter.ViewHolder>() {


    var currentPos = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Price_Adapter.ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_product_rating_item, parent, false))

    }

    override fun onBindViewHolder(holder: Price_Adapter.ViewHolder, position: Int) {


        val pos = holder.adapterPosition

        holder.mContentView.text = mProductRatingList[pos].name


        holder.mContentView.setOnClickListener {
            currentPos = pos

            notifyDataSetChanged()
        }


      //  holder.mContentView.isChecked=mProductRatingList[pos].status


        if(currentPos==pos)
        {
            holder.mContentView.isChecked =true
         //   mListener?.onPriceChange(pos,mProductRatingList[pos])
        }
        else
        {
            holder.mContentView.isChecked =false
        }

       // holder.mContentView.isChecked=mProductRatingList[pos].status

    }

    override fun getItemCount(): Int = mProductRatingList.size


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        val mContentView: CheckBox = mView.tv_product_rating_name


        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

}
