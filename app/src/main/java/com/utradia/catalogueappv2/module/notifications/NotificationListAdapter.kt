package com.utradia.catalogueappv2.module.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.NotificationData
import com.utradia.catalogueappv2.utils.DateTimeUtils
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import javax.inject.Inject


class NotificationListAdapter(context: Context, private val mNotificationData: MutableList<NotificationData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_HEADER = 0
    private val TYPE_VIEW = 1


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


   override fun getItemViewType(position: Int): Int {
        return if (!mNotificationData[position].header_format.isNullOrBlank()) {
            TYPE_HEADER
        } else {

            TYPE_VIEW
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        context = parent.context

        return if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_notification_header, parent, false)
            HeaderViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_notification_view, parent, false)
            ViewHolder(v)
        }

        // set the view's size, margins, paddings and layout parameters

    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val pos = holder.adapterPosition

        if (holder is HeaderViewHolder) {

            holder.notiHeader.text=mNotificationData[pos].header_format

        } else {

            val viewHolder = holder as ViewHolder

            viewHolder.tvNotificationName.text=mNotificationData[pos].message
            viewHolder.tvNotificationDate.text=mNotificationData[pos].created

        }

    }


    override fun getItemCount(): Int {
        return mNotificationData.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvNotificationName: TextView = itemView.findViewById(R.id.tv_notification_name)
        val tvNotificationDate: TextView = itemView.findViewById(R.id.tv_notification_date)

    }

    inner class HeaderViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val notiHeader: TextView = itemView.findViewById(R.id.noti_header)
    }

}