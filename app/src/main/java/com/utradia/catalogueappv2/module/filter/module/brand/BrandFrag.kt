package com.utradia.catalogueappv2.module.filter.module.brand

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.BrandList
import com.utradia.catalogueappv2.model.ProductsByCatResponse
import com.utradia.catalogueappv2.module.filter.module.product_rating.Product_Rating_Frag
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_brand.*


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [BrandFrag.OnListFragmentInteractionListener] interface.
 */
private const val ARG_PARAM1 = "clearAction"

class BrandFrag : Fragment() {

    private var param1: String? = null

    private var listener: onBrandInteraction? = null

    private lateinit var mAdapter: Brand_Adapter

    private lateinit var brandList:MutableList<ProductsByCatResponse.BrandsBean>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

    }

     fun refreshAdapter()
    {
        mAdapter.notifyDataSetChanged()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_brand, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brandList=arguments!!.getParcelableArrayList("brandList")!!

        val layoutManager1 = LinearLayoutManager(this.activity, RecyclerView.VERTICAL, false)
        rv_brand_list.layoutManager = layoutManager1

        if(brandList.size>0)
        {
            searchView.visibility==View.VISIBLE
            rv_brand_list.visibility=View.VISIBLE
            txtNoItemsFound.visibility=View.GONE

            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            rv_brand_list.addItemDecoration(itemDecoration)

            mAdapter = Brand_Adapter(brandList, listener)

            rv_brand_list.itemAnimator= ScaleInAnimator()
            rv_brand_list.adapter = mAdapter


            mAdapter.notifyDataSetChanged()


            searchView.OnQueryListener()
        }
        else
        {
            searchView.visibility==View.GONE
            rv_brand_list.visibility=View.GONE
            txtNoItemsFound.visibility=View.VISIBLE
        }

        btn_clear.setOnClickListener {
            listener!!.clearBrands()
        }

    }

    fun SearchView.OnQueryListener()
    {
        this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                mAdapter.filter.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                mAdapter.filter.filter(s)
                return false
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onBrandInteraction) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }



    interface onBrandInteraction {

        fun onBrandClick(item: ProductsByCatResponse.BrandsBean?)

        fun clearBrands()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Product_Rating_Frag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
                Product_Rating_Frag().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }

}
