package com.utradia.catalogueappv2.module.filter.module.product_rating

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.custom.RatingListModel
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_product__rating.*
import android.graphics.PorterDuff
import com.utradia.catalogueappv2.R.id.ratingBar
import android.graphics.drawable.LayerDrawable
import androidx.core.graphics.drawable.DrawableCompat.setTint
import com.utradia.catalogueappv2.R.id.ratingBar
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.fragment_price.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Product_Rating_Frag.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Product_Rating_Frag.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Product_Rating_Frag : Fragment(), SeekBar.OnSeekBarChangeListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnProductInteraction? = null

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
        return inflater.inflate(R.layout.fragment_product__rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ratingGroup.OnCheckedChangeListener()

        settingLayout(view)
        setupRangeSeekBar()

    }

    private fun settingLayout(view: View) {

        if (arguments!!.getParcelable<RatingListModel?>("ratingList") != null) {
            val rateModel: RatingListModel = arguments!!.getParcelable("ratingList")!!

            if (rateModel.name.isNotEmpty()) {
                /*  val radio: RadioButton = view.findViewById(rateModel.id)

                  radio.isChecked=true*/
                if (rateModel.name.equals("1")) {
                    seekbar_reverse.progress = 1
                    val progress = rateBarOne.getProgressDrawable()
                    DrawableCompat.setTint(progress, Color.parseColor("#0373d3"))
                } else if (rateModel.name.equals("2")) {
                    seekbar_reverse.progress = 2
                    val progress = rateBarTwo.getProgressDrawable()
                    DrawableCompat.setTint(progress, Color.parseColor("#0373d3"))
                } else if (rateModel.name.equals("3")) {
                    seekbar_reverse.progress = 3
                    val progress = rateBarThree.getProgressDrawable()
                    DrawableCompat.setTint(progress, Color.parseColor("#0373d3"))
                } else if (rateModel.name.equals("4")) {
                    seekbar_reverse.progress = 4
                    val progress = rateBarFour.getProgressDrawable()
                    DrawableCompat.setTint(progress, Color.parseColor("#0373d3"))
                } else if (rateModel.name.equals("5")) {
                    seekbar_reverse.progress = 5
                    val progress = rateBarFive.getProgressDrawable()
                    DrawableCompat.setTint(progress, Color.parseColor("#0373d3"))
                }
            }
        }

        btnClear.setOnClickListener {
            seekbar_reverse.progress = 0
            listener!!.clearRating()
            val progressone = rateBarOne.getProgressDrawable()
            DrawableCompat.setTint(progressone, Color.parseColor("#cccccc"))

            val progressTwo = rateBarTwo.getProgressDrawable()
            DrawableCompat.setTint(progressTwo, Color.parseColor("#cccccc"))

            val progressThree = rateBarThree.getProgressDrawable()
            DrawableCompat.setTint(progressThree, Color.parseColor("#cccccc"))


            val progressFour = rateBarFour.getProgressDrawable()
            DrawableCompat.setTint(progressFour, Color.parseColor("#cccccc"))

            val progressFive = rateBarFive.getProgressDrawable()
            DrawableCompat.setTint(progressFive, Color.parseColor("#cccccc"))

        }

    }


    // TODO: Rename method, update argument and hook method into UI event


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProductInteraction) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        //Here passing progress value
        listener?.onRateProduct("$progress", 1)
        //End

        // Here we are set the rating
        if (progress == 1) {
            seekbar_reverse.progress = 1
            val progressone = rateBarOne.getProgressDrawable()
            DrawableCompat.setTint(progressone, Color.parseColor("#0373d3"))

            val progressTwo = rateBarTwo.getProgressDrawable()
            DrawableCompat.setTint(progressTwo, Color.parseColor("#cccccc"))

            val progressThree = rateBarThree.getProgressDrawable()
            DrawableCompat.setTint(progressThree, Color.parseColor("#cccccc"))


            val progressFour = rateBarFour.getProgressDrawable()
            DrawableCompat.setTint(progressFour, Color.parseColor("#cccccc"))

            val progressFive = rateBarFive.getProgressDrawable()
            DrawableCompat.setTint(progressFive, Color.parseColor("#cccccc"))
        } else if (progress == 2) {
            seekbar_reverse.progress = 2
            val progressone = rateBarOne.getProgressDrawable()
            DrawableCompat.setTint(progressone, Color.parseColor("#cccccc"))

            val progressTwo = rateBarTwo.getProgressDrawable()
            DrawableCompat.setTint(progressTwo, Color.parseColor("#0373d3"))

            val progressThree = rateBarThree.getProgressDrawable()
            DrawableCompat.setTint(progressThree, Color.parseColor("#cccccc"))


            val progressFour = rateBarFour.getProgressDrawable()
            DrawableCompat.setTint(progressFour, Color.parseColor("#cccccc"))

            val progressFive = rateBarFive.getProgressDrawable()
            DrawableCompat.setTint(progressFive, Color.parseColor("#cccccc"))
        } else if (progress == 3) {
            seekbar_reverse.progress = 3
            val progressone = rateBarOne.getProgressDrawable()
            DrawableCompat.setTint(progressone, Color.parseColor("#cccccc"))

            val progressTwo = rateBarTwo.getProgressDrawable()
            DrawableCompat.setTint(progressTwo, Color.parseColor("#cccccc"))

            val progressThree = rateBarThree.getProgressDrawable()
            DrawableCompat.setTint(progressThree, Color.parseColor("#0373d3"))


            val progressFour = rateBarFour.getProgressDrawable()
            DrawableCompat.setTint(progressFour, Color.parseColor("#cccccc"))

            val progressFive = rateBarFive.getProgressDrawable()
            DrawableCompat.setTint(progressFive, Color.parseColor("#cccccc"))
        } else if (progress == 4) {
            seekbar_reverse.progress = 4
            val progressone = rateBarOne.getProgressDrawable()
            DrawableCompat.setTint(progressone, Color.parseColor("#cccccc"))

            val progressTwo = rateBarTwo.getProgressDrawable()
            DrawableCompat.setTint(progressTwo, Color.parseColor("#cccccc"))

            val progressThree = rateBarThree.getProgressDrawable()
            DrawableCompat.setTint(progressThree, Color.parseColor("#cccccc"))


            val progressFour = rateBarFour.getProgressDrawable()
            DrawableCompat.setTint(progressFour, Color.parseColor("#0373d3"))

            val progressFive = rateBarFive.getProgressDrawable()
            DrawableCompat.setTint(progressFive, Color.parseColor("#cccccc"))
        } else if (progress == 5) {
            seekbar_reverse.progress = 5
            val progressone = rateBarOne.getProgressDrawable()
            DrawableCompat.setTint(progressone, Color.parseColor("#cccccc"))

            val progressTwo = rateBarTwo.getProgressDrawable()
            DrawableCompat.setTint(progressTwo, Color.parseColor("#cccccc"))

            val progressThree = rateBarThree.getProgressDrawable()
            DrawableCompat.setTint(progressThree, Color.parseColor("#cccccc"))


            val progressFour = rateBarFour.getProgressDrawable()
            DrawableCompat.setTint(progressFour, Color.parseColor("#cccccc"))

            val progressFive = rateBarFive.getProgressDrawable()
            DrawableCompat.setTint(progressFive, Color.parseColor("#0373d3"))
        } else {
            seekbar_reverse.progress = 0
            val progressone = rateBarOne.getProgressDrawable()
            DrawableCompat.setTint(progressone, Color.parseColor("#cccccc"))

            val progressTwo = rateBarTwo.getProgressDrawable()
            DrawableCompat.setTint(progressTwo, Color.parseColor("#cccccc"))

            val progressThree = rateBarThree.getProgressDrawable()
            DrawableCompat.setTint(progressThree, Color.parseColor("#cccccc"))


            val progressFour = rateBarFour.getProgressDrawable()
            DrawableCompat.setTint(progressFour, Color.parseColor("#cccccc"))

            val progressFive = rateBarFive.getProgressDrawable()
            DrawableCompat.setTint(progressFive, Color.parseColor("#cccccc"))
        }
        //End
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    private fun setupRangeSeekBar() {
        seekbar_reverse.max = 5
        seekbar_reverse.setOnSeekBarChangeListener(this)
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
    interface OnProductInteraction {
        // TODO: Update argument type and name
        fun onRateProduct(rateText: String, id: Int)

        fun clearRating()
    }

    private fun RadioGroup.OnCheckedChangeListener() {
        this.setOnCheckedChangeListener { _, checkedId ->
            if (ratingGroup.checkedRadioButtonId != -1) {
                val radio: RadioButton = findViewById(checkedId)

                listener?.onRateProduct(getCheck(radio.id), radio.id)
            }
        }
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
        fun newInstance(param1: String, param2: String) =
                Product_Rating_Frag().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


    private fun getCheck(id: Int): String {
        var rateProduct = ""
        when (id) {
            R.id.rb_prod_1 -> {
                rateProduct = "4"
            }
            R.id.rb_prod_2 -> {
                rateProduct = "3"
            }
            R.id.rb_prod_3 -> {
                rateProduct = "2"
            }
            R.id.rb_prod_4 -> {
                rateProduct = "1"
            }
        }
        return rateProduct
    }


}
