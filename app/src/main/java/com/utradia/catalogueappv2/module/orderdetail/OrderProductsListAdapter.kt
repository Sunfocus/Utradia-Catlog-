package com.utradia.catalogueappv2.module.orderdetail

import android.content.Context
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
import com.utradia.catalogueappv2.model.OrderDetailResponse
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject


class OrderProductsListAdapter(context: Context, private var mOrderList: MutableList<OrderDetailResponse.OrderDataBean.OrderProductsBean>
                               ,private val orderStatus:String, private val mListener: OnProductReview) : androidx.recyclerview.widget.RecyclerView.Adapter<OrderProductsListAdapter.ViewHolder>() {


    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    lateinit var mAppUtils: AppUtils

    @Inject
    lateinit var mImageUtility: ImageUtility
    private  var context:Context

    init {
        this.context=context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mCategoryList: List<OrderDetailResponse.OrderDataBean.OrderProductsBean>) {
        mOrderList.addAll(mCategoryList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductsListAdapter.ViewHolder {


        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_order_product_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: OrderProductsListAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

       // mListener.itemReview(mOrderList.get(pos).id)

        holder.txtProductName.text = mOrderList[pos].title

        var size=""
        if(mOrderList[pos].variant_group_type.equals("1",true))
            size=context.resources.getString(R.string.size)+ ": "+mOrderList[pos].prices[0].sizes+", "+context.resources.getString(R.string.color)+ ": "+mOrderList[pos].prices[0].color_name
        else if(mOrderList[pos].variant_group_type.equals("2",true))
            size=context.resources.getString(R.string.color)+ ": "+mOrderList[pos].prices[0].color_name
        else if(mOrderList[pos].variant_group_type.equals("3",true))
            size=context.resources.getString(R.string.size)+ ": "+mOrderList[pos].prices[0].sizes
        holder.txtMakeModel.text = size

        val qty=context.resources.getString(R.string.qty_)+" : "+mOrderList[pos].cart_quantity
        holder.txtQty.text = qty



        if(mOrderList[pos].variant_group_type.equals("0",true)) {

            mImageUtility.loadImage(mOrderList[pos].image, holder.imgProduct)


            if (mOrderList[pos].discounted_price.equals("",true))
                holder.txtPrice.text = mPrefs.currencySymbol+" "+mOrderList[pos].discount
            else
                holder.txtPrice.text = mPrefs.currencySymbol+" "+mOrderList[pos].discounted_price
        }
        else {
            if (mOrderList[pos].prices[0].var_image.isNotEmpty())
                mImageUtility.loadImage(mOrderList[pos].prices[0].var_image, holder.imgProduct)
            else
                mImageUtility.loadImage(mOrderList[pos].image, holder.imgProduct)

            if(mOrderList[pos].prices[0].price.equals("",true))
            {
                if (mOrderList[pos].discounted_price.equals("",true))
                    holder.txtPrice.text = mPrefs.currencySymbol+" "+mOrderList[pos].discount
                else
                    holder.txtPrice.text = mPrefs.currencySymbol+" "+mOrderList[pos].discounted_price
            }
            else{
                holder.txtPrice.text = mPrefs.currencySymbol+" "+mOrderList[pos].prices[0].price
            }
        }

        holder.txtShippingMethod.text=mOrderList[pos].shipment_type

        holder.txtShippingPrice.text=mPrefs.currencySymbol+" "+mOrderList[pos].shipment_charges


        if(orderStatus.equals("Completed") && mOrderList[pos].rating==0.0)
            holder.txtReviewProduct.visibility=View.VISIBLE
        else
            holder.txtReviewProduct.visibility=View.GONE

        holder.txtReviewProduct.setOnClickListener{mListener.itemReview(pos)}
    }


    override fun getItemCount(): Int {
        return mOrderList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtQty: TextView = itemView.findViewById(R.id.txtQty)
        val txtMakeModel: TextView = itemView.findViewById(R.id.txtMakeModel)
        val txtShippingMethod: TextView = itemView.findViewById(R.id.txtShippingMethod)
        val txtShippingPrice: TextView = itemView.findViewById(R.id.txtShippingPrice)
        val txtReviewProduct: TextView = itemView.findViewById(R.id.txtReviewProduct)
        val group: Group = itemView.findViewById(R.id.group)

    }
}