package com.utradia.catalogueappv2.module.imageZoomView

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.module.imageZoomView.intrface.GetItemClickPosition
import java.util.*

/**
 * Created by PC-DESTOP on 2/16/2018.
 */

class ImageSliderAdapter(private val imageList: ArrayList<String>, private val itemClickPosition: GetItemClickPosition) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {


    private var context: Context? = null

    private var imagePosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_image_slider, parent, false)
        return ViewHolder(v)
    }


    fun loadPosition(imagepos:Int)
    {
        imagePosition=imagepos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pos = holder.adapterPosition

        Glide.with(context!!).load(imageList[holder.adapterPosition]).into(holder.imageView)

        if (pos == imagePosition) {
            holder.imageView.setBackgroundColor(ContextCompat.getColor(context!!,R.color.app_blue))
        } else {
            holder.imageView.setBackgroundColor(ContextCompat.getColor(context!!,R.color.cat_back))
        }

        holder.imageView.setOnClickListener {
            imagePosition=pos
            notifyDataSetChanged()
            itemClickPosition.getItemPosition(imagePosition)
        }

    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
