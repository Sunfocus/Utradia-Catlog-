package com.utradia.catalogueappv2.module.orders

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.OrderListResponse
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.DateTimeUtils
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject


class OrdersListAdapter(private val context: Context, private var mOrderList: MutableList<OrderListResponse.DataBean.OrdersBean>
                        , private val mListener: OnOrderClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<OrdersListAdapter.ViewHolder>() {


    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    lateinit var mAppUtils: AppUtils

    @Inject lateinit var mDateTimeUtils: DateTimeUtils

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersListAdapter.ViewHolder {


        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_order_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: OrdersListAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        //2018-09-07 10:00:53
        holder.txtOrderDate.text=mDateTimeUtils.convertDateOneToAnother(mOrderList.get(pos).created,"yyyy-MM-dd hh:mm:ss","MMM dd yyyy, hh:mmaa")

        holder.txtOrderId.text = mOrderList.get(pos).orderId

        holder.frameOrderConfirmation.visibility = View.GONE
        holder.txtCancelOrder.visibility = View.GONE

        when (mOrderList.get(pos).order_status) {
            "Completed" -> {
                holder.txtStatusValue.setTextColor(ContextCompat.getColor(context, R.color.light_green))
               //holder.frameOrderConfirmation.visibility = View.VISIBLE
                holder.txtPayNow.visibility = View.GONE
              //holder.txtReviewProduct.visibility = View.VISIBLE
            }
            "Processing" -> holder.txtStatusValue.setTextColor(ContextCompat.getColor(context, R.color.sky_blue))
            "PENDING" -> {
                holder.txtStatusValue.setTextColor(ContextCompat.getColor(context, R.color.dark_yellow))
                holder.frameOrderConfirmation.visibility = View.VISIBLE
                holder.txtPayNow.visibility = View.VISIBLE
               //holder.txtReviewProduct.visibility = View.GONE
                holder.txtCancelOrder.visibility = View.VISIBLE
            }
            "Shipped" -> holder.txtStatusValue.setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
            "Cancelled" -> holder.txtStatusValue.setTextColor(ContextCompat.getColor(context, R.color.dark_red))
        }

        holder.txtStatusValue.text = mOrderList.get(pos).order_status
        holder.txtItemQtyValue.text = mOrderList.get(pos).item_total

        val total = mPrefs.currencySymbol + " " + mAppUtils.round(mOrderList.get(pos).total_price, 2)
        holder.txtAmountValue.text = total

        holder.txtViewOrder.setOnClickListener { mListener.onItemClicked(mOrderList.get(pos).id,mOrderList.get(pos).total_price,pos,"view") }
        holder.txtCancelOrder.setOnClickListener { mListener.onItemClicked(mOrderList.get(pos).id,mOrderList.get(pos).total_price,pos,"cancel") }

        holder.txtPayNow.setOnClickListener {
            mListener.onItemClicked(mOrderList.get(pos).id,mOrderList.get(pos).total_price,pos,"payment")
        }
    }


    override fun getItemCount(): Int {
        return mOrderList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val txtOrderDate: TextView = itemView.findViewById(R.id.txtOrderDate)
        val txtOrderId: TextView = itemView.findViewById(R.id.txtOrderId)
        val txtAmountValue: TextView = itemView.findViewById(R.id.txtAmountValue)
        val txtStatusValue: TextView = itemView.findViewById(R.id.txtStatusValue)
        val txtItemQtyValue: TextView = itemView.findViewById(R.id.txtItemQtyValue)
        val txtViewOrder: TextView = itemView.findViewById(R.id.txtViewOrder)
        val txtCancelOrder: TextView = itemView.findViewById(R.id.txtCancelOrder)
        val frameOrderConfirmation: FrameLayout = itemView.findViewById(R.id.frameOrderConfirmation)
        val txtPayNow: TextView = itemView.findViewById(R.id.txtPayNow)
        val txtReviewProduct: TextView = itemView.findViewById(R.id.txtReviewProduct)
        val orderLayout: ConstraintLayout = itemView.findViewById(R.id.orderLayout)
    }
}