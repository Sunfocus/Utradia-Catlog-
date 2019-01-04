package com.utradia.catalogueappv2.module.filter.module.price

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.innovattic.rangeseekbar.RangeSeekBar
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.model.custom.PriceListModel
import kotlinx.android.synthetic.main.fragment_price.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PriceFrag.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PriceFrag.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PriceFrag : Fragment(), RangeSeekBar.SeekBarChangeListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnPriceInteraction? = null
    var mPriceOne: String = "0"
    var mPriceTwo: String = "0"
    var mPriceFinal: String = ""

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
        return inflater.inflate(R.layout.fragment_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingLayout(view)
        setupRangeSeekBar()
        editTextListener();

    }

    private fun editTextListener() {

        etMinPrice.addTextChangedListener(GenericTextWatcher(etMinPrice))
        etMaxPrice.addTextChangedListener(GenericTextWatcher(etMaxPrice))
    }


    private fun settingLayout(view: View) {


        if (arguments!!.getParcelable<PriceListModel?>("priceList") != null) {
            val priceModel: PriceListModel = arguments!!.getParcelable("priceList")!!

            if (priceModel.name.isNotEmpty()) {
                //  val radio: RadioButton = view.findViewById(priceModel.id)
                //  radio.isChecked = true

                var mSelectPrice = priceModel.name.toString().replace("\\n", "").replace("\"", "").replace("\"", "")
                val strArray = mSelectPrice.split(",")

                for (j in strArray.indices) {
                    etMinPrice.setText(strArray[0])
                    etMaxPrice.setText(strArray[1])
                    rangeSeekBar.setMinThumbValue(strArray[0].toInt())
                    rangeSeekBar.setMaxThumbValue(strArray[1].toInt())
                }

            }

        }

        priceGroup.OnCheckedChangeListener()

        /*     val mAdapter:Price_Adapter


             val layoutManager1 = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
             rv_price_list.layoutManager = layoutManager1

             val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
             rv_price_list.addItemDecoration(itemDecoration)

             mAdapter = Price_Adapter(arguments!!.getParcelableArrayList("priceList")!!,listener)

             rv_price_list.itemAnimator= ScaleInAnimator()
             rv_price_list.adapter = mAdapter*/


        btn_clear.setOnClickListener {
            listener!!.clearPrice()

            etMinPrice.setText("")
            etMaxPrice.setText("")
            rangeSeekBar.setMaxThumbValue(50000)
            rangeSeekBar.setMinThumbValue(0)

        }

    }

    private fun RadioGroup.OnCheckedChangeListener() {
        this.setOnCheckedChangeListener { _, checkedId ->
            if (priceGroup.checkedRadioButtonId != -1) {
                val radio: RadioButton = findViewById(checkedId)
                listener?.onPriceChange(getCheck(radio.id), radio.id)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPriceInteraction) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onStartedSeeking() {
        Log.i(TAG, "Started seeking.")
    }

    override fun onStoppedSeeking() {
        Log.i(TAG, "Stopped seeking.")
    }

    override fun onValueChanged(minThumbValue: Int, maxThumbValue: Int) {
        Log.i(TAG, "Selected range is from $minThumbValue,$maxThumbValue")

        etMinPrice.setText("$minThumbValue")
        etMaxPrice.setText("$maxThumbValue")
        listener?.onPriceChange("${etMinPrice.text},${etMaxPrice.text}", 1)
    }

    private fun setupRangeSeekBar() {
        rangeSeekBar.max = 50000
        rangeSeekBar.seekBarChangeListener = this

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
    interface OnPriceInteraction {
        // TODO: Update argument type and name
        fun onPriceChange(price: String, id: Int)

        fun clearPrice()
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
        fun newInstance(param1: String, param2: String) =
                PriceFrag().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    private fun getCheck(id: Int): String {
        var price = ""
        when (id) {
            R.id.rb_price_1 -> {
                price = "0,500"
            }
            R.id.rb_price_2 -> {
                price = "501,1500"
            }
            R.id.rb_price_3 -> {
                price = "1501,5000"
            }
            R.id.rb_price_4 -> {
                price = "1501,50000"
            }
        }
        return price
    }

    inner class GenericTextWatcher internal constructor(private val view: View) : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            // TODO Auto-generated method stub
            val text = editable.toString()
            when (view.id) {
                R.id.etMinPrice -> {
                    mPriceOne = "${etMinPrice.text}"
                    mPriceFinal = "$mPriceOne,$mPriceTwo"
                    Log.i(TAG, "Value $mPriceFinal")
                    if (mPriceFinal.equals(",0")) {
                    } else if (mPriceFinal.equals("," + mPriceTwo)) {
                    } else if (mPriceFinal.equals(mPriceOne + ",")) {
                    } else if (mPriceFinal.equals(",")) {
                        listener!!.clearPrice()
                        rangeSeekBar.setMaxThumbValue(50000)
                        rangeSeekBar.setMinThumbValue(0)
                    } else {
                        rangeSeekBar.setMaxThumbValue(mPriceTwo.toInt())
                        rangeSeekBar.setMinThumbValue(mPriceOne.toInt())
                        listener?.onPriceChange("$mPriceFinal", 1)
                    }
                }
                R.id.etMaxPrice -> {
                    mPriceTwo = "${etMaxPrice.text}"
                    mPriceFinal = "$mPriceOne,$mPriceTwo"
                    Log.i(TAG, "Value $mPriceFinal")
                    if (mPriceFinal.equals("0,")) {
                    } else if (mPriceFinal.equals(mPriceOne + ",")) {
                    } else if (mPriceFinal.equals("," + mPriceTwo)) {
                    } else if (mPriceFinal.equals(",")) {
                        listener!!.clearPrice()
                        rangeSeekBar.setMaxThumbValue(50000)
                        rangeSeekBar.setMinThumbValue(0)
                    } else {
                        rangeSeekBar.setMaxThumbValue(mPriceTwo.toInt())
                        rangeSeekBar.setMinThumbValue(mPriceOne.toInt())
                        listener?.onPriceChange("$mPriceFinal", 1)
                    }

                }
            }
        }

        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            // TODO Auto-generated method stub
        }

        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            // TODO Auto-generated method stub
        }
    }
}
