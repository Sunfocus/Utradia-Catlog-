package com.utradia.catalogueappv2.module.storedetail.more_options.catalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.Catalogue
import com.utradia.catalogueappv2.utils.DateTimeUtils
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject


class CatalogueListAdapter(context: Context, private val mCatalogueData: List<Catalogue>,private val mCallback:ProductCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var context: Context


    @Inject
    lateinit var mPrefs: PreferenceManager


    @Inject
    lateinit var mDateTimeUtils: DateTimeUtils

    @Inject
    lateinit var mImageUtility: ImageUtility

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        context = parent.context

            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_catalogue_view, parent, false)
            return ViewHolder(v)

        // set the view's size, margins, paddings and layout parameters
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        val viewHolder = holder as ViewHolder

        mImageUtility.loadImage(mCatalogueData[pos].main_image, holder.imgCatalogue)

            viewHolder.tvCategoryType.text=mCatalogueData[pos].title
            viewHolder.tvDescription.text=mCatalogueData[pos].description
            viewHolder.tvTime.text=mDateTimeUtils.convertDateOneToAnother(mCatalogueData[pos].created,"yyyy-MM-dd hh:mm:ss","dd,MMM yyyy")

        viewHolder.itemLayout.setOnClickListener {
            mCallback.itemClick(mCatalogueData[pos])
        }



    }

    interface ProductCallback
    {
        fun itemClick(item:Catalogue)
    }



    override fun getItemCount(): Int {
        return mCatalogueData.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvCategoryType: TextView = itemView.findViewById(R.id.tv_categoryType)
        val imgCatalogue: ImageView = itemView.findViewById(R.id.imgCatalogue)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_categoryDescription)
        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)

    }
}