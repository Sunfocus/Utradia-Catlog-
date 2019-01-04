package com.utradia.catalogueappv2.module.filter.module.product_rating


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.custom.RatingListModel
import kotlinx.android.synthetic.main.view_product_rating_item.view.*

class Product_Rating_Adapter
internal constructor(private val mProductRatingList: MutableList<RatingListModel>,
                     private val mListener: Product_Rating_Frag.OnProductInteraction?)
    : RecyclerView.Adapter<Product_Rating_Adapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Product_Rating_Adapter.ViewHolder {

        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_product_rating_item, parent, false))

    }

    override fun onBindViewHolder(holder: Product_Rating_Adapter.ViewHolder, position: Int) {


             val pos=holder.adapterPosition

            holder.mContentView.text=mProductRatingList[pos].name


        holder.mContentView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(arg0: View) {
               // mListener?.onRateProduct(mProductRatingList[pos])
            }
        })


      //  holder.mContentView.isChecked = mProductRatingList[pos].status


    }

    override fun getItemCount(): Int = mProductRatingList.size



    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

         val mContentView: CheckBox = mView.tv_product_rating_name

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

}
