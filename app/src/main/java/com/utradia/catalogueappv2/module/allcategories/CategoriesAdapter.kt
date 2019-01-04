package com.utradia.catalogueappv2.module.allcategories

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.CategoriesResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import javax.inject.Inject

class CategoriesAdapter internal constructor(context: Context, private var mDataList: List<CategoriesResponse.CategoriesBean>, private val mListener: OnItemInteracted) : androidx.recyclerview.widget.RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    var context:Context
    init {
        this.context=context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mDataList: List<CategoriesResponse.CategoriesBean>){
        this.mDataList=mDataList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_category_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtCatName.text= mDataList[holder.adapterPosition].name
        mImageUtility.loadImageWithoutPlaceholder(mDataList[holder.adapterPosition].image,holder.imgCatImg)
        holder.clCatParent.setOnClickListener { mListener.onMainItemClicked(mDataList[position].id,position) }

        if (mDataList[position].isSelected)
        {
            holder.clCatParent.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
            holder.txtCatName.setTextColor(ContextCompat.getColor(context,R.color.text_purple))
            holder.imgCatImg.setColorFilter(ContextCompat.getColor(context, R.color.text_purple), android.graphics.PorterDuff.Mode.MULTIPLY)
        }
        else{
            holder.clCatParent.setBackgroundColor(ContextCompat.getColor(context,R.color.cat_back))
            holder.txtCatName.setTextColor(ContextCompat.getColor(context,R.color.cat_icon_tint))
            holder.imgCatImg.setColorFilter(ContextCompat.getColor(context, R.color.cat_icon_tint), android.graphics.PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtCatName: TextView = itemView.findViewById(R.id.txtCatName)
        val imgCatImg: ImageView = itemView.findViewById(R.id.imgCatImg)
        val clCatParent: ConstraintLayout = itemView.findViewById(R.id.clCatParent)

    }

}