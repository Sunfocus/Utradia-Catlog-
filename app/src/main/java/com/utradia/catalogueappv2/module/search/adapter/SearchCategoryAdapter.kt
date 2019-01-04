package com.utradia.catalogueappv2.module.search.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.SearchCategoryModel
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


class SearchCategoryAdapter(data: OrderedRealmCollection<SearchCategoryModel>) : RealmRecyclerViewAdapter<SearchCategoryModel, RecyclerView.ViewHolder>(data, true) {


    private var mcallback: Searchcallback? = null


    init {
        // Only set this if the model class has a primary key that is also a integer or long.
        // In that case, {@code getItemId(int)} must also be overridden to return the key.

        setHasStableIds(true)
    }

    fun settingcallback(searchcallback: Searchcallback) {
        mcallback = searchcallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_search_item, parent, false)
        return ViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return super.getItemCount()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val obj = getItem(holder.adapterPosition)

            holder.data = obj

            holder.tvSuggestionName.text = obj!!.name

            holder.searchLayout.setOnClickListener {
                val searchModel = getItem(position)
                mcallback!!.getSearch(searchModel!!.productId,searchModel.name)
            }
        }
    }


    interface Searchcallback {

        fun getSearch(id: String,name:String)

    }


    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val tvSuggestionName: TextView = itemView.findViewById(R.id.tv_suggestionName)
        val ivSearchSuggestion: ImageView = itemView.findViewById(R.id.iv_searchSuggestion)
        val searchLayout: ConstraintLayout = itemView.findViewById(R.id.searchLayout)

        var data: SearchCategoryModel? = null



    }


}
