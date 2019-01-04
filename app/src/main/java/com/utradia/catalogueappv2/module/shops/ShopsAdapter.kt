package com.utradia.catalogueappv2.module.shops

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView

import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ShopListResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import java.util.ArrayList
import javax.inject.Inject


class ShopsAdapter internal
constructor(context: Context, private val mShopList: List<ShopListResponse.ShopListsDataBean>, private val mListener: OnShopClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<ShopsAdapter.ViewHolder>(),Filterable {


    @Inject
    lateinit var mImageUtility: ImageUtility

    private var beanFilterArrayList: List<ShopListResponse.ShopListsDataBean>

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)

        this.beanFilterArrayList = mShopList
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopsAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_shop_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtShopName.text= beanFilterArrayList[holder.adapterPosition].brand_name
        mImageUtility.loadImage(beanFilterArrayList[holder.adapterPosition].logo,holder.imgLogo)
        holder.clShopParent.setOnClickListener { view -> mListener.onShopClicked(beanFilterArrayList[holder.adapterPosition].id,beanFilterArrayList[holder.adapterPosition].brand_name
                ,beanFilterArrayList[holder.adapterPosition].logo
                ,holder.adapterPosition) }
    }

    override fun getItemCount(): Int {
        return beanFilterArrayList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

         val txtShopName: TextView = itemView.findViewById(R.id.txtShopName)
         val imgLogo: ImageView = itemView.findViewById(R.id.imgShopLogo)
         val clShopParent: ConstraintLayout = itemView.findViewById(R.id.clShopParent)

    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    beanFilterArrayList = mShopList
                } else {
                    val filteredList = ArrayList<ShopListResponse.ShopListsDataBean>()
                    for (row in mShopList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.toLowerCase().contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }

                    beanFilterArrayList = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = beanFilterArrayList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                beanFilterArrayList = filterResults.values as List<ShopListResponse.ShopListsDataBean>
                notifyDataSetChanged()
            }
        }
    }

}