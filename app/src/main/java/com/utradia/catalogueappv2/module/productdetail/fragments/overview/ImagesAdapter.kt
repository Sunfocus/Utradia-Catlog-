package com.utradia.catalogueappv2.module.productdetail.fragments.overview

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.Thumbnails
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject

class ImagesAdapter(context: Context, private var list: List<Thumbnails>
                    , private val mListener: OnThumbClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject
    lateinit var mPrefs: PreferenceManager

    var context:Context

    init {
        this.context=context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mProductList: List<Thumbnails>){
        this.list=mProductList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_thumbnails, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        if (list[pos].isSelected)
            holder.llThumbnail.setBackgroundColor(ContextCompat.getColor(context,R.color.app_light_blue))
        else
            holder.llThumbnail.setBackgroundColor(ContextCompat.getColor(context,R.color.cat_back))

        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .load(list[pos].url)
                .into(holder.imgProduct)


        holder.llThumbnail.setOnClickListener { mListener.onThumbSelected(list[pos].url,pos) }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


        val imgProduct: ImageView = itemView.findViewById(R.id.imgThumb)

        val llThumbnail: LinearLayout = itemView.findViewById(R.id.llThumbnail)

    }
}