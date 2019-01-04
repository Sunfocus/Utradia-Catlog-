package com.utradia.catalogueappv2.module.storedetail.more_options.shop_products

import android.content.Context
import android.graphics.Paint
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ClientProductResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject


class ShopProductAdapter(context: Context, private var mProductList: MutableList<ClientProductResponse.OffersBean>
                         , private val mListener: OnProductClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<ShopProductAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject
    lateinit var mPrefs: PreferenceManager

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mCategoryList: List<ClientProductResponse.OffersBean>){
        mProductList.addAll(mCategoryList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopProductAdapter.ViewHolder {


        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_product_grid, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: ShopProductAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        holder.txtProductName.text = mProductList[pos].title
        holder.txtStrikeThru.text =mPrefs.currencySymbol+" "+ mProductList[pos].discount

        if(mProductList[pos].discounted_price.equals("",true))
        {
            holder.txtPrice.visibility=View.INVISIBLE
            holder.txtStrikeThru.paintFlags = holder.txtStrikeThru.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }else
        {
            holder.txtStrikeThru.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.txtPrice.visibility=View.VISIBLE
            holder.txtPrice.text = mPrefs.currencySymbol+" "+mProductList[pos].discounted_price
        }

        holder.txtMakeModel.text = mProductList[pos].model

        val text="("+mProductList[pos].raters.toString()+")"
        holder.txtRaters.text = text

        holder.ratingBar.rating= mProductList[pos].rating.toFloat()
        mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)
        holder.clProductParent.setOnClickListener { mListener.onItemClicked(mProductList[pos].id,mProductList[pos].title) }

    }


    override fun getItemCount(): Int {
        return mProductList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar2)
        val txtRaters: TextView = itemView.findViewById(R.id.txtRaters)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtMakeModel: TextView = itemView.findViewById(R.id.txtMakeModel)
        val txtStrikeThru: TextView = itemView.findViewById(R.id.txtQty)
        val clProductParent: ConstraintLayout = itemView.findViewById(R.id.clProductParent)

    }
}