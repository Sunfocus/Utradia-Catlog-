package com.utradia.catalogueappv2.module.home

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import javax.inject.Inject

class HomeCatAdapter(context: Context, private var mCategoryList: List<HomeResponse.CategoriesBean>, private val colors: Array<Int>, private val mListener: OnHomeCatClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility

    private val TYPE_ITEM = 0
    private val TYPE_FOOTER = 1

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mCategoryList: List<HomeResponse.CategoriesBean>) {
        this.mCategoryList = mCategoryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        if (viewType == TYPE_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.view_homecategory_item, parent, false)
            // set the view's size, margins, paddings and layout parameters
            return ViewHolder(v)
        } else if (viewType == TYPE_FOOTER) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.view_homecategory_footer, parent, false)
            return FooterViewHolder(v)
        }
        // create a new view
        throw RuntimeException("No match for $viewType.")
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        if (holder is ViewHolder) {
            holder.txtCatName.text = mCategoryList[pos].name
            holder.flOuter.setBackgroundResource(colors[pos])
            mImageUtility.loadImageWithoutPlaceholder(mCategoryList[pos].image, holder.imgCatImg)
            holder.clCatParent.setOnClickListener { mListener.onCategoryClicked(mCategoryList[pos].id, mCategoryList[pos].name) }
        } else if (holder is FooterViewHolder) {
            holder.clCatParent.setOnClickListener { mListener.onAllCategoryClicked() }
        }

    }


    override fun getItemCount(): Int {
        return mCategoryList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionFooter(position))
            return TYPE_FOOTER
        return TYPE_ITEM
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtCatName: TextView = itemView.findViewById(R.id.txtCatName)
        val flOuter: FrameLayout = itemView.findViewById(R.id.flOuter)
        val imgCatImg: ImageView = itemView.findViewById(R.id.imgCatImg)
        val clCatParent: ConstraintLayout = itemView.findViewById(R.id.clCatParent)

    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == itemCount - 1
    }

    internal inner class FooterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val clCatParent: ConstraintLayout = itemView.findViewById(R.id.clCatParent)
    }
}