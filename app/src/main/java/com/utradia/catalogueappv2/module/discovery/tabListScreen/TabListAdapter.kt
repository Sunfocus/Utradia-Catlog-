package com.utradia.catalogueappv2.module.discovery.tabListScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import com.utradia.catalogueappv2.R


class TabListAdapter internal constructor(private val consltList: List<String>) : RecyclerView.Adapter<TabListAdapter.ViewHolder>() {


    lateinit var context: Context
    private lateinit var mCallback: ListCallback




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_tab_list_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    fun settingCallback(mCallback: ListCallback) {
        this.mCallback = mCallback
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pos = holder.adapterPosition

        holder.itemLayout.setOnClickListener {
            mCallback.eventDetail("","")
        }

    }

    override fun getItemCount(): Int {
        return consltList.size
    }

    interface ListCallback {

        fun eventDetail(id:String,status:String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
    }

}