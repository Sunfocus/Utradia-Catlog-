package com.utradia.catalogueappv2.module.flashsales.next

import android.content.Context
import android.graphics.Paint
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.FlashSalesResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject


class NextSaleAdapter(context: Context, private var mProductList: MutableList<FlashSalesResponse.NextFlashSaleBean.ProductsBeanX>
                         , private val mListener: OnNextItemClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<NextSaleAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject
    lateinit var mPrefs: PreferenceManager

    private var context:Context

    init {
        this.context=context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextSaleAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_flashsale_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: NextSaleAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        holder.progressBar.getProgressDrawable().setColorFilter(
                ContextCompat.getColor(context,R.color.app_blue), android.graphics.PorterDuff.Mode.SRC_IN)

        val qty=mProductList[pos].quantity
        val sold=mProductList[pos].sold
        val percent=(sold.toInt()*100)/qty.toInt()

        holder.progressBar.progress= percent
        holder.txtPercent.text=percent.toString()+"%"

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



        holder.txtProductName.text = mProductList[pos].title
        holder.txtPrice.text = mPrefs.currencySymbol+" "+mProductList[pos].discounted_price

        holder.txtStrikeThru.text =mPrefs.currencySymbol+" "+ mProductList[pos].discount
        if(!mProductList[pos].discounted_price.equals("",true)){

            holder.txtStrikeThru.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        else{
            holder.txtPrice.visibility=View.GONE
        }


        holder.txtMakeModel.text = mProductList[pos].model


        mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)
        holder.clProductParent.setOnClickListener { mListener.onItemClicked(mProductList[pos].id,mProductList[pos].title) }

    }


    override fun getItemCount(): Int {
        return mProductList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val txtPercent: TextView = itemView.findViewById(R.id.txtPercent)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtMakeModel: TextView = itemView.findViewById(R.id.txtMakeModel)
        val txtStrikeThru: TextView = itemView.findViewById(R.id.txtQty)
        val txtDiscountPercentage: TextView=itemView.findViewById(R.id.txtDiscountPercentage)
        val clProductParent: ConstraintLayout = itemView.findViewById(R.id.clProductParent)

    }
}