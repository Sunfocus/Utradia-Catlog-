package com.utradia.catalogueappv2.module.flashsales.current

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.module.flashsales.FlashSalesActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_currentsale.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class CurrentSaleFragment : Fragment(), OnCurrentSaleItemClicked {
    private val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private var variableName: CountDownTimer? = null

    /*
    * View method
    * */
    override fun onItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(activity!!, id, name, true))
    }

    private var mAdapter: CurrentSaleAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the view from fragmenttab1.xml
        return inflater.inflate(R.layout.fragment_currentsale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()

    }

    private fun updateUI() {
        val response = FlashSalesActivity.mFlashResponse
        if (response?.flash_sale != null && response.flash_sale.end_datetime != null) {
            startCountDown(response.flash_sale.end_datetime)
        } else
            clCountdown.visibility = View.GONE

        if (response?.flash_sale?.products != null && response.flash_sale.products.size > 0) {
            val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            rvCurrentSale.layoutManager = layoutManager
            mAdapter = CurrentSaleAdapter(activity!!, response.flash_sale.products, this)
            rvCurrentSale.adapter = mAdapter
        }
    }

    fun startCountDown(date: String) {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)
        val eventDate = dateFormat.parse(date)
        val currentDate = Date()
        val diff = eventDate.time - currentDate.time
        variableName = null
        variableName = object : CountDownTimer(diff, 1000) {
            override fun onFinish() {
                onResume()
            }

            override fun onTick(millisUntilFinished: Long) {
                txtHours.setText(TimeUnit.MILLISECONDS.toHours(millisUntilFinished).toString())
                txtMinutes.text = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)))
                txtSeconds.text = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
            }

        }

        variableName!!.start()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (variableName != null)
            variableName!!.cancel()
    }
}