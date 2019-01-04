package com.utradia.catalogueappv2.module.productdetail.fragments.ratingreview

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ProductReviewListResponse
import com.utradia.catalogueappv2.utils.DateTimeUtils
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject


class RatingReviewListAdapter(context: Context, mReviewData: ProductReviewListResponse
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {


    private lateinit var context: Context

    private var mReviewList: MutableList<ProductReviewListResponse.DataBean>

    @Inject
    lateinit var mPrefs: PreferenceManager


    @Inject
    lateinit var mDateTimeUtils: DateTimeUtils

    @Inject
    lateinit var mImageUtility: ImageUtility

    init {
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
        mReviewList = mReviewData.data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        context = parent.context

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_rating_user, parent, false))

        // set the view's size, margins, paddings and layout parameters

    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        val viewHolder = holder as ViewHolder

        mImageUtility.loadImage(mReviewList[pos].image, viewHolder.userImage)
        viewHolder.txtUserName.text = mReviewList[getposition(pos)].username
        viewHolder.txtReviewMsg.text = mReviewList[getposition(pos)].review
        viewHolder.ratingBar.rating = mReviewList[getposition(pos)].rating.toFloat()
        viewHolder.txtCreatedTime.text = mDateTimeUtils.ratingdateFormat(mReviewList[getposition(pos)].created, context)
        if (pos % 2 == 0) {
            viewHolder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        } else {
            viewHolder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_F9))
        }


    }

    private fun getposition(position: Int): Int {
        return position
    }


    override fun getItemCount(): Int {
        return mReviewList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
        val userImage: CircleImageView = itemView.findViewById(R.id.iv_userImage)
        val txtCreatedTime: TextView = itemView.findViewById(R.id.txt_created_time)
        val txtUserName: TextView = itemView.findViewById(R.id.txt_userName)
        val txtReviewMsg: TextView = itemView.findViewById(R.id.txt_review_msg)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

    }

}