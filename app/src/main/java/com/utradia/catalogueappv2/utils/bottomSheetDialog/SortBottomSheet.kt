package com.utradia.catalogueappv2.utils.bottomSheetDialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.utradia.catalogueappv2.R

import com.utradia.catalogueappv2.module.filter.module.price.PriceFrag
import kotlinx.android.synthetic.main.fragment_sort.*


private const val ARG_PARAM = "sortType"


class SortBottomSheet:BaseBottomSheetDialog() {


    private var param1: Int? = null



    private var listener: OnSortInteraction? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sort, container, false)
        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSortInteraction) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intializeLayout(view)
    }

    private fun intializeLayout(view: View) {
        iv_cross.setOnClickListener {
            dismiss()
        }

        when(param1)
        {
            R.id.radioButton->
            {
                radioButton.isChecked=true
            }
            R.id.radioButton2->
            {
                radioButton2.isChecked=true
            }
            R.id.radioButton3->
            {
                radioButton3.isChecked=true
            }
        }


        sort_radioGroup.OnCheckedChangeListener()
    }


    private fun RadioGroup.OnCheckedChangeListener() {
        this.setOnCheckedChangeListener { _, checkedId ->

                val radio: RadioButton = findViewById(checkedId)

            listener!!.onfilterSort(radio.text.toString())

            dismiss()
        }
    }

    interface OnSortInteraction {
        // TODO: Update argument type and name
        fun onfilterSort(sort:String)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PriceFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
                PriceFrag().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM, param1)
                    }
                }
    }

}