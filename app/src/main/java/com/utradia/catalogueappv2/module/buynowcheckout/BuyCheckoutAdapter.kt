package com.utradia.catalogueappv2.module.buynowcheckout

import android.annotation.SuppressLint
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.CheckoutData
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject

class BuyCheckoutAdapter(context: Context, private var mProductList: MutableList<CheckoutData.ProductDataBean>
                         , private val mListener: OnBuyItemClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<BuyCheckoutAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject
    lateinit var mPrefs: PreferenceManager
    private var context: Context

    init {
        this.context = context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mProductList: MutableList<CheckoutData.ProductDataBean>) {
        this.mProductList = mProductList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyCheckoutAdapter.ViewHolder {


        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_checkout_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BuyCheckoutAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        holder.txtProductName.text = mProductList[pos].title

        holder.txtMakeModel.text = mProductList[pos].model

        val qty = context.resources.getString(R.string.qty_) + " : " + mProductList[pos].cart_quantity
        holder.txtQty.text = qty


        if (mProductList[pos].variant_group_type.equals("0", true)) {

            mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)


            if (mProductList[pos].discounted_price.equals("", true))
                holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discount
            else
                holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discounted_price
        } else {
            if (mProductList[pos].prices[0].var_image.isNotEmpty())
                mImageUtility.loadImage(mProductList[pos].prices[0].var_image, holder.imgProduct)
            else
                mImageUtility.loadImage(mProductList[pos].image, holder.imgProduct)

            if (mProductList[pos].prices[0].price.equals("", true)) {
                if (mProductList[pos].discounted_price.equals("", true))
                    holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discount
                else
                    holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].discounted_price
            } else {
                holder.txtPrice.text = mPrefs.currencySymbol + " " + mProductList[pos].prices[0].price
            }
        }

        if (mProductList[pos].is_shipping_free == 1) {
            holder.group.visibility = View.GONE
        } else {
            holder.group.visibility = View.VISIBLE
            if (mProductList[pos].shipping_data != null) {
                holder.txtShippingMethod.text = mProductList[pos].shipping_data.delivery_method
                holder.txtShippingMethod.setTextColor(ContextCompat.getColor(context, R.color.ccc))
            } else {
                holder.txtShippingMethod.text = context.resources.getString(R.string.not_deliverable_at_this_address)
                holder.txtShippingMethod.setTextColor(ContextCompat.getColor(context, R.color.flash_red))
            }

            val text = mPrefs.currencySymbol + " " + mProductList[pos].shipping_charges
            holder.txtShippingPrice.text = text

            holder.txtShippingMethod.setOnClickListener { mListener.onShippingSelected(pos) }
        }
    }

    override fun getItemCount(): Int {
        return mProductList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val txtShippingMethod: TextView = itemView.findViewById(R.id.txtShippingMethod)
        val txtShippingPrice: TextView = itemView.findViewById(R.id.txtShippingPrice)

        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val group: Group = itemView.findViewById(R.id.group)
        val txtMakeModel: TextView = itemView.findViewById(R.id.txtMakeModel)
        val txtQty: TextView = itemView.findViewById(R.id.txtQty)
        val clProductParent: ConstraintLayout = itemView.findViewById(R.id.clProductParent)

    }
}