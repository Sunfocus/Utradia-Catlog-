package com.utradia.catalogueappv2.module.productdetail.productoptions

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
import com.utradia.catalogueappv2.model.ProductDetailResponse
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject

class ColorsAdapter(val context: Context, private var mColorList: List<ProductDetailResponse.OfferDetailsBean.ColorsBean>
                    , private val mListener: OnColorSelected) : androidx.recyclerview.widget.RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject
    lateinit var mPrefs: PreferenceManager

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setItems(mCategoryList: List<ProductDetailResponse.OfferDetailsBean.ColorsBean>){
        this.mColorList=mCategoryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_color_thumb, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        if (mColorList[pos].isSelected)
            holder.llThumbnail.setBackgroundColor(ContextCompat.getColor(context,R.color.app_light_blue))
        else
            holder.llThumbnail.setBackgroundColor(ContextCompat.getColor(context,R.color.cat_back))

        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .load(mColorList[pos].var_image)
                .into(holder.imgProduct)

        if (mColorList[pos].quantity.toInt()==0)
        {
            holder.llThumbnail.isEnabled=false
            holder.llThumbnail.alpha=.5f
        }
        else
        {
            holder.llThumbnail.isEnabled=true
            holder.llThumbnail.alpha=1f
        }

        holder.llThumbnail.setOnClickListener { mListener.onColorSelected(mColorList[pos].color
                ,pos,mColorList[pos].color_name
                ,mColorList[pos].grp_id
                ,mColorList[pos].price
                ,mColorList[pos].var_image
        ) }


    }


    override fun getItemCount(): Int {
        return mColorList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val imgProduct: ImageView = itemView.findViewById(R.id.imgThumb)

        val llThumbnail: LinearLayout = itemView.findViewById(R.id.llThumbnail)

    }
}
