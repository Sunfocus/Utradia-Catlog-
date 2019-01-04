package com.utradia.catalogueappv2.module.categoryproductlist

import android.content.Context
import android.graphics.Paint
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject

class CatProListAdapter(context: Context, private var mProductList: List<ProductsByCatResponse.OffersBean>
                        , private val mListener: OnProductClick) : androidx.recyclerview.widget.RecyclerView.Adapter<CatProListAdapter.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatProListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(if (viewStatus) R.layout.view_product_item_list else R.layout.view_product_item_grid, null)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: CatProListAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        holder.txtProductName.text = mProductList[pos].title
        holder.txtPrice.text = mPrefs.currencySymbol+" "+mProductList[pos].discounted_price

        holder.txtStrikeThru.text =mPrefs.currencySymbol+" "+ mProductList[pos].discount
        if(!mProductList[pos].discounted_price.equals("",true)){

            holder.txtStrikeThru.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        else{
            holder.txtPrice.visibility=View.GONE
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

        val text="("+mProductList[pos].raters.toString()+")"
        holder.txtRaters.text = text

        holder.ratingBar.rating= mProductList[pos].rating.toFloat()
        mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)
        holder.clProductParent.setOnClickListener { mListener.onItemClick(mProductList[pos].id,mProductList[pos].title) }

    }


    override fun getItemCount(): Int {
        return mProductList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val imgFavourite: ImageView = itemView.findViewById(R.id.imgFavourite)
        val imgNotFavourite: ImageView = itemView.findViewById(R.id.imgNotFavourite)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val txtRaters: TextView = itemView.findViewById(R.id.txtRaters)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtMakeModel: TextView = itemView.findViewById(R.id.txtMakeModel)
        val txtStrikeThru: TextView = itemView.findViewById(R.id.txtQty)
        val clProductParent: ConstraintLayout = itemView.findViewById(R.id.clProductParent)

    }
}