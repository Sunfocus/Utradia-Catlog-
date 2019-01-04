package com.utradia.catalogueappv2.module.filter.module.shop


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.model.ShopListsData
import jp.wasabeef.recyclerview.animators.ScaleInAnimator

import kotlinx.android.synthetic.main.fragment_shop.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ShopFrag : Fragment() {

    private var listener: ShopInteraction? = null

    private lateinit var mAdapter: Shop_Adapter

    private lateinit var shoplList: MutableList<ProductsByCatResponse.ShopsBean>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_shop, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoplList = arguments!!.getParcelableArrayList("shopList")!!


        val layoutManager1 = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rv_shop_list.layoutManager = layoutManager1

        if (shoplList.size > 0) {
            rv_shop_list.visibility = View.VISIBLE
            txtNoItemsFound.visibility = View.GONE
            searchView.visibility = View.VISIBLE

            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            rv_shop_list.addItemDecoration(itemDecoration)

            mAdapter = Shop_Adapter(shoplList, listener)

            rv_shop_list.itemAnimator = ScaleInAnimator()
            rv_shop_list.adapter = mAdapter


            searchView.OnQueryListener()
        } else {
            rv_shop_list.visibility = View.GONE
            txtNoItemsFound.visibility = View.VISIBLE
            searchView.visibility = View.GONE
        }

        btn_clear.setOnClickListener {
            listener!!.clearShop()
        }


    }


    fun SearchView.OnQueryListener() {
        this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                mAdapter.filter.filter(s.toLowerCase())
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                mAdapter.filter.filter(s.toLowerCase())
                return false
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShopInteraction) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun refreshAdapter() {
        mAdapter.notifyDataSetChanged()
    }


    interface ShopInteraction {

        fun onItemShopClick(item: ProductsByCatResponse.ShopsBean?)

        fun clearShop()
    }


}
