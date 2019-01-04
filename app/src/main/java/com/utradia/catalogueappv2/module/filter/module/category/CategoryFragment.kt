package com.utradia.catalogueappv2.module.filter.module.category

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
import com.utradia.catalogueappv2.model.Category
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_category.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CategoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnCategoryClick? = null

    private lateinit var mAdapter: CategoryAdapter

    private lateinit var categoryList:MutableList<Category>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryList=arguments!!.getParcelableArrayList("catList")!!

        val layoutManager1 = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rv_cat_list.layoutManager = layoutManager1

        if(categoryList.size>0)
        {
            rv_cat_list.visibility=View.VISIBLE
            txtNoItemsFound.visibility=View.GONE
            searchView.visibility=View.VISIBLE

            searchView.OnQueryListener()


            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            rv_cat_list.addItemDecoration(itemDecoration)

            mAdapter = CategoryAdapter(categoryList, listener)

            rv_cat_list.itemAnimator= ScaleInAnimator()
            rv_cat_list.adapter = mAdapter



            mAdapter.notifyDataSetChanged()

        }
        else
        {
            rv_cat_list.visibility=View.GONE
            txtNoItemsFound.visibility=View.VISIBLE
            searchView.visibility=View.GONE
        }


        btn_clear.setOnClickListener {
            listener!!.clearCategory()
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
        if (context is OnCategoryClick) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun refreshAdapter() {
        mAdapter.notifyDataSetChanged()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnCategoryClick {
        // TODO: Update argument type and name
        fun onCategory(catModel: Category)

        fun clearCategory()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CategoryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
