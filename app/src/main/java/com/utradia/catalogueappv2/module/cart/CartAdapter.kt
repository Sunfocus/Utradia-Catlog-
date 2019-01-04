package com.utradia.catalogueappv2.module.cart

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
import com.utradia.catalogueappv2.model.CartResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject

class CartAdapter(var context: Context, private var mProductList: MutableList<CartResponse.ProductDataBean>
                  , private val mListener: OnCartClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject
    lateinit var mPrefs: PreferenceManager

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mProductList: MutableList<CartResponse.ProductDataBean>) {
        this.mProductList = mProductList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_cart_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        holder.txtProductName.text = mProductList[pos].title

        holder.txtStrikeThru.visibility = View.GONE

        var size = ""
        if (mProductList[pos].prices.size > 0) {
            when {
                mProductList[pos].variant_group_type.equals("1", true) -> size = context.resources.getString(R.string.size) + ": " + mProductList[pos].prices[0].sizes + ", " + context.resources.getString(R.string.color) + ": " + mProductList[pos].prices[0].color_name
                mProductList[pos].variant_group_type.equals("2", true) -> size = context.resources.getString(R.string.color) + ": " + mProductList[pos].prices[0].color_name
                mProductList[pos].variant_group_type.equals("3", true) -> size = context.resources.getString(R.string.size) + ": " + mProductList[pos].prices[0].sizes
            }
        }
        holder.txtMakeModel.text = size

        holder.txtQuantity.text = mProductList[pos].cart_quantity

        if (mProductList[pos].cart_quantity.equals("1", true)) {
            holder.imgMinus.isEnabled = false
            holder.imgMinus.alpha = .5f
        } else {
            holder.imgMinus.isEnabled = true
            holder.imgMinus.alpha = 1f
        }

        /*
        checking for favourite
        * */
        if (mProductList[pos].favourite == 1) {
            holder.imgNotFavourite.visibility = View.GONE
            holder.imgFavourite.visibility = View.VISIBLE
        } else {
            holder.imgNotFavourite.visibility = View.VISIBLE
            holder.imgFavourite.visibility = View.GONE
        }

        if (mProductList[pos].variant_group_type.equals("0", true)) {

            mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)

            if (mProductList[pos].discounted_price.isNotEmpty())
                holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discounted_price
            else
                holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discount

        } else {
            if (mProductList[pos].prices.size > 0 && mProductList[pos].prices[0].var_image.isNotEmpty())
                mImageUtility.loadImage(mProductList[pos].prices[0].var_image, holder.imgProduct)
            else
                mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)

            if (mProductList[pos].prices.size > 0) {
                if (mProductList[pos].prices[0].price.equals("", true)) {
                    if (mProductList[pos].discounted_price.equals("", true))
                        holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discount
                    else
                        holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discounted_price
                } else {
                    holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].prices[0].price
                }
            }
        }

        /*CLICKS*/
        holder.imgPlus.setOnClickListener {
            mListener.onQuantityChanged(mProductList[pos].cart_id, pos, mProductList[pos].cart_quantity.toInt() + 1)
        }
        holder.imgMinus.setOnClickListener {
            mListener.onQuantityChanged(mProductList[pos].cart_id, pos, mProductList[pos].cart_quantity.toInt() - 1)
        }

        holder.txtDelete.setOnClickListener { mListener.onDeleteClicked(mProductList[pos].cart_id, pos) }
        holder.imgNotFavourite.setOnClickListener { mListener.onWishListClicked(mProductList[pos].cart_id, pos) }
        holder.clProductParent.setOnClickListener { mListener.onItemClicked(mProductList[pos].id, mProductList[pos].title) }

    }

    override fun getItemCount(): Int {
        return mProductList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val imgFavourite: ImageView = itemView.findViewById(R.id.imgFavourite)
        val imgNotFavourite: ImageView = itemView.findViewById(R.id.imgNotFavourite)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtMakeModel: TextView = itemView.findViewById(R.id.txtMakeModel)
        val txtStrikeThru: TextView = itemView.findViewById(R.id.txtQty)
        val txtDelete: TextView = itemView.findViewById(R.id.txtDelete)
        val imgPlus: ImageView = itemView.findViewById(R.id.imgPlus)
        val imgMinus: ImageView = itemView.findViewById(R.id.imgMinus)
        val clProductParent: ConstraintLayout = itemView.findViewById(R.id.clProductParent)

    }
}