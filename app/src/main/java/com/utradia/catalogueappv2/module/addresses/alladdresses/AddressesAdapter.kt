package com.utradia.catalogueappv2.module.addresses.alladdresses

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.AddressResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import javax.inject.Inject

class AddressesAdapter internal constructor(context: Context
            , private var mDataList: List<AddressResponse.AddressBean>
            , private val mListener: onAddressInteracted) : androidx.recyclerview.widget.RecyclerView.Adapter<AddressesAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    var context: Context
    init {
        this.context=context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems( mDataList: List<AddressResponse.AddressBean>){
        this.mDataList=mDataList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_address_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos=holder.adapterPosition
        holder.txtName.text= mDataList[pos].full_name
        val text=mDataList[pos].houseno+", "+mDataList[pos].locality+
                ", "+mDataList[pos].city_name+", "+mDataList[pos].state_name+
                ", "+mDataList[pos].pincode
        holder.txtAddress.text= text
        holder.imgEdit.setOnClickListener { mListener.onEditClicked(mDataList[pos].id,pos) }
        holder.chkDefault.setOnClickListener { mListener.onDefaultSelected(mDataList[pos].id,pos) }
        holder.txtDelete.setOnClickListener { mListener.onDeleteClicked(mDataList[pos].id,pos) }
        holder.cardAddress.setCardBackgroundColor(ContextCompat.getColor(context,R.color.app_blue))

        if (mDataList[pos].defaultX.equals("1",true))
            holder.cardAddress.setCardBackgroundColor(ContextCompat.getColor(context,R.color.app_blue))
        else
            holder.cardAddress.setCardBackgroundColor(ContextCompat.getColor(context,R.color.white))

        holder.chkDefault.isChecked = mDataList[pos].defaultX.equals("1",true)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtAddress: TextView = itemView.findViewById(R.id.txtAddress)
        val txtDelete: TextView = itemView.findViewById(R.id.txtDelete)
        val imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
        val chkDefault: RadioButton = itemView.findViewById(R.id.chkDefault)
        val cardAddress: androidx.cardview.widget.CardView = itemView.findViewById(R.id.cardAddress)


    }

}