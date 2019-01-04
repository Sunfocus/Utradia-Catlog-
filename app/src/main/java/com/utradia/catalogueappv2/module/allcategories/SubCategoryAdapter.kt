package com.utradia.catalogueappv2.module.allcategories

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
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

class SubCategoryAdapter(context: Context, private var mCategoryList: List<CategoriesResponse.SubCategoriesBean>
                         , private val mListener: OnSubCatClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mCategoryList: List<CategoriesResponse.SubCategoriesBean>){
        this.mCategoryList=mCategoryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryAdapter.ViewHolder {


            val v = LayoutInflater.from(parent.context).inflate(R.layout.view_subcat_item, parent, false)
            // set the view's size, margins, paddings and layout parameters
            return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: SubCategoryAdapter.ViewHolder, position: Int) {
           val pos = holder.adapterPosition

            holder.txtCatName.text = mCategoryList[pos].name
            mImageUtility.loadImage(mCategoryList[pos].image, holder.imgCatImg)
            holder.clCatParent.setOnClickListener { mListener.onSubCategoryClicked(mCategoryList[pos].id,mCategoryList[pos].name) }


    }


    override fun getItemCount(): Int {
        return mCategoryList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtCatName: TextView = itemView.findViewById(R.id.txtCatName)
        val imgCatImg: ImageView = itemView.findViewById(R.id.imgCatImg)
        val clCatParent: ConstraintLayout = itemView.findViewById(R.id.clCatParent)

    }
}