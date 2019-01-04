package com.utradia.catalogueappv2.module.events

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView

import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.EventsResponse
import com.utradia.catalogueappv2.utils.DateTimeUtils
import com.utradia.catalogueappv2.utils.ImageUtility
import java.util.ArrayList
import javax.inject.Inject


class EventsAdapter internal
constructor(context: Context
            , private var mEventList: List<EventsResponse.EventsBean>
            , private var mEventListFilterable: List<EventsResponse.EventsBean>
            , private val mListener: OnEventClicked) : androidx.recyclerview.widget.RecyclerView.Adapter<EventsAdapter.ViewHolder>() ,Filterable{
    /**
     *
     * Returns a filter that can be used to constrain data with a filtering
     * pattern.
     *
     *
     * This method is usually implemented by [android.widget.Adapter]
     * classes.
     *
     * @return a filter used to constrain data
     */
    override fun getFilter(): Filter {
        return  EventsFilter()
    }

    @Inject
    lateinit var mImageUtility: ImageUtility
    @Inject lateinit var mDateTimeUtils: DateTimeUtils
     var context:Context

    init {
        this.context=context
        (context.applicationContext as UtradiaApplication).appComponent?.inject(this)
    }

    fun setData(mEventList: List<EventsResponse.EventsBean>){
        this.mEventList= mEventList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_event_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtEventTitle.text= mEventList[holder.adapterPosition].title

        holder.txtDescription.text= mEventList[holder.adapterPosition].client_name

        val text= mEventList[holder.adapterPosition].start_time + " - "+ mEventList[holder.adapterPosition].end_time + ", "+ mDateTimeUtils.convertDate(mEventList[holder.adapterPosition].end_date)
        holder.txtTimeValue.text=text

        mImageUtility.loadImageWithoutPlaceholder(mEventList[holder.adapterPosition].image,holder.imgImage)
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .load(mEventList[holder.adapterPosition].image)
                .into(holder.imgImage)

        holder.clEvent.setOnClickListener { mListener.onAnEventClicked(mEventList[holder.adapterPosition].id
                ,mEventList[holder.adapterPosition].title,mEventList[holder.adapterPosition].image,holder.imgImage) }
    }

    override fun getItemCount(): Int {
        return mEventList.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
         val txtEventTitle: TextView = itemView.findViewById(R.id.txtEventTitle)
         val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
         val txtTimeValue: TextView = itemView.findViewById(R.id.txtTimeValue)
         val imgImage: ImageView = itemView.findViewById(R.id.imgImage)
         val clEvent: ConstraintLayout = itemView.findViewById(R.id.clEvent)
    }

    internal inner class EventsFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()
            if (constraint == null || constraint.isEmpty()) {
                results.values = mEventList
                results.count = mEventList.size
            } else {
                val mCategoryListFiletered = ArrayList<EventsResponse.EventsBean>()
                for (c in mEventListFilterable) {
                    if (c.title.contains(constraint.toString(),true)) {
                        // if `contains` == true then add it
                        // to our filtered list
                        mCategoryListFiletered.add(c)
                    }
                }
                results.values = mCategoryListFiletered
                results.count = mCategoryListFiletered.size
            }

            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            mEventList = results.values as List<EventsResponse.EventsBean>
            notifyDataSetChanged()
        }
    }

}