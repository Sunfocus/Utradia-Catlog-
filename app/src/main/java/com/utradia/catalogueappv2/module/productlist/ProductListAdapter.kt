package com.utradia.catalogueappv2.module.productlist

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject

class ProductListAdapter(context: Context, private var mProductList: List<ProductsByCatResponse.OffersBean>
                         , private val mListener: OnProductClick) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject lateinit var mPrefs: PreferenceManager

    private var viewStatus:Boolean=true

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setView(viewStatus: Boolean){
        this.viewStatus=viewStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListAdapter.ViewHolder {


        val v = LayoutInflater.from(parent.context).inflate(if (viewStatus) R.layout.view_product_item_list else R.layout.view_product_item_grid, null)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        //real price  txtPrice

        //discount price  txtStrikeThru
        Log.i("","mProductList::"+mProductList.toString());

        holder.txtProductName.text = mProductList[pos].title

        holder.txtStrikeThru.text=mPrefs.currencySymbol + " " + mProductList[pos].discount

        if (mProductList[pos].discounted_price.isEmpty()) {
            holder.txtPrice.visibility=View.GONE
            holder.txtStrikeThru.paintFlags = holder.txtStrikeThru.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        } else
        {
            holder.txtStrikeThru.paintFlags = holder.txtStrikeThru.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.txtPrice.visibility=View.VISIBLE
            holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discounted_price
        }


        if(mProductList[pos].favourite==1)
        {
            holder.imgFavourite.visibility=View.VISIBLE
            holder.imgNotFavourite.visibility=View.GONE
        }
        else
        {
            holder.imgFavourite.visibility=View.GONE
            holder.imgNotFavourite.visibility=View.VISIBLE
        }

        holder.txtMakeModel.text = mProductList[pos].model



        if(mProductList[pos].discounted_price.equals("")){
            holder.txtDiscountPercentage.visibility=View.INVISIBLE
        }
        else{
            val discount=mProductList[pos].discount
            val discounted_price=mProductList[pos].discounted_price
            val mPercentage=(discount.toInt() - discounted_price.toInt())
            val mFinalPercentage=  (mPercentage*100)/discount.toInt()
            holder.txtDiscountPercentage.text=mFinalPercentage.toString()+"%"
        }

        val text="("+mProductList[pos].raters.toString()+")"
        holder.txtRaters.text = text

        holder.ratingBar.rating= mProductList[pos].rating.toFloat()
        mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)
        holder.clProductParent.setOnClickListener { mListener.onItemClick(pos,mProductList[pos].id,mProductList[pos].title) }

        holder.favLayout.setOnClickListener {
            if(mProductList[pos].favourite==1)
            {
                mListener.removeFavouroite(mProductList[pos].id,pos)
            }
            else
            {
                mListener.addToFavourite(mProductList[pos].id,pos)
            }
        }

    }


    override fun getItemCount(): Int {
        return mProductList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val imgFavourite: ImageView = itemView.findViewById(R.id.imgFavourite)
        val imgNotFavourite: ImageView = itemView.findViewById(R.id.imgNotFavourite)
        val favLayout: FrameLayout = itemView.findViewById(R.id.flWish)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val txtRaters: TextView = itemView.findViewById(R.id.txtRaters)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtMakeModel: TextView = itemView.findViewById(R.id.txtMakeModel)
        val txtStrikeThru: TextView = itemView.findViewById(R.id.txtQty)
        val txtDiscountPercentage: TextView=itemView.findViewById(R.id.txtDiscountPercentage)
        val clProductParent: ConstraintLayout = itemView.findViewById(R.id.clProductParent)

    }
}