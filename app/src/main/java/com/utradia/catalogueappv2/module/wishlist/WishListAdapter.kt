package com.utradia.catalogueappv2.module.wishlist

import android.content.Context
import android.graphics.Paint
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.WishListResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject


class WishListAdapter internal
constructor(context: Context, private var mProductList: List<WishListResponse.FavouriteDataBean>
            , private val mListener: OnWishListClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<WishListAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject lateinit var mPrefs:PreferenceManager
    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }


    fun setItems(mProductList: List<WishListResponse.FavouriteDataBean>){
        this.mProductList=mProductList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_wishlist_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pos = holder.adapterPosition

        holder.txtProductName.text = mProductList[pos].title

        val text= mPrefs.currencySymbol+" "+mProductList[pos].discounted_price
        holder.txtPrice.text =text

        val text1=mPrefs.currencySymbol+" "+ mProductList[pos].discount
        holder.txtStrikeThru.text =text1


        if(!mProductList[pos].discounted_price.equals("",true)){

            holder.txtStrikeThru.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        else{
            holder.txtPrice.visibility=View.GONE
        }

        holder.txtMakeModel.text = mProductList[pos].model

        mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)
        holder.clProductParent.setOnClickListener { mListener.onWishListItemClicked(mProductList[pos].id,mProductList[pos].title) }
        holder.imgDelete.setOnClickListener { mListener.onDeleteClicked(mProductList[pos].id,pos) }
    }

    override fun getItemCount(): Int {
        return mProductList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtMakeModel: TextView = itemView.findViewById(R.id.txtMakeModel)
        val txtStrikeThru: TextView = itemView.findViewById(R.id.txtQty)
        val clProductParent: ConstraintLayout = itemView.findViewById(R.id.clProductParent)

    }

}