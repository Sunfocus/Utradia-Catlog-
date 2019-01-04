package com.utradia.catalogueappv2.module.home

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
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import javax.inject.Inject

class MostPopularAdapter(context: Context, private var mCategoryList: List<HomeResponse.MostPopularBean>
                         , private val mListener: OnHomeCatClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<MostPopularAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mCategoryList: List<HomeResponse.MostPopularBean>){
        this.mCategoryList=mCategoryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularAdapter.ViewHolder {


        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_mostpopular_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: MostPopularAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        holder.txtCatName.text = mCategoryList[pos].title
        mImageUtility.loadImageWithoutPlaceholder(mCategoryList[pos].image, holder.imgCatImg)
        holder.clPopularParent.setOnClickListener { mListener.omMostPopularClicked(mCategoryList[pos].id
                ,mCategoryList[pos].title,mCategoryList[pos].category_id) }


    }


    override fun getItemCount(): Int {
        return mCategoryList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val txtCatName: TextView = itemView.findViewById(R.id.txtCatName)
        val imgCatImg: ImageView = itemView.findViewById(R.id.imgCatImg)
        val clPopularParent: ConstraintLayout = itemView.findViewById(R.id.clPopularParent)

    }
}